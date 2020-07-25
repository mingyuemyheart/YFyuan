package com.moft.xfapply.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moft.xfapply.R;
import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.activity.logic.CameraLogic;
import com.moft.xfapply.activity.logic.PropertyLogic;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.event.CommandEvent;
import com.moft.xfapply.model.base.IFireFacilityInfo;
import com.moft.xfapply.model.base.IGeomElementInfo;
import com.moft.xfapply.model.base.IMediaInfo;
import com.moft.xfapply.model.common.ComLocation;
import com.moft.xfapply.model.common.PropertyDes;
import com.moft.xfapply.model.entity.MaterialEquipmentDBInfo;
import com.moft.xfapply.model.entity.MaterialExtinguishingAgentDBInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.moft.xfapply.activity.logic.PropertyLogic.MAP_POS;

public class FireEnginePartDetailActivity extends BaseActivity {
    public static final int REQUEST_DETAIL_ACTIVITY_CODE = 5000;
    private TextView tv_title = null;
    private LinearLayout ll_content = null;

    private IGeomElementInfo info;
    private boolean isHistory;
    private boolean isEditable;

    private ComLocation mComLocation;
    private List<IMediaInfo> mediaList = new ArrayList<>();
    private List<PropertyDes> pdListDetail = null;
    private PropertyLogic mCurPropertyLogic = null;
    private CameraLogic mCurCameraLogic = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geom_element_part_detail);
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
        info = (IGeomElementInfo) intent.getSerializableExtra("info");

        if(info != null) {
            ret = true;
            isHistory = intent.getBooleanExtra("is_history", false);
            isEditable = intent.getBooleanExtra("is_editable", false);

            if(AppDefs.CompEleType.EXTINGUISHING_AGENT.toString().equals(info.getEleType())) {
                MaterialExtinguishingAgentDBInfo extinguishingAgentInfo = (MaterialExtinguishingAgentDBInfo)info;
                if(AppDefs.CompEleType.FIRE_VEHICLE.toString().equals(extinguishingAgentInfo.getStoragePositionType())) {
                    pdListDetail = extinguishingAgentInfo.getFireVehiclePdListDetail();
                } else {
                    pdListDetail = info.getPdListDetail();
                }
            } else if(AppDefs.CompEleType.EQUIPMENT.toString().equals(info.getEleType())) {
                MaterialEquipmentDBInfo EquipmentInfo = (MaterialEquipmentDBInfo)info;
                if(AppDefs.CompEleType.FIRE_VEHICLE.toString().equals(EquipmentInfo.getStoragePositionType())) {
                    pdListDetail = EquipmentInfo.getFireVehiclePdListDetail();
                } else {
                    pdListDetail = info.getPdListDetail();
                }
            } else {
                pdListDetail = info.getPdListDetail();
            }

            pdListDetail.remove(0);

            mediaList = (List<IMediaInfo>)intent.getSerializableExtra("media_info");

            if(mediaList == null) {
                mediaList = new ArrayList<>();
            }

            PropertyDes pd = new PropertyDes(PropertyDes.TYPE_PHOTO, mediaList, info.getUuid());
            pdListDetail.add(pd);
        }
        return ret;
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
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CommandEvent event) {
        if(CommandEvent.CMD_EXIT.equals(event.command)) {
            finish();
        }
    }
}
