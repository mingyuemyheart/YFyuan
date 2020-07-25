package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.KeyUnitDisposalPointActivity;
import com.moft.xfapply.activity.KeyUnitFireFacilityDetailActivity;
import com.moft.xfapply.activity.KeyUnitKeyPartDetailActivity;
import com.moft.xfapply.activity.KeyUnitPartitionDetailActivity;
import com.moft.xfapply.activity.OldPlanActivity;
import com.moft.xfapply.activity.PanoramaActivity;
import com.moft.xfapply.activity.PickOrTakeImageActivity;
import com.moft.xfapply.activity.QRCodeActivity;
import com.moft.xfapply.activity.adapter.FireFacilityExpandableAdapter;
import com.moft.xfapply.activity.adapter.KeyPartExpandableAdapter;
import com.moft.xfapply.activity.adapter.PartitionExpandableAdapter;
import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.logic.location.NavService;
import com.moft.xfapply.logic.network.OkHttpClientManager;
import com.moft.xfapply.model.base.IFireFacilityInfo;
import com.moft.xfapply.model.base.IGeomElementInfo;
import com.moft.xfapply.model.base.IKeyPartInfo;
import com.moft.xfapply.model.base.IMediaInfo;
import com.moft.xfapply.model.base.IPartitionInfo;
import com.moft.xfapply.model.common.ComLocation;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.common.EleCondition;
import com.moft.xfapply.model.common.FireFacilityCategary;
import com.moft.xfapply.model.common.KeyPartCategary;
import com.moft.xfapply.model.common.PartitionCategary;
import com.moft.xfapply.model.common.PropertyDes;
import com.moft.xfapply.model.entity.KeyUnitMediaDBInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.dto.KeyUnitDTO;
import com.moft.xfapply.model.external.dto.KeyUnitDetailDTO;
import com.moft.xfapply.model.external.dto.KeyUnitDisposalPointDTO;
import com.moft.xfapply.model.external.result.ArrayRestResult;
import com.moft.xfapply.model.external.result.SimpleRestResult;
import com.moft.xfapply.utils.DictionaryUtil;
import com.moft.xfapply.utils.GsonUtil;
import com.moft.xfapply.utils.LogUtils;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.Utils;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class ZddwLogic extends DetailLogic {
    public ZddwLogic(View v, Activity a) {
        super(v, a);
    }
    private RelativeLayout rl_new = null;
    private ImageView iv_new = null;
    private ImageView iv_new_no = null;

    private List<ImageView> imageList = new ArrayList<>();
    private List<TextView> textList = new ArrayList<>();

    //当前选择的分区对象
    private IPartitionInfo curConditionPart = null;

    private int curSelected = 0;
    //当前基础对象的显示的属性item
    private List<PropertyDes> pdListDetail = null;
    //重点单位所有分区的Map
    private Map<String, IPartitionInfo> partitionInfoMap = new LinkedHashMap<>();
    //重点单位所有重点部位的Map
    private Map<String, IKeyPartInfo> keyPartInfoMap = new LinkedHashMap<>();
    //重点单位所有消防设施的Map
    private Map<String, IFireFacilityInfo> fireFacilityInfoMap = new LinkedHashMap<>();
    //重点单位所有对象（基础信息、分区、消防设施、重点区域）的附件信息
    private Map<String, List<IMediaInfo>> mAttachMap = new LinkedHashMap<>();
    //仅挂载到重点单位下的关键media
    private List<IMediaInfo> pickMediaInfos = new ArrayList<>();
    //重点单位所有的media数据（一次数据库查询获取，减少数据库查询）
    private List<IMediaInfo> keyUnitMediaInfos = new ArrayList<>();

    //ID:837818 19-01-09 【总分区】创建及用途对应 王泉
    private IPartitionInfo rootPartition;

    @Override
    protected void onInited(final IGeomElementInfo info) {
        if(!isHistory && LvApplication.getInstance().getIsDoUpdateData()) {
            if (LvApplication.getInstance().getIsUpdateDataConfirm()) {
                GeomEleBussiness.getInstance().getGeomElement(info.getEleType(), info.getUuid(), new OkHttpClientManager.ResultCallback<SimpleRestResult>() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(SimpleRestResult result) {
                        if (result != null && result.isSuccess()) {
                            try {
                                Gson gson = GsonUtil.create();
                                if (result.getData() != null) {
                                    String jsonStr = gson.toJson(result.getData());
                                    KeyUnitDetailDTO keyUnitDetailDTO = gson.fromJson(jsonStr, KeyUnitDetailDTO.class);
                                    KeyUnitDTO keyUnitDTO = keyUnitDetailDTO.getKeyUnitDTO();

                                    if(keyUnitDTO != null) {
                                        if (info.getVersion() != null && keyUnitDTO.getVersion() != null) {
                                            if (info.getVersion() < keyUnitDTO.getVersion()) {
                                                // ID:886048 数据详细界面，不提示是否更新最新版本数据，自动更新最新数据。 2019-02-22 王旭 START
                                                if (mListener != null) {
                                                    mListener.onRefresh();
                                                } else {
                                                    Toast.makeText(getActivity(), "更新失败", Toast.LENGTH_SHORT).show();
                                                }
                                                // ID:886048 数据详细界面，不提示是否更新最新版本数据，自动更新最新数据。 2019-02-22 王旭 END
                                            }
                                        } else {
                                            if (info.getVersion() == null) {
                                                // ID:886048 数据详细界面，不提示是否更新最新版本数据，自动更新最新数据。 2019-02-22 王旭 START
                                                if (mListener != null) {
                                                    mListener.onRefresh();
                                                } else {
                                                    Toast.makeText(getActivity(), "更新失败", Toast.LENGTH_SHORT).show();
                                                }
                                                // ID:886048 数据详细界面，不提示是否更新最新版本数据，自动更新最新数据。 2019-02-22 王旭 END
                                            }
                                        }
                                    }
                                }
                            } catch (Exception ex) {

                            }
                        }
                    }
                });
            } else {
                doRefresh();
            }
        }
    }

    @Override
    protected void onInitData(IGeomElementInfo obj) {
        long startTime = System.currentTimeMillis();
        resetDataInfo();

        //ID:B886058 19-02-21 【数据】查看详细重点单位界面，响应速度慢，黑屏。 王泉
        loadKeyUnitMediaInfos();
        loadBaseMediaInfos();
        createComLocation(obj);
        loadBaseMediaInfos();
        loadPartInfo();
        switch (curSelected) {
            case 0:
                getBaseInfo();
                break;
            default:
                break;
        }
        LogUtils.d(String.format("onInitData time = %fs", (System.currentTimeMillis() - startTime) / 1000f));
    }

    @Override
    protected void onInitDataNew(IGeomElementInfo obj) {
        resetDataInfo();
        loadBaseMediaInfos();
        createComLocation(obj);

        switch (curSelected) {
            case 0:
                getBaseInfo();
                break;
            default:
                break;
        }
    }

    private void resetDataInfo() {
        partitionInfoMap.clear();
        fireFacilityInfoMap.clear();
        keyPartInfoMap.clear();
        mAttachMap.clear();
        keyUnitMediaInfos.clear();
    }

    @Override
    protected void onPreInitView() {
        View parent = getActivity().getWindow().getDecorView();
        rl_new = (RelativeLayout) parent.findViewById(R.id.rl_new);
        iv_new = (ImageView) parent.findViewById(R.id.iv_new);
        iv_new_no = (ImageView) parent.findViewById(R.id.iv_new_no);

        rl_new.setVisibility(View.VISIBLE);
        rl_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onInitView() {
        if(ll_tab.getChildCount() <= 0) {
                View holdView = getActivity().getLayoutInflater().inflate(R.layout.child_zddw_tab, null);
            ll_tab.addView(holdView);

            RelativeLayout rl_base_info = (RelativeLayout) holdView.findViewById(R.id.rl_base_info);
            rl_base_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (curSelected == 0) {
                        return;
                    }

                    imageList.get(curSelected).setSelected(false);
                    textList.get(curSelected).setTextColor(0xFF575757);

                    curSelected = 0;
                    imageList.get(curSelected).setSelected(true);
                    textList.get(curSelected).setTextColor(0xFFFF801A);

                    doBaseInfo();
                }
            });

            RelativeLayout rl_zone_info = (RelativeLayout) holdView.findViewById(R.id.rl_zone_info);
            rl_zone_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (curSelected == 1) {
                        return;
                    }

                    imageList.get(curSelected).setSelected(false);
                    textList.get(curSelected).setTextColor(0xFF575757);

                    curSelected = 1;
                    imageList.get(curSelected).setSelected(true);
                    textList.get(curSelected).setTextColor(0xFFFF801A);

                    doZoneInfo();
                }
            });

            RelativeLayout rl_fire_info = (RelativeLayout) holdView.findViewById(R.id.rl_fire_info);
            rl_fire_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (curSelected == 2) {
                        return;
                    }

                    imageList.get(curSelected).setSelected(false);
                    textList.get(curSelected).setTextColor(0xFF575757);

                    curSelected = 2;
                    imageList.get(curSelected).setSelected(true);
                    textList.get(curSelected).setTextColor(0xFFFF801A);

                    doFireInfo();
                }
            });

            RelativeLayout rl_important_info = (RelativeLayout) holdView.findViewById(R.id.rl_important_info);
            rl_important_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (curSelected == 3) {
                        return;
                    }

                    imageList.get(curSelected).setSelected(false);
                    textList.get(curSelected).setTextColor(0xFF575757);

                    curSelected = 3;
                    imageList.get(curSelected).setSelected(true);
                    textList.get(curSelected).setTextColor(0xFFFF801A);

                    doImportantInfo();
                }
            });

            imageList.clear();
            textList.clear();

            imageList.add((ImageView)holdView.findViewById(R.id.iv_base_info));
            imageList.add((ImageView)holdView.findViewById(R.id.iv_zone_info));
            imageList.add((ImageView)holdView.findViewById(R.id.iv_fire_info));
            imageList.add((ImageView)holdView.findViewById(R.id.iv_important_info));

            textList.add((TextView)holdView.findViewById(R.id.tv_base_info));
            textList.add((TextView)holdView.findViewById(R.id.tv_zone_info));
            textList.add((TextView)holdView.findViewById(R.id.tv_fire_info));
            textList.add((TextView)holdView.findViewById(R.id.tv_important_info));

            imageList.get(curSelected).setSelected(true);
            textList.get(curSelected).setTextColor(0xFFFF801A);
        }
    }

    @Override
    protected void onInitPopNav() {
        RelativeLayout optionView = (RelativeLayout) getView().findViewById(R.id.rl_nav);
        optionView.setVisibility(View.VISIBLE);

        List<Dictionary> contList = new ArrayList<>();
        contList.add(new Dictionary("二维码", "4"));
        contList.add(new Dictionary("关键图示", "5"));
        if (!AppDefs.ActionType.NEW.toString().equals(operType)) {
            contList.add(new Dictionary("全景漫游", "6"));
            contList.add(new Dictionary("三维场景", "7"));
            contList.add(new Dictionary("旧版预案", "8"));
//                    contList.add(new Dictionary("微信分享全景", "9"));
            contList.add(new Dictionary("处置要点", "10"));
        }
        if (!AppDefs.ActionType.NEW.toString().equals(operType)) {
            contList.add(new Dictionary("在地图显示", "0"));
        }
        contList.addAll(NavService.getInstance(getActivity()).getNavList());

        setOptionNav(contList);
    }

    @Override
    protected void onSelectNavOperation(String code) {
        if ("0".equals(code)) {
            doFinish(true);
        } else if ("4".equals(code)) { //二维码
            startQRCode();
        } else if ("5".equals(code)) { //关键图示
            startPickImageActivity();
        } else if ("6".equals(code)) { //全景漫游
            startPanoramaActivity();
        } else if ("7".equals(code)) { //三维场景
            Toast.makeText(getActivity(), "无三维场景！",
                    Toast.LENGTH_SHORT).show();
        } else if ("8".equals(code)) { //旧版预案
            startOldPreplan();
        } else if ("10".equals(code)) {
            startDisposalPoint();
        } else {
            startMapNav(code);
        }
    }

    private void startQRCode() {
        Intent intent = new Intent(getActivity(), QRCodeActivity.class);
        intent.putExtra("type", mGeomEleDBInfo.getEleType());
        intent.putExtra("key", mGeomEleDBInfo.getUuid());
        intent.putExtra("city", mGeomEleDBInfo.getBelongtoGroup());
        getActivity().startActivity(intent);
    }

    private void startPickImageActivity() {
        List<IMediaInfo> mediaInfos = getPickMediaInfos();
        EleCondition ec = new EleCondition();
        ec.setUuid(mGeomEleDBInfo.getUuid());
        ec.setName(mGeomEleDBInfo.getName());
        ec.setType(mGeomEleDBInfo.getEleType());
        ec.setCityCode(mGeomEleDBInfo.getBelongtoGroup());
        ec.setHistory(isHistory);
        Intent intent = new Intent(getActivity(), PickOrTakeImageActivity.class);
        intent.putExtra("EleCondition", ec);
        intent.putExtra("pick_media_infos", (Serializable) mediaInfos);
        intent.putExtra("isEditable", isEditable);
        getActivity().startActivity(intent);
    }

    private void startPanoramaActivity() {
        KeyUnitMediaDBInfo info = getPanoMediaInfos();
        if(info != null) {
            Intent intent = new Intent(getActivity(), PanoramaActivity.class);
            intent.putExtra("pano_url", info.getMediaUrl());
            getActivity().startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "无全景漫游！", Toast.LENGTH_SHORT).show();
        }
    }

    // 【HW-857417】  2019-01-24  宋满意  重点单位预案显示可以查看多个旧版预案
    private void startOldPreplan() {
        List<IMediaInfo> mediaInfos = getPreplanMediaInfos();
        if (mediaInfos == null || mediaInfos.isEmpty()) {
            Toast.makeText(getActivity(), "无旧版预案！",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        EleCondition ec = new EleCondition();
        ec.setUuid(mGeomEleDBInfo.getUuid());
        ec.setName(mGeomEleDBInfo.getName());
        ec.setType(mGeomEleDBInfo.getEleType());
        ec.setCityCode(mGeomEleDBInfo.getBelongtoGroup());
        ec.setHistory(isHistory);
        Intent intent = new Intent(getActivity(), OldPlanActivity.class);
        intent.putExtra("EleCondition", ec);
        intent.putExtra("pick_media_infos", (Serializable) mediaInfos);
        getActivity().startActivity(intent);
    }

    // 重点单位对应处置要点 edit by wangx 2019-08-08
    private void startDisposalPoint() {
        reuestDisposalPointByKeyUnit(mGeomEleDBInfo.getUuid());
    }

    private void reuestDisposalPointByKeyUnit(final String uuid) {
        if (StringUtil.isEmpty(uuid)) {
            return;
        }
        OkHttpClientManager http = OkHttpClientManager.getInstance();
        http.getAsyn(http.convertToURL(String.format(Constant.METHOD_GET_KEY_UNIT_DISPOSAL_POINTS, uuid)), new OkHttpClientManager.ResultCallback<ArrayRestResult>() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(getActivity(), "网络异常，请检查网络是否正常！",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(ArrayRestResult result) {
                if (result != null && result.isSuccess()) {
                    Gson gson = GsonUtil.create();
                    if (result.getContents() != null) {
                        String jsonStr = gson.toJson(result.getContents());
                        Type listType = new TypeToken<List<KeyUnitDisposalPointDTO>>(){}.getType();
                        List<KeyUnitDisposalPointDTO> kuPoints = gson.fromJson(jsonStr, listType);
                        if (kuPoints.isEmpty()) {
                            Toast.makeText(getActivity(), "暂无处置要点！", Toast.LENGTH_SHORT).show();
                        } else {
                            EleCondition ec = new EleCondition();
                            ec.setUuid(mGeomEleDBInfo.getUuid());
                            ec.setName(mGeomEleDBInfo.getName());
                            Intent intent = new Intent(getActivity(), KeyUnitDisposalPointActivity.class);
                            intent.putExtra("EleCondition", ec);
                            intent.putExtra("key_unit_disposal_points", (Serializable) kuPoints);
                            intent.putExtra("key_unit_uuid", uuid);
                            getActivity().startActivity(intent);
                        }

                    } else {
                        Toast.makeText(getActivity(), "暂无处置要点！", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "网络异常，请检查网络是否正常！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startMapNav(String code) {
        ComLocation cl = mComLocationMap.get(mGeomEleDBInfo.getUuid());
        if (cl == null || cl.isInvalid()) {
            Toast.makeText(getActivity(), "无效地址！",
                    Toast.LENGTH_SHORT).show();
        } else {
            NavService.getInstance(getActivity()).doStartNavi(
                    code, cl.getLat(), cl.getLng(), cl.getName());
        }
    }

    private List<IMediaInfo> getPickMediaInfos() {
        List<IMediaInfo> mediaInfos = new ArrayList<>();

        if(pickMediaInfos != null && pickMediaInfos.size() > 0) {
            for(IMediaInfo item : pickMediaInfos) {
                KeyUnitMediaDBInfo info = (KeyUnitMediaDBInfo)item;
                if(!AppDefs.KeyUnitMediaType.KU_MEDIA_IMAGE.toString().equals(info.getClassification()) &&
                        !AppDefs.KeyUnitMediaType.KU_MEDIA_PANO.toString().equals(info.getClassification()) &&
                        !AppDefs.KeyUnitMediaType.KU_MEDIA_MX.toString().equals(info.getClassification()) &&
                        !AppDefs.KeyUnitMediaType.KU_MEDIA_PRE_PALN.toString().equals(info.getClassification())) {
                    mediaInfos.add(info);
                }
            }
        }
        return mediaInfos;
    }

    private List<IMediaInfo> getPreplanMediaInfos() {
        List<IMediaInfo> mediaInfos = new ArrayList<>();

        if(pickMediaInfos != null && pickMediaInfos.size() > 0) {
            for(IMediaInfo item : pickMediaInfos) {
                KeyUnitMediaDBInfo info = (KeyUnitMediaDBInfo)item;
                if(AppDefs.KeyUnitMediaType.KU_MEDIA_PRE_PALN.toString().equals(info.getClassification())) {
                    mediaInfos.add(info);
                }
            }
        }
        return mediaInfos;
    }

    private KeyUnitMediaDBInfo getPanoMediaInfos() {
        KeyUnitMediaDBInfo retInfo = null;

        if(pickMediaInfos != null && pickMediaInfos.size() > 0) {
            for(IMediaInfo item : pickMediaInfos) {
                KeyUnitMediaDBInfo info = (KeyUnitMediaDBInfo)item;
                if(AppDefs.KeyUnitMediaType.KU_MEDIA_PANO.toString().equals(info.getClassification())) {
                    retInfo = info;
                    break;
                }
            }
        }
        return retInfo;
    }

    @Override
    public void doMore() {
        showPopNav();
    }

    private void setNewEnable() {
        rl_new.setEnabled(true);
        iv_new.setVisibility(View.VISIBLE);
        iv_new_no.setVisibility(View.INVISIBLE);
    }

    private void setNewDisable() {
        rl_new.setEnabled(false);
        iv_new.setVisibility(View.INVISIBLE);
        iv_new_no.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPropertyInit() {
        if (isEditable) {
            if(curSelected == 1 || curConditionPart != null) {
                setNewEnable();
            } else {
                setNewDisable();
            }
        } else {
            setNewDisable();
        }

        if(curSelected != 0) {
            updateKeyUnitPartView();
        } else {
            if (mCurPropertyLogic == null) {
                mCurPropertyLogic = new PropertyLogic(ll_content, getActivity());
                mCurPropertyLogic.setEditable(isEditable);
                mCurPropertyLogic.setPdListDetail(pdListDetail);
                mCurPropertyLogic.setListener(new PropertyLogic.OnPropertyLogicListener() {
                    @Override
                    public void onSetCameraLogic(CameraLogic cl) {
                        mCurCameraLogic = cl;
                    }

                    @Override
                    public boolean onDelete(PropertyDes pd) {
                        return true;
                    }

                    public void onAttach(PropertyDes pd, String path) {

                    }

                    @Override
                    public ComLocation onGetComLocation(PropertyDes pd) {
                        return mComLocationMap.get(pd.getEntityUuid());
                    }

                    @Override
                    public String onGetCityCode() {
                        return mCityCode;
                    }

                    @Override
                    public boolean onGetIsHistory() {
                        return isHistory;
                    }
                });
                mCurPropertyLogic.init();

            } else {
                pdListDetail = mCurPropertyLogic.getPdListDetail();
                mCurPropertyLogic.setEditable(isEditable);
                mCurPropertyLogic.refresh(pdListDetail);
            }
        }
    }

    private void updateKeyUnitPartView() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.key_unit_part_view, null);
        ExpandableListView expandableListView = (ExpandableListView)view.findViewById(R.id.expand_list_view);
        expandableListView.setGroupIndicator(null);
        BaseExpandableListAdapter adapter = null;
        if(curSelected == 1) {
            List<PartitionCategary> list = getPartitionCategaries();
            adapter = getPartitionAdapter(expandableListView, list);
            expandableListView.setAdapter(adapter);
            for(int index = 0; index < list.size(); ++index) {
                expandableListView.expandGroup(index);
            }
        } else if(curSelected == 2) {
            List<FireFacilityCategary> list = getFireFacilityCategaries();
            adapter = getFireFacilityAdapter(expandableListView, list);
            expandableListView.setAdapter(adapter);
            for(int index = 0; index < list.size(); ++index) {
                expandableListView.expandGroup(index);
            }
        } else if(curSelected == 3) {
            List<KeyPartCategary> list = getKeyPartCategaries();
            adapter = getKeyPartAdapter(expandableListView, list);
            expandableListView.setAdapter(adapter);
            for(int index = 0; index < list.size(); ++index) {
                expandableListView.expandGroup(index);
            }
        }
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        view.setLayoutParams(layoutParams);
        ll_content.addView(view);
    }

    private List<PartitionCategary> getPartitionCategaries() {
        List<PartitionCategary> partitionCategaries = new ArrayList<>();
        List<IPartitionInfo> list = new ArrayList<>();
        for(IPartitionInfo partitionInfo : partitionInfoMap.values()) {
            if(rootPartition != null && rootPartition.getUuid().equals(partitionInfo.getBelongToPartitionUuid())) {
                list.add(partitionInfo);
            }
        }
        if(rootPartition != null) {
            partitionCategaries.add(new PartitionCategary(rootPartition.getName(), rootPartition.getAddress(), list));
        } else {
            partitionCategaries.add(new PartitionCategary("单位总分区", "", list));
        }
        return partitionCategaries;
    }

    private List<FireFacilityCategary> getFireFacilityCategaries() {
        List<FireFacilityCategary> fireFacilityCategaries = new ArrayList<>();
        Map<String, List<IFireFacilityInfo>> infoMap = new HashMap<>();
        List<Dictionary> dicList = LvApplication.getInstance().getCompDicMap().get("FIRE_FACILITY");
        for(IFireFacilityInfo facilityInfo : fireFacilityInfoMap.values()) {
            String facilityType = facilityInfo.getFacilityType();
            Dictionary dictionary = DictionaryUtil.getDictionaryByCode(facilityType, dicList);
            if(dictionary != null) {
                if (!StringUtil.isEmpty(dictionary.getId()) && dictionary.getId().length() > 2) {
                    Dictionary parentDic = DictionaryUtil.getDictionaryById(dictionary.getId().substring(0, 2), dicList);
                    facilityType = parentDic.getValue();
                } else {
                    facilityType = dictionary.getValue();
                }
            } else {
                facilityType = DictionaryUtil.getValueByCode(facilityType, dicList);
            }
            if (infoMap.containsKey(facilityType)) {
                infoMap.get(facilityType).add(facilityInfo);
            } else {
                List<IFireFacilityInfo> newFireFacilitys = new ArrayList<>();
                newFireFacilitys.add(facilityInfo);
                infoMap.put(facilityType, newFireFacilitys);
            }
        }
        for(Map.Entry<String, List<IFireFacilityInfo>> item : infoMap.entrySet()) {
            fireFacilityCategaries.add(new FireFacilityCategary(item.getKey(), item.getValue()));
        }
        return fireFacilityCategaries;
    }

    private List<KeyPartCategary> getKeyPartCategaries() {
        List<KeyPartCategary> keyPartCategaries = new ArrayList<>();
        Map<String, List<IKeyPartInfo>> infoMap = new HashMap<>();
        List<Dictionary> dicList = LvApplication.getInstance().getCompDicMap().get("KEY_PART");
        for(IKeyPartInfo keyPartInfo : keyPartInfoMap.values()) {
            String typeName = DictionaryUtil.getValueByCode(keyPartInfo.getKeyPartType(), dicList);
            if(infoMap.containsKey(typeName)) {
                infoMap.get(typeName).add(keyPartInfo);
            } else {
                List<IKeyPartInfo> newKeyParts = new ArrayList<>();
                newKeyParts.add(keyPartInfo);
                infoMap.put(typeName, newKeyParts);
            }
        }
        for(Map.Entry<String, List<IKeyPartInfo>> item : infoMap.entrySet()) {
            keyPartCategaries.add(new KeyPartCategary(item.getKey(), item.getValue()));
        }
        return keyPartCategaries;
    }

    private PartitionExpandableAdapter getPartitionAdapter(final ExpandableListView listView, List<PartitionCategary> list) {
        PartitionExpandableAdapter partitionAdapter = new PartitionExpandableAdapter(getActivity(), list);
        partitionAdapter.setOnGroupClickListener(new PartitionExpandableAdapter.OnGroupClickListener() {
            @Override
            public void onGroupClick(View view, int position) {
                boolean isExpanded = listView.isGroupExpanded(position);
                if (isExpanded) {
                    listView.collapseGroup(position);
                } else {
                    listView.expandGroup(position);
                }
            }

            @Override
            public void onItemClick(View view) {
                IPartitionInfo info = (IPartitionInfo)view.getTag();
                if(info != null) {
                    startKeyUnitPartitionDetailActivity(info);
                }
            }
        });

        return partitionAdapter;
    }

    private FireFacilityExpandableAdapter getFireFacilityAdapter(final ExpandableListView listView, List<FireFacilityCategary> list) {
        FireFacilityExpandableAdapter fireFacilityAdapter = new FireFacilityExpandableAdapter(getActivity(), list);
        fireFacilityAdapter.setOnGroupClickListener(new FireFacilityExpandableAdapter.OnGroupClickListener() {
            @Override
            public void onGroupClick(View view, int position) {
                boolean isExpanded = listView.isGroupExpanded(position);
                if (isExpanded) {
                    listView.collapseGroup(position);
                } else {
                    listView.expandGroup(position);
                }
            }

            @Override
            public void onItemClick(View view) {
                IFireFacilityInfo info = (IFireFacilityInfo)view.getTag();
                if(info != null) {
                    startKeyUnitFireFacilityDetailActivity(info);
                }
            }
        });

        return fireFacilityAdapter;
    }

    private KeyPartExpandableAdapter getKeyPartAdapter(final ExpandableListView listView, List<KeyPartCategary> list) {
        KeyPartExpandableAdapter keyPartAdapter = new KeyPartExpandableAdapter(getActivity(), list);
        keyPartAdapter.setOnGroupClickListener(new KeyPartExpandableAdapter.OnGroupClickListener() {
            @Override
            public void onGroupClick(View view, int position) {
                boolean isExpanded = listView.isGroupExpanded(position);
                if (isExpanded) {
                    listView.collapseGroup(position);
                } else {
                    listView.expandGroup(position);
                }
            }

            @Override
            public void onItemClick(View view) {
                IKeyPartInfo info = (IKeyPartInfo)view.getTag();
                if(info != null) {
                    startKeyUnitKeyPartDetailActivity(info);
                }
            }
        });

        return keyPartAdapter;
    }

    private void startKeyUnitPartitionDetailActivity(IPartitionInfo info) {
        Intent intent = new Intent(getActivity(), KeyUnitPartitionDetailActivity.class);
        intent.putExtra("is_history", isHistory);
        intent.putExtra("is_editable", isEditable);
        intent.putExtra("info", info);
        intent.putExtra("key_unit_uuid", mGeomEleDBInfo.getUuid());

        List<IPartitionInfo> subPartitionList = getSubPartitionList(info.getUuid());
        intent.putExtra("sub_partition_info", (Serializable) subPartitionList);
        intent.putExtra("media_info", (Serializable) mAttachMap.get(info.getUuid()));
        intent.putExtra("sub_media_info", (Serializable) getSubPartitionMediaMap(subPartitionList));
        getActivity().startActivity(intent);
    }

    private void startKeyUnitFireFacilityDetailActivity(IFireFacilityInfo info) {
        Intent intent = new Intent(getActivity(), KeyUnitFireFacilityDetailActivity.class);
        intent.putExtra("is_history", isHistory);
        intent.putExtra("is_editable", isEditable);
        intent.putExtra("info", info);
        intent.putExtra("key_unit_uuid", mGeomEleDBInfo.getUuid());
        intent.putExtra("root_partition_info", (Serializable) rootPartition);
        intent.putExtra("partition_info", (Serializable) getPartitionList());
        intent.putExtra("key_diagram_media_info", (Serializable)getPickMediaInfos());
        intent.putExtra("media_info", (Serializable) mAttachMap.get(info.getUuid()));
        getActivity().startActivity(intent);
    }

    private void startKeyUnitKeyPartDetailActivity(IKeyPartInfo info) {
        Intent intent = new Intent(getActivity(), KeyUnitKeyPartDetailActivity.class);
        intent.putExtra("is_history", isHistory);
        intent.putExtra("is_editable", isEditable);
        intent.putExtra("info", info);
        intent.putExtra("key_unit_uuid", mGeomEleDBInfo.getUuid());
        intent.putExtra("partition_info", (Serializable) getPartitionListByType(info.getKeyPartType()));
        intent.putExtra("key_diagram_media_info", (Serializable)getPickMediaInfos());
        intent.putExtra("media_info", (Serializable) mAttachMap.get(info.getUuid()));
        getActivity().startActivityForResult(intent, KeyUnitKeyPartDetailActivity.REQUEST_DETAIL_ACTIVITY_CODE);
    }

    private List<IPartitionInfo> getPartitionList() {
        List<IPartitionInfo> retList = new ArrayList<>();
        for(IPartitionInfo item : partitionInfoMap.values()) {
            retList.add(item);
        }
        return  retList;
    }

    private List<IPartitionInfo> getPartitionListByType(String type) {
        List<IPartitionInfo> retList = new ArrayList<>();
        for(IPartitionInfo item : partitionInfoMap.values()) {
            if(AppDefs.KeyUnitKeyPartType.KEY_PART_STRUCTURE.toString().equals(type)) {
                if(AppDefs.KeyUnitPartitionType.PARTITION_UNIT.toString().equals(item.getPartitionType()) ||
                        AppDefs.KeyUnitPartitionType.PARTITION_STRUCTURE.toString().equals(item.getPartitionType()) ||
                        AppDefs.KeyUnitPartitionType.PARTITION_FLOOR.toString().equals(item.getPartitionType())) {
                    retList.add(item);
                }
            } else if(AppDefs.KeyUnitKeyPartType.KEY_PART_DEVICE.toString().equals(type)) {
                if(AppDefs.KeyUnitPartitionType.PARTITION_UNIT.toString().equals(item.getPartitionType()) ||
                        AppDefs.KeyUnitPartitionType.PARTITION_DEVICE.toString().equals(item.getPartitionType())) {
                    retList.add(item);
                }
            } else if(AppDefs.KeyUnitKeyPartType.KEY_PART_STORAGE.toString().equals(type)) {
                if(AppDefs.KeyUnitPartitionType.PARTITION_UNIT.toString().equals(item.getPartitionType()) ||
                        AppDefs.KeyUnitPartitionType.PARTITION_STORAGE_ZONE.toString().equals(item.getPartitionType()) ||
                        AppDefs.KeyUnitPartitionType.PARTITION_STORAGE.toString().equals(item.getPartitionType())) {
                    retList.add(item);
                }
            }

        }
        return  retList;
    }

    private List<IPartitionInfo> getSubPartitionList(String uuid) {
        List<IPartitionInfo> retList = new ArrayList<>();
        for(IPartitionInfo item : partitionInfoMap.values()) {
            if(uuid.equals(item.getBelongToPartitionUuid())) {
                retList.add(item);
            }
        }
        return  retList;
    }

    private Map<String, List<IMediaInfo>> getSubPartitionMediaMap(List<IPartitionInfo> subPartitionList) {
        Map<String, List<IMediaInfo>> subPartitionMap = new LinkedHashMap<>();
        for(IPartitionInfo item : subPartitionList) {
            if(mAttachMap.containsKey(item.getUuid())) {
                subPartitionMap.put(item.getUuid(), mAttachMap.get(item.getUuid()));
            }
        }
        return subPartitionMap;
    }

    private void doBaseInfo() {
        getBaseInfo();
        initProperty();
    }

    private void doZoneInfo() {
        initProperty();
    }

    private void doFireInfo() {
        initProperty();
    }

    private void doImportantInfo() {
        initProperty();
    }

    //ID:B886058 19-02-21 【数据】查看详细重点单位界面，响应速度慢，黑屏。 王泉
    private void loadKeyUnitMediaInfos() {
        keyUnitMediaInfos = getKeyUnitMediaInfos(mCityCode, mGeomEleDBInfo.getUuid());
    }

    private void loadBaseMediaInfos() {
        // 取得单位现场照片附件
        List<IMediaInfo> attachPicList = getKeyUnitPicInfos(mGeomEleDBInfo.getBelongtoGroup(), mGeomEleDBInfo.getUuid());
        pickMediaInfos = getKeyUnitPickMediaInfos(mGeomEleDBInfo.getBelongtoGroup(), mGeomEleDBInfo.getUuid());

        mAttachMap.put(mGeomEleDBInfo.getUuid(), attachPicList);
    }

    //load所有（分区、消防设施、重点部位信息）
    private void loadPartInfo() {
        long startTime = System.currentTimeMillis();
        //获取单位分区信息，及附件信息（各分区及子分区）
        loadPartitionInfos();
        LogUtils.d(String.format("loadPartitionInfos time = %fs", (System.currentTimeMillis() - startTime) / 1000f));
        //获取单位消防设施，及附件信息
        loadKeyPartInfos();
        LogUtils.d(String.format("loadKeyPartInfos time = %fs", (System.currentTimeMillis() - startTime) / 1000f));
        //获取单位重点部位，及附件信息
        loadFireFacilityInfos();
        LogUtils.d(String.format("loadFireFacilityInfos time = %fs", (System.currentTimeMillis() - startTime) / 1000f));
    }

    private void loadPartitionInfos() {
        List<IPartitionInfo> partitionList = GeomEleBussiness.getInstance().getPartitionInfoByKeyUnitUuid(mCityCode, mGeomEleDBInfo.getUuid(), isHistory);

        for (IPartitionInfo item : partitionList) {
            if(StringUtil.isEmpty(item.getBelongToPartitionUuid())) {
                rootPartition = item;
            }
            // 取得现场照片附件
            List<IMediaInfo> attachFileList = getPartMediaInfos(item.getPartitionType(), item.getUuid());
            createComPartLocation(item.getUuid(), item.getName(), item.getLatitude(), item.getLongitude());
            partitionInfoMap.put(item.getUuid(), item);
            mAttachMap.put(item.getUuid(), attachFileList);
        }
    }

    private void loadKeyPartInfos() {
        //ID:B886058 19-02-21 【数据】查看详细重点单位界面，响应速度慢，黑屏。 王泉 开始
        String uuids = "";
        for(IPartitionInfo partitionInfo : partitionInfoMap.values()) {
            uuids += "'" + partitionInfo.getUuid() + "',";
        }

        if(uuids.length() > 1) {
            uuids = uuids.substring(0, uuids.length() - 1);
            List<IKeyPartInfo> keyPartList = GeomEleBussiness.getInstance().getKeyPartInfoByPartitionUuids(mCityCode, uuids, isHistory);
            for (IKeyPartInfo item : keyPartList) {
                // 取得现场照片附件
                List<IMediaInfo> attachFileList = getPartMediaInfos(item.getKeyPartType(), item.getUuid());

                createComPartLocation(item.getUuid(), item.getName(), item.getLatitude(), item.getLongitude());
                keyPartInfoMap.put(item.getUuid(), item);
                mAttachMap.put(item.getUuid(), attachFileList);
            }
        }
    }

    private void loadFireFacilityInfos() {
        //ID:B886058 19-02-21 【数据】查看详细重点单位界面，响应速度慢，黑屏。 王泉 开始
        String uuids = "";
        for(IPartitionInfo partitionInfo : partitionInfoMap.values()) {
            uuids += "'" + partitionInfo.getUuid() + "',";
        }
        if(uuids.length() > 1) {
            uuids = uuids.substring(0, uuids.length() - 1);
            List<IFireFacilityInfo> fireFacilityList = GeomEleBussiness.getInstance().getFireFacilityInfoByPartitionUuids(mCityCode, uuids, isHistory);
            for (IFireFacilityInfo item : fireFacilityList) {
                List<IMediaInfo> attachFileList = getPartMediaInfos(item.getFacilityType(), item.getUuid());

                createComPartLocation(item.getUuid(), item.getName(), item.getLatitude(), item.getLongitude());
                fireFacilityInfoMap.put(item.getUuid(), item);
                mAttachMap.put(item.getUuid(), attachFileList);
            }
        }
        //ID:B886058 19-02-21 【数据】查看详细重点单位界面，响应速度慢，黑屏。 王泉 结束
    }

    //获取重点单位各part的Uuid
    private List<IMediaInfo> getPartMediaInfos(String partType, String uuid) {
        //ID:B886058 19-02-21 【数据】查看详细重点单位界面，响应速度慢，黑屏。 王泉 开
//        GeomEleBussiness eleBussiness = GeomEleBussiness.getInstance();
//        List<IMediaInfo> afList = eleBussiness.getKeyUnitPartMediaInfos(mCityName, partType, uuid, isHistory);
        List<IMediaInfo> afList = new ArrayList<>();
        for(IMediaInfo item : keyUnitMediaInfos) {
            KeyUnitMediaDBInfo info = (KeyUnitMediaDBInfo)item;
            if(partType.equals(info.getBelongToType()) && uuid.equals(info.getBelongToUuid()) &&
                    AppDefs.KeyUnitMediaType.KU_MEDIA_IMAGE.toString().equals(info.getClassification())) {
                afList.add(info);
            }
        }
        //ID:B886058 19-02-21 【数据】查看详细重点单位界面，响应速度慢，黑屏。 王泉 结束
        return afList;
    }

    //获取重点单位下的所有图片附件（仅仅挂载到重点单位下）
    private List<IMediaInfo> getKeyUnitPicInfos(String group, String uuid) {
        //ID:B886058 19-02-21 【数据】查看详细重点单位界面，响应速度慢，黑屏。 王泉 开始
//        String condition = String.format("%s and %s and %s",
//                String.format(Constant.SEARCH_COND_KEY_UNIT_UUID, uuid),
//                String.format(Constant.SEARCH_COND_BELONG_TO_UUID, uuid),
//                String.format(Constant.SEARCH_COND_CLASSIFICATION, AppDefs.KeyUnitMediaType.KU_MEDIA_IMAGE.toString()));
//
//        List<IMediaInfo> afList = GeomEleBussiness.getInstance().getKeyUnitMediaInfosByCondition(group, condition, isHistory);
        List<IMediaInfo> afList = new ArrayList<>();
        for(IMediaInfo item : keyUnitMediaInfos) {
            KeyUnitMediaDBInfo info = (KeyUnitMediaDBInfo)item;
            if(uuid.equals(info.getKeyUnitUuid()) && uuid.equals(info.getBelongToUuid()) &&
                    AppDefs.KeyUnitMediaType.KU_MEDIA_IMAGE.toString().equals(info.getClassification())) {
                afList.add(info);
            }
        }
        //ID:B886058 19-02-21 【数据】查看详细重点单位界面，响应速度慢，黑屏。 王泉 结束
        return afList;
    }

    //获取重点单位下所有的media信息列表
    private List<IMediaInfo> getKeyUnitMediaInfos(String group, String uuid) {
        String condition = String.format(Constant.SEARCH_COND_KEY_UNIT_UUID, uuid);

        List<IMediaInfo> afList = GeomEleBussiness.getInstance().getKeyUnitMediaInfosByCondition(group, condition, isHistory);

        return afList;
    }

    //获取重点单位下的关键media附件（仅仅挂载到重点单位下）
    private List<IMediaInfo> getKeyUnitPickMediaInfos(String group, String uuid) {
        //ID:B886058 19-02-21 【数据】查看详细重点单位界面，响应速度慢，黑屏。 王泉 开始
//        String condition = String.format("%s and %s",
//                String.format(Constant.SEARCH_COND_KEY_UNIT_UUID, uuid),
////                String.format(Constant.SEARCH_COND_BELONG_TO_UUID, uuid),
//                String.format(Constant.SEARCH_COND_CLASSIFICATION_NOT_EQUALS, AppDefs.KeyUnitMediaType.KU_MEDIA_IMAGE.toString()));
//
//        List<IMediaInfo> afList = GeomEleBussiness.getInstance().getKeyUnitMediaInfosByCondition(group, condition, isHistory);
        List<IMediaInfo> afList = new ArrayList<>();
        for(IMediaInfo item : keyUnitMediaInfos) {
            KeyUnitMediaDBInfo info = (KeyUnitMediaDBInfo)item;
            if(uuid.equals(info.getKeyUnitUuid()) && !AppDefs.KeyUnitMediaType.KU_MEDIA_IMAGE.toString().equals(info.getClassification())) {
                afList.add(info);
            }
        }
        //ID:B886058 19-02-21 【数据】查看详细重点单位界面，响应速度慢，黑屏。 王泉 结束
        return afList;
    }

    private void getBaseInfo() {
        if (mCurPropertyLogic != null) {
            return;
        }

        pdListDetail = mGeomEleDBInfo.getPdListDetail();
        //获取地理信息和附件信息存放在pdListDetailViewAll中
        addPropertyDesMapPos(pdListDetail, mGeomEleDBInfo.getUuid());
        pdListDetail.add(new PropertyDes(PropertyDes.TYPE_PHOTO, mAttachMap.get(mGeomEleDBInfo.getUuid()), mGeomEleDBInfo.getUuid()));
    }

    private void createComPartLocation(String uuid, String name, String latitude, String longitude) {
        ComLocation cl = new ComLocation();
        cl.setLat(Utils.convertToDouble(latitude));
        cl.setLng(Utils.convertToDouble(longitude));
        cl.setName(name);
        cl.setEleUuid(uuid);
        mComLocationMap.put(uuid, cl);
    }
}
