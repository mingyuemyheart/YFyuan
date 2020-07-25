package com.moft.xfapply.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.logic.DisasterMapLogic;
import com.moft.xfapply.activity.logic.MapLayerLogic;
import com.moft.xfapply.activity.logic.MapLogic;
import com.moft.xfapply.activity.logic.MapOptionDisaterLogic;
import com.moft.xfapply.activity.logic.MeasureLogic;
import com.moft.xfapply.activity.logic.NearLogic;
import com.moft.xfapply.activity.logic.OutlineLogic;
import com.moft.xfapply.activity.logic.QicaiOptionLogic;
import com.moft.xfapply.activity.logic.RoutePlanLogic;
import com.moft.xfapply.activity.logic.SearchBarLogic;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.BaseActivity;
import com.moft.xfapply.logic.location.NavService;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.common.DisasterInfo;
import com.moft.xfapply.model.common.LayerSelect;
import com.moft.xfapply.model.common.QueryCondition;
import com.moft.xfapply.model.common.RoutePlanInfo;
import com.moft.xfapply.model.entity.GeomElementDBInfo;
import com.moft.xfapply.model.entity.MapSearchInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.utils.CoordinateUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.widget.dialog.CustomAlertDialog;
import com.moft.xfapply.widget.dialog.ListDialog;

import java.util.ArrayList;
import java.util.List;

