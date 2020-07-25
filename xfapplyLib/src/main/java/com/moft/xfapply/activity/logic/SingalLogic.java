package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.PanoramaActivity;
import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.logic.location.NavService;
import com.moft.xfapply.logic.network.OkHttpClientManager;
import com.moft.xfapply.model.base.IGeomElementInfo;
import com.moft.xfapply.model.base.IMediaInfo;
import com.moft.xfapply.model.common.ComLocation;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.common.PropertyDes;
import com.moft.xfapply.model.entity.WatersourceMediaDBInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.base.ISourceDataDTO;
import com.moft.xfapply.model.external.result.SimpleRestResult;
import com.moft.xfapply.utils.GsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class SingalLogic extends DetailLogic {
    public SingalLogic(View v, Activity a) {
        super(v, a);
    }

    private List<PropertyDes> pdListDetail = null;
    private HashMap<String, List<IMediaInfo>> mAttachMap = new HashMap<>();

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
                                    ISourceDataDTO elementInfo = (ISourceDataDTO) gson.fromJson(jsonStr, GeomEleBussiness.getInstance().getDTOClsMap().get(info.getEleType()));
                                    if (info.getVersion() != null && elementInfo.getVersion() != null) {
                                        if (info.getVersion() < elementInfo.getVersion()) {
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
        createComLocation(info);

        pdListDetail = info.getPdListDetail();
        addPropertyDesMapPos(pdListDetail, info.getUuid());

        GeomEleBussiness eleBussiness = GeomEleBussiness.getInstance();
        List<IMediaInfo> mediaList = eleBussiness.getMediaInfos(mCityCode, mType, info.getUuid(), isHistory);

        PropertyDes pd = new PropertyDes(PropertyDes.TYPE_PHOTO, mediaList, info.getUuid());
        pdListDetail.add(pd);
        mAttachMap.put(info.getUuid(), mediaList);
    }

    @Override
    protected void onInitDataNew(IGeomElementInfo info) {
        createComLocation(info);
        pdListDetail = info.getPdListDetail();
        addPropertyDesMapPos(pdListDetail, info.getUuid());
        List<IMediaInfo> attachFileList = new ArrayList<>();
        PropertyDes pd = new PropertyDes(PropertyDes.TYPE_PHOTO, attachFileList, info.getUuid());
        pdListDetail.add(pd);
        mAttachMap.put(info.getUuid(), attachFileList);
    }

    @Override
    protected void onPreInitView() {

    }

    @Override
    protected void onInitView() {

    }

    @Override
    protected void onInitPopNav() {
        if(!AppDefs.CompEleType.EQUIPMENT.toString().equals(mGeomEleDBInfo.getEleType()) &&
                !AppDefs.CompEleType.EXTINGUISHING_AGENT.toString().equals(mGeomEleDBInfo.getEleType())) {
            RelativeLayout optionView = (RelativeLayout)getView().findViewById(R.id.rl_nav);
            optionView.setVisibility(View.VISIBLE);

            List<Dictionary> contList = new ArrayList<>();
            if (isWaterSource(mGeomEleDBInfo.getEleType())) {
                contList.add(new Dictionary("全景影像", "5"));
            }

            if (!AppDefs.ActionType.NEW.toString().equals(operType)) {
                contList.add(new Dictionary("在地图显示", "0"));
            }
            contList.addAll(NavService.getInstance(getActivity()).getNavList());

            setOptionNav(contList);
        }
    }

    private boolean isWaterSource(String eleType) {
         boolean ret = false;
        if (AppDefs.CompEleType.WATERSOURCE_CRANE.name().equals(eleType)
                || AppDefs.CompEleType.WATERSOURCE_FIRE_HYDRANT.name().equals(eleType)
                || AppDefs.CompEleType.WATERSOURCE_FIRE_POOL.name().equals(eleType)
                || AppDefs.CompEleType.WATERSOURCE_NATURE_INTAKE.name().equals(eleType)) {
            ret = true;
        }
        return ret;
    }

    @Override
    protected void onSelectNavOperation(String code) {
        if ("0".equals(code)) {
            doFinish(true);
        } else if ("5".equals(code)) {
            // 全景影像，目前只有水源有这个
            startPanoramaActivity();
        } else {
            startMapNav(code);
        }
    }

    private void startPanoramaActivity() {
        WatersourceMediaDBInfo info = getPanoMediaInfos();
        if(info != null) {
            Intent intent = new Intent(getActivity(), PanoramaActivity.class);
            intent.putExtra("pano_url", info.getMediaUrl());
            getActivity().startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "无全景漫游！", Toast.LENGTH_SHORT).show();
        }
    }

    private WatersourceMediaDBInfo getPanoMediaInfos() {
        WatersourceMediaDBInfo retInfo = null;
        List<IMediaInfo> mediaInfos = mAttachMap.get(mGeomEleDBInfo.getUuid());
        if(mediaInfos != null && mediaInfos.size() > 0) {
            for(IMediaInfo item : mediaInfos) {
                WatersourceMediaDBInfo info = (WatersourceMediaDBInfo)item;
                if(AppDefs.MediaFormat.URL.name().equals(info.getMediaFormat())) {
                    retInfo = info;
                    break;
                }
            }
        }
        return retInfo;
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

    @Override
    protected void onPropertyInit() {
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
                return false;
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
    }
}
