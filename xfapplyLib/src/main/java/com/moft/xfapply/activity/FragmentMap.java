package com.moft.xfapply.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.model.LatLng;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.activity.logic.MapLayerLogic;
import com.moft.xfapply.activity.logic.MapLogic;
import com.moft.xfapply.activity.logic.MapLogic.OnMapLogicListener;
import com.moft.xfapply.activity.logic.MeasureLogic;
import com.moft.xfapply.activity.logic.NearLogic;
import com.moft.xfapply.activity.logic.OutlineLogic;
import com.moft.xfapply.activity.logic.SearchBarLogic;
import com.moft.xfapply.activity.logic.SearchBarLogic.OnSearchBarLogicListener;
import com.moft.xfapply.activity.logic.ThemeLogic;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.logic.location.NavService;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.base.IGeomElementInfo;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.common.LayerSelect;
import com.moft.xfapply.model.common.QueryCondition;
import com.moft.xfapply.model.common.SearchSummaryInfo;
import com.moft.xfapply.model.entity.GeomElementDBInfo;
import com.moft.xfapply.model.entity.MapSearchInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.Utils;
import com.moft.xfapply.widget.dialog.CustomAlertDialog;
import com.moft.xfapply.widget.dialog.ListDialog;

/*
 * @Author: 宋满意
 * @Date:   2018-06-01 17:30:53
 * @Last Modified by:   宋满意
 * @Last Modified time: 2019-02-19 16:08:16
 * No.              Date.          Modifier    Description
 * 【HW-886077】      2019-02-19     宋满意       地图自动显示周边数据
 */