public class DisasterActivity extends BaseActivity implements
        MapLogic.OnMapLogicListener,
        MapOptionDisaterLogic.OnMapOptionDisaterLogicListener,
        SearchBarLogic.OnSearchBarLogicListener,
        OutlineLogic.OnOutlinLogicListener,
        MeasureLogic.OnMeasureLogicListener,
        RoutePlanLogic.OnRoutePlanLogicListener,
        NearLogic.OnNearLogicListener,
        QicaiOptionLogic.OnQicaiOptionLogicListener {

    private List<RoutePlanInfo> mRoutePlanInfoList = null;

    private DisasterMapLogic mMapLogic = null;
    private MapOptionDisaterLogic mMapOptionLogic = null;
    private SearchBarLogic mSearchBarLogic = null;
    private MeasureLogic mMeasureLogic = null;
    private OutlineLogic mOutlineLogic = null;
    private RoutePlanLogic mRoutePlanLogic = null;
    private NearLogic mNearLogic = null;
    private QicaiOptionLogic mQicaiOptionLogic = null;

    private RelativeLayout re_title = null;
    private TextView tv_select = null;
    private LinearLayout re_option = null;
    private PopupWindow popCaldisInfo = null;
    private View re_plan = null;
    private ImageView iv_plan = null;
    private View re_route = null;
    private View re_whp = null;
    private View re_czcx = null;

    private DisasterInfo disasterInfo = new DisasterInfo();
    private boolean isChangingDisaster = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disaster);

        mMapLogic = new DisasterMapLogic(getWindow().getDecorView(), this);
        mMapLogic.setListener(this);
        mMapLogic.init();

        mMapOptionLogic = new MapOptionDisaterLogic(getWindow().getDecorView(), this);
        mMapOptionLogic.setBaiduMap(mMapLogic.getBaiduMap());
        mMapOptionLogic.setListener(this);
        mMapOptionLogic.init();

        mSearchBarLogic = new SearchBarLogic(getWindow().getDecorView(), this);
        mSearchBarLogic.setCondition(LvApplication.getInstance().getMapDisasterQueryCondition());
        mSearchBarLogic.setListener(this);
        mSearchBarLogic.init();

        mOutlineLogic = new OutlineLogic(getWindow().getDecorView(), this);
        mOutlineLogic.setShowExtra(true);
        mOutlineLogic.setListener(this);
        mOutlineLogic.init();

        mRoutePlanLogic = new RoutePlanLogic(getWindow().getDecorView(), this);
        mRoutePlanLogic.setListener(this);
        mRoutePlanLogic.init();

        mNearLogic = new NearLogic(getWindow().getDecorView(), this);
        mNearLogic.setListener(this);
        mNearLogic.init();

        mQicaiOptionLogic = new QicaiOptionLogic(getWindow().getDecorView(), this);
        mQicaiOptionLogic.setListener(this);
        mQicaiOptionLogic.init();

        tv_select = (TextView) findViewById(R.id.tv_select);
        re_title = (RelativeLayout) findViewById(R.id.re_title);
        re_option = (LinearLayout) findViewById(R.id.re_option);
        re_option.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mOutlineLogic.isShowing()) {
                    mOutlineLogic.hidePopInfo();
                }
                mMapOptionLogic.show();
            }
        });
        re_plan = findViewById(R.id.re_plan);
        re_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!disasterInfo.isImportant()) {
                    Toast.makeText(DisasterActivity.this, "没有该灾害关联的预案信息！",
                            Toast.LENGTH_SHORT).show();
                }
                mOutlineLogic.showPopInfo(disasterInfo.getMsi());
                updatePopInfoTitle(disasterInfo.getMsi());
            }
        });
        iv_plan = (ImageView) findViewById(R.id.iv_plan);
        refreshDisasterInfo(null);

        re_route = findViewById(R.id.re_route);
        re_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRoutePlan();
            }
        });

        re_whp = findViewById(R.id.re_whp);
        re_whp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisasterActivity.this, HcSearchActivity.class);
                startActivity(intent);
            }
        });

        re_czcx = findViewById(R.id.re_czcx);
        re_czcx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisasterActivity.this, DealProgramActivity.class);
                startActivity(intent);
            }
        });
    }

    private void doRoutePlan() {
        if (disasterInfo.getMsi() == null) {
            Toast.makeText(DisasterActivity.this, "灾害对象未设定！",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        mRoutePlanLogic.show();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK || requestCode != Constant.SEARCH_INFO_MAP ) {
            return;
        }

        if (data.hasExtra("MapSearchInfo")) {
            onActivityResultMapSearchInfo(data);
            return;
        }
    }

    private void onActivityResultMapSearchInfo(Intent data) {
        MapSearchInfo msi = (MapSearchInfo)data.getSerializableExtra("MapSearchInfo");
        if (msi == null) {
            Toast.makeText(this, "无效数据！",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (msi.isGeoPosValid()) {
            mMapLogic.drawMarkerAndCenterTo(msi);
        }

        if(!isFinishing()) {
            mOutlineLogic.showPopInfo(msi);
            updatePopInfoTitle(msi);
        }
    }

    @Override
    public void onExtra(MapSearchInfo msi) {
        if (isNewDisaster(msi)) {
            refreshDisasterInfo(msi);
        }
    }

    @Override
    public void onUpdateAddress(MapSearchInfo msi) {
        if (disasterInfo != null && !StringUtil.isEmpty(disasterInfo.getAddress())) {
            disasterInfo.setAddress(msi.getAddress());
        }
    }

    private void updatePopInfoTitle(MapSearchInfo msi) {
        if (isNewDisaster(msi)) {
            mOutlineLogic.updateExtra("设定为灾害对象", Color.rgb(0, 116, 199));
        } else {
            mOutlineLogic.updateExtra("当前灾害对象", Color.rgb(225, 101, 49));
        }
    }

    private boolean isNewDisaster(double lat, double lng) {
        MapSearchInfo msiDisaster = disasterInfo.getMsi();
        if (msiDisaster == null) {
            return true;
        }
        double dis = CoordinateUtil.getInstance().getDisTwo(
                lng, lat, msiDisaster.getLng()/1e6, msiDisaster.getLat()/1e6);
        if (dis > 200) {
            return true;
        }
        return false;
    }

    private boolean isNewDisaster(MapSearchInfo msi) {
        MapSearchInfo msiDisaster = disasterInfo.getMsi();
        if (msiDisaster == null) {
            return true;
        }
        if (msi.getLat() == msiDisaster.getLat() && msi.getLng() == msiDisaster.getLng()) {
            return false;
        }
        return true;
    }

    private void changeDisaster(final MapSearchInfo msi) {
        String content = "是否切换至新选择的灾害对象！";
        CustomAlertDialog.show(this, content, new CustomAlertDialog.OnDoingListener() {
            @Override
            public void onOK() {
                disasterInfo.setMsi(msi);
                //重新设置灾害对象后，清除上一次的灾害路线
                mMapLogic.clearRouteLine();
                refreshDisasterInfo(msi);
                isChangingDisaster = false;
            }

            @Override
            public void onNG() {
                if (isChangingDisaster) {
                    adjustDisasterAddress(msi.getLat() / 1e6, msi.getLng() / 1e6);
                }
                isChangingDisaster = false;
            }
        });
    }

    private void changeDisasterAddress(final double lat, final double lng) {
        String content = "是否将灾害地址移动到当前位置！";
        CustomAlertDialog.show(this, content, new CustomAlertDialog.OnDoingListener() {
            @Override
            public void onOK() {
                adjustDisasterAddress(lat, lng);
            }

            @Override
            public void onNG() {
                mMapLogic.drawDisasterMarker(disasterInfo.getMsi());
            }
        });
    }

    private void adjustDisasterAddress(double lat, double lng) {
        MapSearchInfo msiDisaster = disasterInfo.getMsi();
        if (msiDisaster == null) {
            return;
        }
        msiDisaster.setLat((int)(lat*1e6));
        msiDisaster.setLng((int)(lng*1e6));
        disasterInfo.setLat(lat);
        disasterInfo.setLng(lng);
        mRoutePlanLogic.setDisasterInfo(disasterInfo);
        Toast.makeText(this, "灾害地址重新设定完成！",
                Toast.LENGTH_SHORT).show();
    }

    private void refreshDisasterInfo(MapSearchInfo msi) {
        if (msi != null) {
            if (isNewDisaster(msi) && disasterInfo.getMsi() != null) {
                changeDisaster(msi);
                return;
            }
            disasterInfo.setMsi(msi);
            disasterInfo.setImportant(false);
            disasterInfo.setName(msi.getName());
            disasterInfo.setAddress(msi.getAddress());
            disasterInfo.setLat(msi.getLat() / 1e6);
            disasterInfo.setLng(msi.getLng() / 1e6);
            if (Constant.MAP_SEARCH_FIRE.equals(msi.getType())) {
                if (!StringUtil.isEmpty(msi.getExtra())) {
                    String[] exs = msi.getExtra().split(Constant.OUTLINE_DIVIDER);
                    if (exs.length >= 2) {
                        if (AppDefs.CompEleType.KEY_UNIT.toString().equals(exs[0])) {
                            disasterInfo.setImportant(true);
                            disasterInfo.setType(exs[0]);
                            disasterInfo.setKey(exs[1]);
                            disasterInfo.setCity(exs[2]);
                        }
                    }
                }
            }

            if (msi.isGeoPosValid()) {
                mRoutePlanLogic.setDisasterInfo(disasterInfo);
                Toast.makeText(this, "灾害对象已设定！",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "灾害对象地址无效！",
                        Toast.LENGTH_SHORT).show();
            }
        }

        if (disasterInfo.getMsi() != null) {
            mMapLogic.drawDisasterMarker(disasterInfo.getMsi());
            if (!mOutlineLogic.isShowing()) {
                mOutlineLogic.showPopInfo(disasterInfo.getMsi());
            }
            updatePopInfoTitle(disasterInfo.getMsi());
        }

        if (disasterInfo.isImportant()) {
            tv_select.setText("重点单位灾害");
            iv_plan.setImageResource(R.drawable.tool_plan);
        } else {
            tv_select.setText("一般灾害");
            iv_plan.setImageResource(R.drawable.tool_plan_unsel);
        }

        String disAddress = disasterInfo.getName();
        if (StringUtil.isEmpty(disasterInfo.getName())) {
            disAddress = disasterInfo.getAddress();
        }
        if (!StringUtil.isEmpty(disAddress)) {
            mSearchBarLogic.setSearchKey("灾害对象: " + disAddress);
        }
    }

    @Override
    public void onPositionDragStart(double lat, double lng) {
        Toast.makeText(this, "正在调整灾害地址！",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPositionDragEnd(double lat, double lng) {
        if (isNewDisaster(lat, lng)) {
            isChangingDisaster = true;
            mMapLogic.requestAddress(lat, lng);
        } else {
            changeDisasterAddress(lat, lng);
        }
    }

    @Override
    public void onPositionSelected(MapSearchInfo msi) {
        if (isChangingDisaster) {
            changeDisaster(msi);
        } else {
            if (!isFinishing()) {
                mOutlineLogic.showPopInfo(msi);
                updatePopInfoTitle(msi);
            }
        }
    }

    @Override
    public void onElementSelected(MapSearchInfo msi) {
        if(!isFinishing()) {
            mOutlineLogic.showPopInfo(msi);
            updatePopInfoTitle(msi);
        }
    }

    @Override
    public void onUnSelected() {
        if (mOutlineLogic.isShowing()) {
            mOutlineLogic.hidePopInfo();
        }
    }

    private void doSearchNear(MapSearchInfo msi, String eleTyep) {
        if (disasterInfo.getMsi() == null) {
            Toast.makeText(DisasterActivity.this, "灾害对象未设定！",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        QueryCondition qc = LvApplication.getInstance().getMapDisasterQueryCondition();
        qc.setType(QueryCondition.TYPE_POSITION);
        qc.setLat(msi.getLat()/1e6);
        qc.setLng(msi.getLng()/1e6);
        qc.setName(msi.getName());
        qc.setAddress(msi.getAddress());
        qc.setSylxCode(eleTyep);

        mMapLogic.searchNear();
    }

    @Override
    public void onSearchNear(MapSearchInfo msi, String type) {
        doSearchNear(msi, "");
    }

    @Override
    public void onNearSetting(String dis) {
        PrefSetting.getInstance().setLoadDis(dis);
        doSearchNear(disasterInfo.getMsi(), "00");
    }

    @Override
    public void onNearShow() {
        doSearchNear(disasterInfo.getMsi(), "00");
    }

    @Override
    public void onRoutePlanOk(List<RoutePlanInfo> dataList) {
        mRoutePlanInfoList = dataList;
    }

    @Override
    public void onChangeToMap(int pos) {
        if (mRoutePlanInfoList == null || mRoutePlanInfoList.size() == 0) {
            return;
        }
        mMapLogic.clearRouteLine();
        if (pos < 0) {
            pos *= -1;
            int focusIndex = 0;
            mMapLogic.showMapPoi(false);
            for (int i = 0; i < pos; i++) {
                RoutePlanInfo rpi = mRoutePlanInfoList.get(i);
                if (rpi.getCurRouteLine() != null) {
                    String name = "";
                    if (rpi.getGeoElement() != null && !StringUtil.isEmpty(rpi.getGeoElement().getName())) {
                        name = rpi.getGeoElement().getName();
                    }
                    if (focusIndex == 0) {
                        mMapLogic.drawRouteLine(name, rpi.getCurRouteLine(), true);
                    } else {
                        mMapLogic.drawRouteLine(name, rpi.getCurRouteLine(), false);
                    }
                    focusIndex++;
                }
            }
        } else {
            RoutePlanInfo rpi = mRoutePlanInfoList.get(pos);
            if (rpi.getCurRouteLine() != null) {
                String name = "";
                if (rpi.getGeoElement() != null && !StringUtil.isEmpty(rpi.getGeoElement().getName())) {
                    name = rpi.getGeoElement().getName();
                }
                mMapLogic.showMapPoi(false);
                mMapLogic.drawRouteLine(name, rpi.getCurRouteLine(), true);
            }
        }
    }

    @Override
    public void onDismiss() {
    }

    @Override
    public void onClickSearch() {
        Intent intent = new Intent(this, MapSearchActivity.class);
        intent.putExtra("FromMap", false);
        intent.putExtra("AutoSearch", false);
        startActivityForResult(intent, Constant.SEARCH_INFO_MAP);
    }

    @Override
    public void onClickList() {

    }

    @Override
    public void onViewPano(String uid, double lat, double lng, String name) {
        Intent intent = new Intent(this, PanoActivity.class);
        intent.putExtra("type", PanoActivity.UID_STREET);
        intent.putExtra("title", name);
        intent.putExtra("uid", uid);
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);

        startActivity(intent);
    }

    @Override
    public void onViewPano(double lat, double lng, String name) {
        Intent intent = new Intent(this, PanoActivity.class);
        intent.putExtra("type", PanoActivity.GEO);
        intent.putExtra("title", name);
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);

        startActivity(intent);
    }

    @Override
    public void onViewPanoIndoor(String uid, double lat, double lng, String name) {
        Intent intent = new Intent(this, PanoActivity.class);
        intent.putExtra("type", PanoActivity.UID_INTERIOR);
        intent.putExtra("title", name);
        intent.putExtra("uid", uid);
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);

        startActivity(intent);
    }

    @Override
    public void onStartNav(double lat, double lng, String endName) {
        NavService.getInstance(this).doNav(lat, lng, endName);
    }

    @Override
    public void onPopDismiss() {
    }

    @Override
    public void onViewDetail(String type, String key, String city) {
        Intent intent = new Intent(this, WsDetailActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("key", key);
        intent.putExtra("city", city);
        startActivity(intent);
    }

    @Override
    public void onViewQRCode(String type, String key, String city) {
        Intent intent = new Intent(this, QRCodeActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("key", key);
        intent.putExtra("city", city);
        startActivity(intent);
    }

    @Override
    public void onMoveMap(double lat, double lng) {
        mMapLogic.moveTo(new LatLng(lat, lng));
    }

    @Override
    public void onDial(String dialInfo) {
        final List<Dictionary> contentList = new ArrayList<>();
        String[] dials = dialInfo.split("&");
        if(dials.length < 1) {
            return;
        }
        for (int i = 0; i < dials.length; i++) {
            contentList.add(new Dictionary(dials[i],""));
        }

        ListDialog.show(this, contentList, -1, new ListDialog.OnSelectedListener() {
            @Override
            public void onSelected(int position) {
                if (-1 == position) {
                    return;
                }
                String dialSingle = contentList.get(position).getValue();
                if (!StringUtil.isEmpty(dialSingle)) {
                    String[] ds = dialSingle.split("：");
                    if (ds.length > 1) {
                        Intent intent = new Intent(Intent.ACTION_DIAL,
                                Uri.parse("tel:" + ds[1]));
                        DisasterActivity.this.startActivity(intent);
                    }
                }
            }
        });
    }

    private void startMeasure(int dis_type) {
        if (popCaldisInfo != null && popCaldisInfo.isShowing()) {
            return;
        }

        re_title.setVisibility(View.INVISIBLE);
        re_option.setVisibility(View.INVISIBLE);

        int[] location = new int[2];
        re_title.getLocationOnScreen(location);

        View popView = getLayoutInflater().inflate(
                R.layout.popup_measure_result, null);

        popCaldisInfo = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, false);
        popCaldisInfo.setOutsideTouchable(false);
        popCaldisInfo.showAtLocation(getWindow().getDecorView(), Gravity.TOP, 0, location[1]);

        mMeasureLogic = new MeasureLogic(popView, this);
        mMeasureLogic.setListener(this);
        mMeasureLogic.setDisType(dis_type);
        mMeasureLogic.setParentView(getWindow().getDecorView());
        mMeasureLogic.init();

        mMapLogic.setMeasureLogic(mMeasureLogic);
    }

    private void endMeasure() {
        re_title.setVisibility(View.VISIBLE);
        re_option.setVisibility(View.VISIBLE);

        if (popCaldisInfo != null && popCaldisInfo.isShowing()) {
            popCaldisInfo.dismiss();
            popCaldisInfo = null;
        }

        mMeasureLogic = null;
        mMapLogic.setMeasureLogic(mMeasureLogic);
    }

    @Override
    public void onMeasureDis() {
        startMeasure(MeasureLogic.DIS_TYPE_DIS);
    }

    @Override
    public void onMeasureArea() {
        startMeasure(MeasureLogic.DIS_TYPE_AREA);
    }

    @Override
    public void onMeasureCancel() {
        endMeasure();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back(null);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void back(View view) {
        String content = "是否退出灾情模式！";
        CustomAlertDialog.show(this, content, new CustomAlertDialog.OnDoingListener() {
            @Override
            public void onOK() {
                finish();
            }

            @Override
            public void onNG() {

            }
        });
    }

    @Override
    public void onQicaiChange(String type) {
        mQicaiOptionLogic.hidePopOption();

        ArrayList<String> geoUuidList = new ArrayList<>();
        if (mRoutePlanLogic != null) {
            List<RoutePlanInfo> rpiList = mRoutePlanLogic.getRoutePlanInfoList();
            if (rpiList != null) {
                for (RoutePlanInfo rpi: rpiList){
                    if (rpi.getGeoElement() != null) {
                        geoUuidList.add(rpi.getGeoElement().getUuid());
                    }
                }
            }
        }

        QueryCondition qc = new QueryCondition();
        qc.setType(QueryCondition.TYPE_SYLX);
        qc.setSylxCode(type);

        Intent intent = new Intent(this, WsRecordActivity.class);
        intent.putExtra("QueryCondition", qc);
        intent.putStringArrayListExtra("GeoElementUuidList", geoUuidList);
        startActivity(intent);
    }
}
