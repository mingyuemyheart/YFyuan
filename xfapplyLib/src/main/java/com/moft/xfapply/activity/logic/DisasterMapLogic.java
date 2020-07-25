package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.model.common.OrgDataCountSummary;
import com.moft.xfapply.model.common.QueryCondition;
import com.moft.xfapply.model.common.SearchSummaryInfo;
import com.moft.xfapply.model.entity.GeomElementDBInfo;
import com.moft.xfapply.model.entity.MapSearchInfo;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sxf on 2019-04-21.
 */

public class DisasterMapLogic extends MapCommon {
    public DisasterMapLogic(View v, Activity a) {
        super(v, a);
    }

    private MapLogic.OnMapLogicListener mListener = null;

    // 灾害Marker
    private Marker mDisasterMark = null;

    public BaiduMap getBaiduMap() {
        return mBaiduMap;
    }

    public void init() {
        mMapView = (TextureMapView) getView().findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

        initData();
        initMapSetting();
        initMapCenterPoint();
        initMapOperation();
        initMarkerEvent();
        initMapClickEvent();

        String cityCode = LvApplication.getInstance().getCityCode();
        if ("2102".equals(cityCode)) { // 只有大连才能查看在线车辆
            drawVehicleMarkers();
        }
    }

    public void setListener(MapLogic.OnMapLogicListener listener) {
        this.mListener = listener;
    }

    public void drawDisasterMarker(MapSearchInfo msi) {
        if (msi == null) {
            return;
        }
        if (!msi.isGeoPosValid()) {
            return;
        }
        if (mDisasterMark != null) {
            mDisasterMark.remove();
            mDisasterMark = null;
        }

        mDisasterMark = drawMarker(msi.getLat()/1e6,
                msi.getLng()/1e6, R.drawable.icon_fire);
        mDisasterMark.setDraggable(true);
        mDisasterMark.setZIndex(Constant.ZINDEX_4);

        Bundle bundle = new Bundle();
        bundle.putSerializable("MapSearchInfo", msi);
        mDisasterMark.setExtraInfo(bundle);
    }

    public HashMap<String, SearchSummaryInfo> searchNear() {
        QueryCondition qc = LvApplication.getInstance().getMapDisasterQueryCondition();
        if (qc.getType() != QueryCondition.TYPE_POSITION) {
            return null;
        }

        List<GeomElementDBInfo> wsList =
                GeomEleBussiness.getInstance().loadGeomElement(qc);
        HashMap<String, SearchSummaryInfo> summaryMap = new HashMap<>();
        for (GeomElementDBInfo cgedi : wsList) {
            if (summaryMap.get(cgedi.getEleType()) == null) {
                SearchSummaryInfo ssi = new SearchSummaryInfo();
                ssi.setType(cgedi.getEleType());
                ssi.setCount(1);
                summaryMap.put(cgedi.getEleType(), ssi);
            } else {
                SearchSummaryInfo ssi = summaryMap.get(cgedi.getEleType());
                ssi.setCount(ssi.getCount()+1);
            }
        }

        zoomToSpan(wsList);
        clearMapPosition();
        drawNearCircle(qc.getLat(), qc.getLng());
        drawCluster(wsList, MapCommon.DrawType.NEW);

        return summaryMap;
    }

    @Override
    public void onPositionDragStart(Marker marker) {
        if (mListener != null) {
            LatLng ll = marker.getPosition();
            mListener.onPositionDragStart(ll.latitude, ll.longitude);
        }
    }

    @Override
    public void onPositionDragEnd(Marker marker) {
        if (mListener != null) {
            LatLng ll = marker.getPosition();
            mListener.onPositionDragEnd(ll.latitude, ll.longitude);
        }
    }

    @Override
    public void onPositionClick(MapSearchInfo msi) {
        if (mListener != null) {
            mListener.onPositionSelected(msi);
        }
    };

    @Override
    public void onDistributionClick(OrgDataCountSummary distributionSummary) {
    };

    @Override
    public void onElementClick(MapSearchInfo msi) {
        if (mListener != null) {
            mListener.onElementSelected(msi);
        }
    };

    @Override
    public void onMapEmptyClick() {
        if (mListener != null) {
            mListener.onUnSelected();
        }
    };

    @Override
    public void onClearMap() {
    }

    @Override
    public void onMapStatusChanged() {
    }
}