public class FragmentMap extends Fragment implements
    OnMapLogicListener,
    OnSearchBarLogicListener,
        OutlineLogic.OnOutlinLogicListener,
        MeasureLogic.OnMeasureLogicListener,
        MapLayerLogic.OnMapLayerLogicListener,
        ThemeLogic.OnThemeLogicListener,
        NearLogic.OnNearLogicListener {

    private MapLogic mMapLogic = null;
    private SearchBarLogic mSearchBarLogic = null;
    private OutlineLogic mOutlineLogic = null;
    private MeasureLogic mMeasureLogic = null;
    private MapLayerLogic mMapLayerLogic = null;
    private ThemeLogic mThemeLogic = null;
    private NearLogic mNearLogic = null;

    private RelativeLayout re_title;
    private LinearLayout re_option;
    private PopupWindow popCaldisInfo = null;
    
    @Override
    public View onCreateView(LayoutInflater inflater, 
            ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMapLogic = new MapLogic(getView(), getActivity());
        mMapLogic.setListener(this);
        mMapLogic.init();

        mSearchBarLogic = new SearchBarLogic(getView(), getActivity());
        mSearchBarLogic.setCondition(LvApplication.getInstance().getMapQueryCondition());
        mSearchBarLogic.setListener(this);
        mSearchBarLogic.init();

        mOutlineLogic = new OutlineLogic(getView(), getActivity());
        mOutlineLogic.setListener(this);
        mOutlineLogic.init();

        mMapLayerLogic = new MapLayerLogic(getView(), getActivity());
        mMapLayerLogic.setBaiduMap(mMapLogic.getBaiduMap());
        mMapLayerLogic.setListener(this);
        mMapLayerLogic.init();

        mThemeLogic = new ThemeLogic(getView(), getActivity());
        mThemeLogic.setListener(this);
        mThemeLogic.init();

        mNearLogic = new NearLogic(getView(), getActivity());
        mNearLogic.setListener(this);
        mNearLogic.init();

        re_title = (RelativeLayout) getView().findViewById(R.id.re_title);
        re_option = (LinearLayout)getView().findViewById(R.id.re_option);
        re_option.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mOutlineLogic.isShowing()) {
                    mOutlineLogic.hidePopInfo();
                }

                mMapLayerLogic.show();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("hidden", hidden);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            hidden = savedInstanceState.getBoolean("hidden");
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
                
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode != Constant.LIST_INFO && requestCode != Constant.SEARCH_INFO_MAP ) {
            return;
        }
        
        if (data.hasExtra("neededMapChange")) {
            if (data.hasExtra("QueryCondition")) {
                onActivityResultQueryCondition(data);
            } else {
                onActivityResultShowGeomElement(data);
            }
            return;
        }

        if (data.hasExtra("MapSearchInfo")) {
            onActivityResultMapSearchInfo(data);
            return;
        }
    }

    private void onActivityResultQueryCondition(Intent data) {
        QueryCondition qc = (QueryCondition)data.getSerializableExtra("QueryCondition");
        if (qc == null) {
            Toast.makeText(getActivity(), "无效数据！",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        LvApplication.getInstance().getMapQueryCondition().copy(qc);
        mSearchBarLogic.setSearchKey();

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("正在加载数据，请耐心等待...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mMapLogic.clearMap();
                mMapLogic.loadElement();
                dialog.dismiss();
            }
        }, 200);
    }

    private void onActivityResultShowGeomElement(Intent data) {
        IGeomElementInfo dto = (IGeomElementInfo)data.getSerializableExtra("CompElement");
        String city = data.getStringExtra("city");
        if (dto == null || StringUtil.isEmpty(city)) {
            Toast.makeText(getActivity(), "无效数据！",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!dto.isGeoPosValid()) {
            Toast.makeText(getActivity(), "无效地址！",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        final MapSearchInfo msi = new MapSearchInfo();
        msi.setType(Constant.MAP_SEARCH_FIRE);
        msi.setName(dto.getName());
        msi.setAddress(dto.getAddress());
        msi.setUid("");
        msi.setLat((int)(Utils.convertToDouble(dto.getLatitude()) * 1e6));
        msi.setLng((int)(Utils.convertToDouble(dto.getLongitude()) * 1e6));
        String extra = dto.getEleType() + Constant.OUTLINE_DIVIDER +
                dto.getUuid() + Constant.OUTLINE_DIVIDER + city;
        //对应其他消防队伍分为4类
        extra += Constant.OUTLINE_DIVIDER + dto.getResEleType();
        extra += Constant.OUTLINE_DIVIDER + dto.getOutline();
        msi.setExtra(extra);

        if (msi.isGeoPosValid()) {
            mMapLogic.drawMarkerAndCenterTo(msi);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mOutlineLogic.showPopInfo(msi);
            }
        }, 500);
    }

    private void onActivityResultMapSearchInfo(Intent data) {
        MapSearchInfo msi = (MapSearchInfo)data.getSerializableExtra("MapSearchInfo");
        if (msi == null) {
            Toast.makeText(getActivity(), "无效数据！",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        String extra = msi.getExtra();
        if (!StringUtil.isEmpty(extra)) {
            String[] exs = extra.split(Constant.OUTLINE_DIVIDER);
            if (exs.length >= 3) {
                String cityCode = exs[2];
                if (!cityCode.startsWith(LvApplication.getInstance().getCityCode())) {
                    Toast.makeText(getActivity(), "数据超出权限范围！",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        if (msi.isGeoPosValid()) {
            mMapLogic.drawMarkerAndCenterTo(msi);
        }

        //修改当前Activity销毁，showAtLocation崩溃问题。王泉
        if(!getActivity().isFinishing() && !hidden) {
            mOutlineLogic.showPopInfo(msi);
        }
    }

    @Override
    public void onClickList() {
        QueryCondition qc = LvApplication.getInstance().getMapQueryCondition();
        if (qc.getType() == QueryCondition.TYPE_KEY) {
            Intent intent = new Intent(getActivity(), MapSearchActivity.class);
            intent.putExtra("FromMap", true);
            intent.putExtra("AutoSearch", true);
            startActivityForResult(intent, Constant.SEARCH_INFO_MAP);
        } else {
            Intent intent = new Intent(getActivity(), WsRecordActivity.class);
            intent.putExtra("FromMap", true);
            startActivityForResult(intent, Constant.LIST_INFO);
        }
    }

    @Override
    public void onClickSearch() {
        Intent intent = new Intent(getActivity(), MapSearchActivity.class);
        intent.putExtra("FromMap", true);
        intent.putExtra("AutoSearch", false);
        startActivityForResult(intent, Constant.SEARCH_INFO_MAP);
    }

    @Override
    public void onViewDetail(String type, String key, String city) {
        Intent intent = new Intent(getActivity(), WsDetailActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("key", key);
        intent.putExtra("city", city);
        startActivity(intent);
    }

    @Override
    public void onViewQRCode(String type, String key, String city) {
        Intent intent = new Intent(getActivity(), QRCodeActivity.class);
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

        ListDialog.show(getActivity(), contentList, -1, new ListDialog.OnSelectedListener() {
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
                        getActivity().startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public void onExtra(MapSearchInfo msi) {

    }

    @Override
    public void onUpdateAddress(MapSearchInfo msi) {

    }

    @Override
    public void onPositionSelected(MapSearchInfo msi) {
        if(!getActivity().isFinishing() && !hidden) {
            mOutlineLogic.showPopInfo(msi);
        }
    }

    @Override
    public void onElementSelected(MapSearchInfo msi) {
        if(!getActivity().isFinishing() && !hidden) {
            mOutlineLogic.showPopInfo(msi);
        }
    }

    @Override
    public void onUnSelected() {
        if (mOutlineLogic.isShowing()) {
            mOutlineLogic.hidePopInfo();
        }
    }

    @Override
    public void onPositionDragStart(double lat, double lng) {

    }

    @Override
    public void onPositionDragEnd(double lat, double lng) {

    }

    @Override
    public void onThemeChange() {
        mSearchBarLogic.setSearchKey();
    }

    @Override
    public void onLoadThemeData(int type) {
        QueryCondition qc = new QueryCondition();
        qc.setType(QueryCondition.TYPE_SYLX);

        String zqzdCode = PrefUserInfo.getInstance().getUserInfo("department_uuid");
        qc.setZqzdCode(zqzdCode);

        String grade = PrefUserInfo.getInstance().getUserInfo("department_grade");
        Log.e("department_grade", grade);
        if ("0".equals(grade)) {
            qc.setSylxCode(AppDefs.CompEleType.DETACHMENT.toString());
        } else if ("1".equals(grade)) {
            qc.setSylxCode(AppDefs.CompEleType.BATTALION.toString());
        } else  {
            qc.setSylxCode(AppDefs.CompEleType.SQUADRON.toString());
        }
        GeomEleBussiness xb = GeomEleBussiness.getInstance();
        List<GeomElementDBInfo> list = xb.loadGeomElement(qc, -1, 0);
        mMapLogic.zoomToSpan(list);

        mMapLogic.prepareDistributionData(type);
    }

    public void refresh() {
        mMapLogic.showMapPoi(PrefSetting.getInstance().getMapShowPoi());
        mMapLogic.setTrafficEnabled(PrefSetting.getInstance().getBaiduLuKuang());

        LinearLayout re_fire = (LinearLayout) getView().findViewById(R.id.re_fire);
        if (PrefSetting.getInstance().getDisasterModel()) {
            re_fire.setVisibility(View.VISIBLE);
            re_fire.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String content = "是否进入灾情模式！";
                    CustomAlertDialog.show(getActivity(), content, new CustomAlertDialog.OnDoingListener() {
                        @Override
                        public void onOK() {
                            Intent intent = new Intent(getActivity(), DisasterActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onNG() {

                        }
                    });
                }
            });
        } else {
            re_fire.setVisibility(View.GONE);
        }
    }

    private boolean hidden;
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;

        if (!hidden) {
            refresh();
        } else {
            endMeasure();
            mThemeLogic.hidePopOption();
            mNearLogic.hide();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapLogic.resume();
        if (!hidden) {
            refresh();
        }
    }

    @Override
    public void onDestroy() {        
        mMapLogic.destroy();
        mOutlineLogic.destroy();
        
        super.onDestroy();
    }

    @Override
    public void onSearchNear(MapSearchInfo msi, String type) {
        if (!msi.isGeoPosValid()) {
            return;
        }

        QueryCondition qc = LvApplication.getInstance().getMapQueryCondition();
        qc.setType(QueryCondition.TYPE_POSITION);
        qc.setLat(msi.getLat()/1e6);
        qc.setLng(msi.getLng()/1e6);
        qc.setName(msi.getName());
        qc.setAddress(msi.getAddress());
        qc.setSylxCode(type);

        HashMap<String, SearchSummaryInfo> summaryMap = mMapLogic.searchNear();
        if (StringUtil.isEmpty(qc.getSylxCode())) {
            mOutlineLogic.updateFireNear(summaryMap);
        }

        mSearchBarLogic.setSearchKey();
    }

    @Override
    public void onNearSetting(String dis) {
        PrefSetting.getInstance().setLoadDis(dis);

        Marker curMarker = mMapLogic.getPositionMark();
        QueryCondition qc = LvApplication.getInstance().getMapQueryCondition();
        if (qc.getType() != QueryCondition.TYPE_POSITION || curMarker == null) {
            Toast.makeText(getActivity(), "请在地图上选择位置！", Toast.LENGTH_SHORT).show();
            return;
        }

        Bundle bundle = curMarker.getExtraInfo();
        if (bundle == null) {
            return;
        }
        MapSearchInfo msi = (MapSearchInfo)bundle.getSerializable("MapSearchInfo");
        if (msi == null) {
            return;
        }

        HashMap<String, SearchSummaryInfo> summaryMap = mMapLogic.searchNear();
        if (StringUtil.isEmpty(qc.getSylxCode())) {
            mOutlineLogic.updateFireNear(summaryMap);
        }

        msi.setExtra("near");
        mMapLogic.drawMarker(msi);
    }

    @Override
    public void onNearShow() {

    }

    @Override
    public void onViewPano(String uid, double lat, double lng, String name) {
        Intent intent = new Intent(getActivity(), PanoActivity.class);
        intent.putExtra("type", PanoActivity.UID_STREET);
        intent.putExtra("title", name);
        intent.putExtra("uid", uid);
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);

        startActivity(intent);
    }

    @Override
    public void onViewPano(double lat, double lng, String name) {
        Intent intent = new Intent(getActivity(), PanoActivity.class);
        intent.putExtra("type", PanoActivity.GEO);
        intent.putExtra("title", name);
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);

        startActivity(intent);
    }

    @Override
    public void onViewPanoIndoor(String uid, double lat, double lng, String name) {
        Intent intent = new Intent(getActivity(), PanoActivity.class);
        intent.putExtra("type", PanoActivity.UID_INTERIOR);
        intent.putExtra("title", name);
        intent.putExtra("uid", uid);
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);

        startActivity(intent);
    }

    @Override
    public void onStartNav(double lat, double lng, String endName) {
        NavService.getInstance(getActivity()).doNav(lat, lng, endName);
    }

    @Override
    public void onPopDismiss() {
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        }
        return false;
    }

    @Override
    public void onMeasureCancel() {
        endMeasure();
    }

    @Override
    public void onLayerChanged(LayerSelect dto) {
        mMapLogic.refreshByLayer();
    }

    @Override
    public void onLayerGroupChanged(List<LayerSelect> selectedList) {
        mMapLogic.refreshByLayer();
    }

    @Override
    public void onMeasureDis() {
        startMeasure(MeasureLogic.DIS_TYPE_DIS);
    }

    @Override
    public void onMeasureArea() {
        startMeasure(MeasureLogic.DIS_TYPE_AREA);
    }

    private void startMeasure(int dis_type) {
        if (popCaldisInfo != null && popCaldisInfo.isShowing()) {
            return;
        }

        re_title.setVisibility(View.INVISIBLE);
        re_option.setVisibility(View.INVISIBLE);

        int[] location = new int[2];
        re_title.getLocationOnScreen(location);

        View popView = getActivity().getLayoutInflater().inflate(
                R.layout.popup_measure_result, null);

        popCaldisInfo = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, false);
        popCaldisInfo.setOutsideTouchable(false);
        popCaldisInfo.showAtLocation(getView(), Gravity.TOP, 0, location[1]);

        mMeasureLogic = new MeasureLogic(popView, getActivity());
        mMeasureLogic.setListener(this);
        mMeasureLogic.setDisType(dis_type);
        mMeasureLogic.setParentView(getView());
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

        if(mMeasureLogic != null) {
            mMeasureLogic.endMeasure();
        }
        mMeasureLogic = null;
        mMapLogic.setMeasureLogic(mMeasureLogic);
    }
}
