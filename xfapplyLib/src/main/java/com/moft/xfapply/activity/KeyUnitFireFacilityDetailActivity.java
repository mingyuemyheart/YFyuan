package com.moft.xfapply.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.logic.CameraLogic;
import com.moft.xfapply.activity.logic.PropertyLogic;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.event.CommandEvent;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.model.KeyDiagram.Geometry;
import com.moft.xfapply.model.KeyDiagram.GeometryBD;
import com.moft.xfapply.model.KeyDiagram.GeometryPlot;
import com.moft.xfapply.model.KeyDiagram.PlaneDiagram;
import com.moft.xfapply.model.KeyDiagram.PmtInfo;
import com.moft.xfapply.model.base.IFireFacilityInfo;
import com.moft.xfapply.model.base.IMediaInfo;
import com.moft.xfapply.model.base.IPartitionInfo;
import com.moft.xfapply.model.common.ComLocation;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.common.PropertyDes;
import com.moft.xfapply.model.entity.KeyUnitMediaDBInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.utils.GsonUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.Utils;
import com.moft.xfapply.widget.HighLightImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.moft.xfapply.activity.logic.PropertyLogic.MAP_POS;

public class KeyUnitFireFacilityDetailActivity extends BaseActivity {
    public static final int REQUEST_DETAIL_ACTIVITY_CODE = 5002;
    private TextView tv_title = null;
    private LinearLayout ll_content = null;

    private IFireFacilityInfo info;
    private List<IPartitionInfo> partitionList;
    private List<IMediaInfo> mediaList = new ArrayList<>();
    private List<IMediaInfo> keyDiagramMediaList = new ArrayList<>();
    private boolean isHistory;
    private boolean isEditable;

    private ComLocation mComLocation;
    private List<PropertyDes> pdListDetail = null;
    private PropertyLogic mCurPropertyLogic = null;
    private CameraLogic mCurCameraLogic = null;

