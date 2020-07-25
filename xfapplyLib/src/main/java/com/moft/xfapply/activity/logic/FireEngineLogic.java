package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.FireEnginePartDetailActivity;
import com.moft.xfapply.activity.KeyUnitPartitionDetailActivity;
import com.moft.xfapply.activity.WsDetailActivity;
import com.moft.xfapply.activity.adapter.FireEngineEquipmentAdapter;
import com.moft.xfapply.activity.adapter.FireEngineExtinguishingAgentAdapter;
import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.logic.network.OkHttpClientManager;
import com.moft.xfapply.model.base.IGeomElementInfo;
import com.moft.xfapply.model.base.IMediaInfo;
import com.moft.xfapply.model.common.ComLocation;
import com.moft.xfapply.model.common.PropertyDes;
import com.moft.xfapply.model.entity.MaterialEquipmentDBInfo;
import com.moft.xfapply.model.entity.MaterialExtinguishingAgentDBInfo;
import com.moft.xfapply.model.entity.MaterialMediaDBInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.dto.FireVehicleDTO;
import com.moft.xfapply.model.external.result.SimpleRestResult;
import com.moft.xfapply.utils.GsonUtil;
import com.moft.xfapply.utils.LogUtils;
import com.moft.xfapply.widget.NoScrollListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class FireEngineLogic extends DetailLogic {
    private RelativeLayout rl_new = null;
    private ImageView iv_new = null;
    private ImageView iv_new_no = null;

    private int curSelected = 0;
    private boolean isNew = false;
    private List<ImageView> imageList = new ArrayList<>();
    private List<TextView> textList = new ArrayList<>();

    private List<PropertyDes> pdListDetail = null;
    private HashMap<String, List<IMediaInfo>> mAttachMap = new LinkedHashMap<>();
    private Map<String, MaterialExtinguishingAgentDBInfo> extinguishingAgentInfoMap = new LinkedHashMap<>();
    private Map<String, MaterialEquipmentDBInfo> equipmentInfoMap = new LinkedHashMap<>();

    public FireEngineLogic(View v, Activity a) {
        super(v, a);
    }

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
                                    FireVehicleDTO fireVehicleDTO = gson.fromJson(jsonStr, FireVehicleDTO.class);
                                    if (info.getVersion() != null && fireVehicleDTO.getVersion() != null) {
                                        if (info.getVersion() < fireVehicleDTO.getVersion()) {
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
    protected void onInitData(IGeomElementInfo info) {
        long startTime = System.currentTimeMillis();
        isNew = false;
        resetDataInfo();
        loadBaseMediaInfos();
        loadPartInfo();
        if(curSelected == 0) {
            updateEngineInfo();
        }
        LogUtils.d(String.format("onInitData time = %fs", (System.currentTimeMillis() - startTime) / 1000f));
    }

    @Override
    protected void onInitDataNew(IGeomElementInfo info) {
        isNew = true;
        resetDataInfo();
        loadBaseMediaInfos();
        if(curSelected == 0) {
            updateEngineInfo();
        }
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
            View holdView = getActivity().getLayoutInflater().inflate(R.layout.child_fire_engine_tab, null);
            ll_tab.addView(holdView);

            RelativeLayout rl_engine_info = (RelativeLayout) holdView.findViewById(R.id.rl_engine_info);
            rl_engine_info.setOnClickListener(new View.OnClickListener() {
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

                    updateEngineInfo();
                }
            });

            RelativeLayout rl_mhj_info = (RelativeLayout) holdView.findViewById(R.id.rl_mhj_info);
            rl_mhj_info.setOnClickListener(new View.OnClickListener() {
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

                    updateExtinguishingAgentInfo();
                }
            });

            RelativeLayout rl_equipment_info = (RelativeLayout) holdView.findViewById(R.id.rl_equipment_info);
            rl_equipment_info.setOnClickListener(new View.OnClickListener() {
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

                    updateEquipmentInfo();
                }
            });

            imageList.clear();
            textList.clear();

            imageList.add((ImageView)holdView.findViewById(R.id.iv_engine_info));
            imageList.add((ImageView)holdView.findViewById(R.id.iv_mhj_info));
            imageList.add((ImageView)holdView.findViewById(R.id.iv_equipment_info));

            textList.add((TextView)holdView.findViewById(R.id.tv_engine_info));
            textList.add((TextView)holdView.findViewById(R.id.tv_mhj_info));
            textList.add((TextView)holdView.findViewById(R.id.tv_equipment_info));
            imageList.get(curSelected).setSelected(true);
            textList.get(curSelected).setTextColor(0xFFFF801A);
        }
    }

    @Override
    protected void onInitPopNav() {

    }

    @Override
    protected void onSelectNavOperation(String code) {

    }

    @Override
    protected void onPropertyInit() {
        if(rl_new != null) {
            if (isEditable) {
                if (curSelected == 0) {
                    setNewDisable();
                } else {
                    setNewEnable();
                }
            } else {
                setNewDisable();
            }
        }

        if(curSelected != 0) {
            updateFireEnginePartView();
        } else {
            if (mCurPropertyLogic == null) {
                mCurPropertyLogic = new PropertyLogic(ll_content, getActivity());
                mCurPropertyLogic.clear();
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

                    @Override
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

    private void updateFireEnginePartView() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fire_engine_part_view, null);
        NoScrollListView listView = (NoScrollListView)view.findViewById(R.id.list_view);

        if(curSelected == 1) {
            setFireEngineExtinguishingAgentAdapter(listView);
        } else if(curSelected == 2) {
            setFireEngineEquipmentAdapter(listView);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                IGeomElementInfo info = (IGeomElementInfo)view.getTag();

                if(info != null) {
                    startFireEnginePartDetailActivity(info);
                }
            }
        });
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        view.setLayoutParams(layoutParams);
        ll_content.addView(view);
    }

    private void setFireEngineEquipmentAdapter(NoScrollListView listView) {
        final List<MaterialEquipmentDBInfo> equipmentList = new ArrayList<>();
        for(MaterialEquipmentDBInfo item : equipmentInfoMap.values()) {
            equipmentList.add(item);
        }
        FireEngineEquipmentAdapter equipmentAdapter = new FireEngineEquipmentAdapter(getActivity(), equipmentList);
        listView.setAdapter(equipmentAdapter);
    }

    private void setFireEngineExtinguishingAgentAdapter(NoScrollListView listView) {
        final List<MaterialExtinguishingAgentDBInfo> extinguishingAgentList = new ArrayList<>();
        for(MaterialExtinguishingAgentDBInfo item : extinguishingAgentInfoMap.values()) {
            extinguishingAgentList.add(item);
        }
        FireEngineExtinguishingAgentAdapter extinguishingAgentAdapter = new FireEngineExtinguishingAgentAdapter(getActivity(), extinguishingAgentList);
        listView.setAdapter(extinguishingAgentAdapter);
    }

    private void startFireEnginePartDetailActivity(IGeomElementInfo info) {
        Intent intent = new Intent(getActivity(), FireEnginePartDetailActivity.class);
        intent.putExtra("is_history", isHistory);
        intent.putExtra("is_editable", isEditable);
        intent.putExtra("info", info);
        intent.putExtra("media_info", (Serializable) mAttachMap.get(info.getUuid()));
        getActivity().startActivity(intent);
    }

    private void resetDataInfo() {
        mAttachMap.clear();
        extinguishingAgentInfoMap.clear();
        equipmentInfoMap.clear();
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

    private void loadBaseMediaInfos() {
        List<IMediaInfo> attachPicList = getAttachFileList(mGeomEleDBInfo);
        mAttachMap.put(mGeomEleDBInfo.getUuid(), attachPicList);
    }

    private void loadPartInfo() {
        long startTime = System.currentTimeMillis();
        loadExtinguishingAgentInfo();
        LogUtils.d(String.format("loadExtinguishingAgentInfo time = %fs", (System.currentTimeMillis() - startTime) / 1000f));
        loadEquipmentInfo();
        LogUtils.d(String.format("loadEquipmentInfo time = %fs", (System.currentTimeMillis() - startTime) / 1000f));
    }

    private void loadExtinguishingAgentInfo() {
        GeomEleBussiness eleBussiness = GeomEleBussiness.getInstance();
        List<MaterialExtinguishingAgentDBInfo> list = eleBussiness.getExtinguishingAgentInfosByStorageUuid(mCityCode, mGeomEleDBInfo.getUuid(), isHistory);
        String uuids = "";
        for (MaterialExtinguishingAgentDBInfo item : list) {
            uuids += "'" + item.getUuid() + "',";
            extinguishingAgentInfoMap.put(item.getUuid(), item);
        }
        if(uuids.length() > 1) {
            uuids = uuids.substring(0, uuids.length() - 1);
            // 取得现场照片附件
            Map<String, List<IMediaInfo>> mediaList = getPartGroupMediaList(getPartAttachFileList(AppDefs.CompEleType.EXTINGUISHING_AGENT.toString(), uuids));
            if(mediaList.size() > 0) {
                mAttachMap.putAll(mediaList);
            }
        }
    }

    private void loadEquipmentInfo() {
        GeomEleBussiness eleBussiness = GeomEleBussiness.getInstance();
        List<MaterialEquipmentDBInfo> list = eleBussiness.getEquipmentInfosByStorageUuid(mCityCode, mGeomEleDBInfo.getUuid(), isHistory);
        String uuids = "";
        for (MaterialEquipmentDBInfo item : list) {
            uuids += "'" + item.getUuid() + "',";
            equipmentInfoMap.put(item.getUuid(), item);
        }
        if(uuids.length() > 1) {
            uuids = uuids.substring(0, uuids.length() - 1);
            // 取得现场照片附件
            Map<String, List<IMediaInfo>> mediaList = getPartGroupMediaList(getPartAttachFileList(AppDefs.CompEleType.EQUIPMENT.toString(), uuids));
            if(mediaList.size() > 0) {
                mAttachMap.putAll(mediaList);
            }
        }
    }

    private Map<String, List<IMediaInfo>> getPartGroupMediaList(List<MaterialMediaDBInfo> mediaList) {
        Map<String, List<IMediaInfo>> retMap = new LinkedHashMap<>();
        if(mediaList != null && mediaList.size() > 0) {
            for(MaterialMediaDBInfo item : mediaList) {
                if(retMap.containsKey(item.getMaterialUuid())) {
                    retMap.get(item.getMaterialUuid()).add(item);
                } else {
                    List<IMediaInfo> tempList = new ArrayList<>();
                    tempList.add(item);
                    retMap.put(item.getMaterialUuid(), tempList);
                }
            }
        }
        return retMap;
    }

    private List<MaterialMediaDBInfo> getPartAttachFileList(String type, String uuids) {
        GeomEleBussiness eleBussiness = GeomEleBussiness.getInstance();
        List<MaterialMediaDBInfo> afList = eleBussiness.getMaterialMediaInfoByMaterialUuids(mCityCode, type, uuids, isHistory);

        return afList;
    }

    private List<IMediaInfo> getAttachFileList(IGeomElementInfo info) {
        List<IMediaInfo> mediaList = new ArrayList<>();
        if(!isNew) {
            mediaList = GeomEleBussiness.getInstance().getMediaInfos(mCityCode, mType, info.getUuid(), isHistory);
        }
        return  mediaList;
    }

    private void updateEngineInfo() {
        if(mCurPropertyLogic == null) {
            pdListDetail = mGeomEleDBInfo.getPdListDetail();
            PropertyDes pd = new PropertyDes(PropertyDes.TYPE_PHOTO, mAttachMap.get(mGeomEleDBInfo.getUuid()), mGeomEleDBInfo.getUuid());
            pdListDetail.add(pd);
        }
        initProperty();
    }

    private void updateExtinguishingAgentInfo() {
        initProperty();
    }

    private void updateEquipmentInfo() {
        initProperty();
    }
}