    private HighLightImageView iv_pmt;
    private TextView tv_pmt;
    private PlaneDiagram planeDiagram;
    private GeometryPlot geometryPlot;
    private List<IMediaInfo> pmtList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_facility_detail);
        EventBus.builder().logNoSubscriberMessages(false).sendNoSubscriberEvent(false).build();
        EventBus.getDefault().register(this);
        if(initData()) {
            initView();
        } else {
            Toast.makeText(this, "无效的数据", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private boolean initData() {
        boolean ret = false;
        Intent intent = getIntent();
        info = (IFireFacilityInfo)intent.getSerializableExtra("info");

        if(info != null) {
            ret = true;
            partitionList = (List<IPartitionInfo>)intent.getSerializableExtra("partition_info");
            keyDiagramMediaList = (List<IMediaInfo>)intent.getSerializableExtra("key_diagram_media_info");
            mediaList = (List<IMediaInfo>)intent.getSerializableExtra("media_info");
            isHistory = intent.getBooleanExtra("is_history", false);
            isEditable = intent.getBooleanExtra("is_editable", false);
            createComLocation(info);

            pdListDetail = info.getPdListDetail();
            pdListDetail.remove(0);
            addPropertyDesPartition(pdListDetail, partitionList);
            addPropertyDesMapPos(pdListDetail, info.getUuid());
            //addPropertyDesPartPmt(pdListDetail);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    updatePartPmt();
                }
            });

            if(mediaList == null) {
                mediaList = new ArrayList<>();
            }

            PropertyDes pd = new PropertyDes(PropertyDes.TYPE_PHOTO, mediaList, info.getUuid());
            pdListDetail.add(pd);
        }
        return ret;
    }

    private void addPropertyDesPartition(List<PropertyDes> list, List<IPartitionInfo> partitionList) {
        List<Dictionary> dicList = getPartitionDicList(partitionList);

        PropertyDes pd = new PropertyDes("所属建(构)筑*", "setPartitionUuid", String.class, info.getPartitionUuid(), dicList, true, true);
        list.add(1, pd);
    }

    private List<Dictionary> getPartitionDicList(List<IPartitionInfo> partitionList) {
        List<Dictionary> retList = new ArrayList<>();
        for(IPartitionInfo item : partitionList) {
            retList.add(new Dictionary(item.getName(), item.getUuid()));
        }
        return retList;
    }

    protected void addPropertyDesMapPos(List<PropertyDes> list, String uuid) {
        PropertyDes pro = getPropertyDesMapPos(uuid);
        if(pro != null) {
            list.add(pro);
        }
    }

    private PropertyDes getPropertyDesMapPos(String uuid) {
        PropertyDes pd = new PropertyDes(
                "地图定位", mComLocation.getDesc(), PropertyDes.TYPE_MAP_POS);
        pd.setEntityUuid(uuid);

        return pd;
    }

    //添加所在平面图
    private void addPropertyDesPartPmt(List<PropertyDes> pdList) {
        List<IMediaInfo> pmtList = getPartPmt(info.getPartitionUuid());
        if(pmtList.size() >  0) {
            //TODO:需要考虑怎样把，经纬度和所在平面图都设置在GeometryText???
            PmtInfo pmtInfo = new PmtInfo();
            pmtInfo.setGeometryText(info.getGeometryText());
            pmtInfo.setPlaneDiagram(info.getPlanDiagram());
            pdList.add(new PropertyDes("所在平面图", "setPlanDiagram", PmtInfo.class, pmtInfo, pmtList, PropertyDes.TYPE_ZTPMT_LIST));
        }
    }

    private List<IMediaInfo> getPartPmt(String partitionUuid) {
        List<IMediaInfo> pmtList = new ArrayList<>();
        if(keyDiagramMediaList != null && keyDiagramMediaList.size() > 0) {
            //ID:837831 19-01-10 【消防设施所属平面图】选择所属分区及上级所有分区的平面图 王泉
            List<String> parentList = getPartitionParentUuids(partitionUuid);
            for(IMediaInfo item : keyDiagramMediaList) {
                KeyUnitMediaDBInfo info = (KeyUnitMediaDBInfo)item;
                if(parentList.contains(info.getBelongToUuid())) {
                    if (AppDefs.KeyUnitMediaType.KU_MEDIA_OVERALL_PLAN.toString().equals(info.getClassification()) ||
                            AppDefs.KeyUnitMediaType.KU_MEDIA_INTERNAL_PLANE_DIAGRAM.toString().equals(info.getClassification())) {// && item.getVersioned()) {
                        //如果是总分区显示在最上方
                        if(AppDefs.KeyUnitMediaType.KU_MEDIA_OVERALL_PLAN.toString().equals(info.getClassification())) {
                            pmtList.add(0, item);
                        } else{
                            pmtList.add(item);
                        }
                    }
                }
            }
        }
        return pmtList;
    }

    private void updatePartPmt() {
        iv_pmt = (HighLightImageView) findViewById(R.id.iv_pmt);
        tv_pmt = (TextView)findViewById(R.id.tv_pmt);
        pmtList = getPartPmt(info.getPartitionUuid());
        if(pmtList.size() >  0) {
            planeDiagram = getPlaneDiagram(info.getPlanDiagram());
            IMediaInfo mediaInfo = getPartPmtMedia(pmtList, planeDiagram.getAttachUuid());
            if(mediaInfo != null) {
                geometryPlot = getGeometryPlot(mediaInfo, info.getGeometryText());
                SimpleTarget<Bitmap> simpleTarget = new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> animation) {
                        if(!isFinishing() && resource != null) {
                            if (geometryPlot != null && geometryPlot.getXys() != null && geometryPlot.getXys().size() > 0) {
                                iv_pmt.setOriginalSize(geometryPlot.getBaseWidth(), geometryPlot.getBaseHeight());
                                iv_pmt.setHighLightPoints(geometryPlot.getXys());
                            }
                            iv_pmt.setSourceImageBitmap(resource, iv_pmt.getWidth(), iv_pmt.getHeight());
                        } else {
                            tv_pmt.setVisibility(View.GONE);
                            iv_pmt.setVisibility(View.GONE);
                        }
                    }
                };
                String remoteFullPath = mediaInfo.getMediaUrl();
                if (!remoteFullPath.toLowerCase().startsWith("http")) {
                    remoteFullPath = Constant.URL_HEAD + PrefSetting.getInstance().getServerIP()
                            + Constant.URL_MIDDLE + PrefSetting.getInstance().getServerPort()
                            + Constant.SERVICE_NAME
                            + Constant.ATTACH_NAME + mediaInfo.getMediaUrl();
                }
                Glide.with(this)
                        .load(remoteFullPath)
                        .asBitmap()
                        .into(simpleTarget);
            } else {
                tv_pmt.setVisibility(View.GONE);
                iv_pmt.setVisibility(View.GONE);
            }
        } else {
            tv_pmt.setVisibility(View.GONE);
            iv_pmt.setVisibility(View.GONE);
        }
    }

    private IMediaInfo getPartPmtMedia(List<IMediaInfo> pmtList, String mediaUuid) {
        IMediaInfo info = null;
        for(IMediaInfo item : pmtList) {
            if(item.getUuid().equals(mediaUuid)) {
                info = item;
                break;
            }
        }
        return info;
    }

    private PlaneDiagram getPlaneDiagram(String value) {
        PlaneDiagram planeDiagram = null;
        //解析FloorPlanPosition的json数据
        try {
            Gson gson = GsonUtil.create();
            planeDiagram = gson.fromJson(value, PlaneDiagram.class);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return planeDiagram;
    }

    private GeometryPlot getGeometryPlot(IMediaInfo mediaInfo, String geometryText) {
        GeometryPlot info = null;
        List<Geometry> geometryList = jsonToGeometrys(geometryText);
        if (geometryList != null && geometryList.size() > 0) {
            for (Geometry item : geometryList) {
                if(item instanceof GeometryPlot) {
                    GeometryPlot geometryPlot = (GeometryPlot)item;
                    if (mediaInfo.getUuid().equals(geometryPlot.getBaseUuid())) {
                        info = geometryPlot;
                        break;
                    }
                }
            }
        }
        return info;
    }

    private List<Geometry> jsonToGeometrys(String geometryText) {
        List<Geometry> geometryList = new ArrayList<>();
        Gson gson = GsonUtil.create();
        JSONArray jsonArray = new JSONArray();
        if (!StringUtil.isEmpty(geometryText)) {
            try {
                jsonArray = new JSONArray(geometryText);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for (int index = 0; index < jsonArray.length(); ++index) {
            try {
                String geometry = jsonArray.getString(index);
                Geometry info = gson.fromJson(geometry, Geometry.class);
                if (AppDefs.CoordinateType.ONPLOT.toString().equals(info.getGeometryType())) {
                    GeometryPlot plot = gson.fromJson(geometry, GeometryPlot.class);
                    if (plot != null) {
                        geometryList.add(plot);
                    }
                } else if (AppDefs.CoordinateType.BD_09.toString().equals(info.getGeometryType())) {
                    GeometryBD bd = gson.fromJson(geometry, GeometryBD.class);
                    if (bd != null) {
                        geometryList.add(bd);
                    }
                } else {
                    geometryList.add(info);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return geometryList;
    }

    //ID:837831 19-01-10 【消防设施所属平面图】选择所属分区及上级所有分区的平面图 王泉
    private List<String> getPartitionParentUuids(String partitionUuid) {
        List<String> list = new ArrayList<>();
        for (IPartitionInfo item : partitionList) {
            if(item.getUuid().equals(partitionUuid)) {
                list.addAll(getPartitionParentUuids(item.getBelongToPartitionUuid()));
                list.add(item.getUuid());
            }
        }
        return list;
    }

    protected void createComLocation(IFireFacilityInfo info) {
        mComLocation = new ComLocation();
        mComLocation.setLat(Utils.convertToDouble(info.getLatitude()));
        mComLocation.setLng(Utils.convertToDouble(info.getLongitude()));
        mComLocation.setName(info.getName());
        mComLocation.setEleUuid(info.getUuid());
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);

        tv_title.setText(info.getName());

        mCurPropertyLogic = new PropertyLogic(ll_content, this);
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
                return mComLocation;
            }

            @Override
            public String onGetCityCode() {
                return info.getBelongtoGroup();
            }

            @Override
            public boolean onGetIsHistory() {
                return isHistory;
            }

        });
        mCurPropertyLogic.init();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == MAP_POS) {
            if (!(data.hasExtra("ComLocation"))) {
                return;
            }
            doGetMapPos((ComLocation)data.getSerializableExtra("ComLocation"));
        } else if (requestCode == CameraLogic.PHOTO_REQUEST_TAKEPHOTO) {
            if (mCurCameraLogic != null) {
                mCurCameraLogic.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    protected void doGetMapPos(ComLocation cl) {
        if (mCurPropertyLogic == null || mCurPropertyLogic.getPdListDetail() == null) {
            return;
        }

        PropertyDes curPd = null;
        for (PropertyDes pd : mCurPropertyLogic.getPdListDetail()) {
            if (pd.getType() == PropertyDes.TYPE_MAP_POS &&
                    cl.getEleUuid().equals(pd.getEntityUuid())) {
                curPd = pd;
                break;
            }
        }
        if (curPd == null) {
            return;
        }

        mComLocation.setLat(cl.getLat());
        mComLocation.setLng(cl.getLng());
        mComLocation.setAddress(cl.getAddress());

        curPd.setValue(mComLocation.getDesc());
        mCurPropertyLogic.updateTextValue(curPd);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iv_pmt.destroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CommandEvent event) {
        if(CommandEvent.CMD_EXIT.equals(event.command)) {
            finish();
        }
    }

    public void onPmtClick(View view) {
        IMediaInfo mediaInfo = getPartPmtMedia(pmtList, planeDiagram.getAttachUuid());
        if(mediaInfo != null) {
            ArrayList<IMediaInfo> list = new ArrayList<>();
            list.add(mediaInfo);
            Intent intent = new Intent();
            intent.setClass(this, PickBigImagesActivity.class);
            //ID:886059 19-02-18 【数据】查看详细数据，有大量附件图片，会导致异常崩溃OOM。 王泉
            intent.putExtra(PickBigImagesActivity.EXTRA_HISTORY, isHistory);
            intent.putExtra(PickBigImagesActivity.EXTRA_DATA, list);
            intent.putExtra(PickBigImagesActivity.EXTRA_CURRENT_PIC, 0);
            intent.putExtra(PickBigImagesActivity.EXTRA_GEOMETRY_PLOT, geometryPlot);
            startActivity(intent);
        }
    }
}
