package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.TextureMapView;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.model.common.OrgDataCountSummary;
import com.moft.xfapply.model.common.QueryCondition;
import com.moft.xfapply.model.common.SearchSummaryInfo;
import com.moft.xfapply.model.entity.GeomElementDBInfo;
import com.moft.xfapply.model.entity.MapSearchInfo;
import com.moft.xfapply.model.external.AppDefs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapLogic extends MapCommon {
    private OnMapLogicListener mListener = null;

    private int mCurDistributionGrade = -1;
    private int mDistributionType = -1;
    private boolean mDistributionZooming = false;

    public MapLogic(View v, Activity a) {
        super(v, a);
    }

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
        setPositionMarkResId(R.drawable.cur_pos_icon);

        String cityCode = LvApplication.getInstance().getCityCode();
        if ("2102".equals(cityCode)) { // 只有大连才能查看在线车辆
            drawVehicleMarkers();
        }
    }

    private int getDistributionGrade() {
        int grade = 0;
        float zoom = mBaiduMap.getMapStatus().zoom;
        if (zoom < 7.6) {
            grade = 0;
        } else if (zoom >= 7.6 && zoom < 11.6) {
            grade = 1;
        } else if (zoom >= 11.6 && zoom < 14.4) {
            grade = 2;
        } else {
            grade = 3;
        }
        return grade;
    }

    public void prepareDistributionData(int type) {
        clearMap();
        mDistributionZooming = true;
        mDistributionType = type;

        String code = "";
        String name = "";
        if (0 == type) {
            code = AppDefs.CompEleType.KEY_UNIT.toString();
            name = "重点单位";
        } else if (1 == type) {
            code = AppDefs.CompEleType.SQUADRON.toString();
            name = "执勤队站";
        } else if (2 == type) {
            code = AppDefs.CompEleType.WATERSOURCE_FIRE_HYDRANT.toString();
            name = "消火栓";
        } else if (3 == type) {
            code = AppDefs.CompEleType.WATERSOURCE_CRANE.toString();
            name = "消防水鹤";
        } else if (4 == type) {
            code = AppDefs.CompEleType.WATERSOURCE_FIRE_POOL.toString();
            name = "消防水池";
        } else if (5 == type) {
            code = AppDefs.CompEleType.WATERSOURCE_NATURE_INTAKE.toString();
            name = "天然取水点";
        } else {
            return;
        }

        QueryCondition qc = LvApplication.getInstance().getMapQueryCondition();
        qc.setType(QueryCondition.TYPE_SYLX);

        qc.setSylxCode(code);
        qc.setSylx(name);
    }

    private void loadDistributionData(int type, DrawType drawType) {
        if (type == -1) {
            return;
        }

        if (drawType == DrawType.UPDATE && mDistributionType == type) {
            int grade = getDistributionGrade();
            if (grade == mCurDistributionGrade) {
                drawDistributionMarkers(mDistributionDataList, DrawType.UPDATE);
                return;
            }
        }

        mCurDistributionGrade = getDistributionGrade();

        QueryCondition qc = LvApplication.getInstance().getMapQueryCondition();
        List<OrgDataCountSummary> list = GeomEleBussiness.getInstance().
                getGeomElementCountByOrg(qc, mCurDistributionGrade);

        drawDistributionMarkers(list, DrawType.NEW);
    }

    public void loadElement() {
        QueryCondition qc = LvApplication.getInstance().getMapQueryCondition();

        GeomEleBussiness xb = GeomEleBussiness.getInstance();
        ArrayList<GeomElementDBInfo> list = xb.loadGeomElement(qc, -1, 0);

        drawCluster(list, DrawType.NEW);
    }

    public HashMap<String, SearchSummaryInfo> searchNear() {
        QueryCondition qc = LvApplication.getInstance().getMapQueryCondition();
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
    public void onPositionClick(MapSearchInfo msi) {
        if (mListener != null) {
            mListener.onPositionSelected(msi);
        }
    };

    @Override
    public void onDistributionClick(OrgDataCountSummary distributionSummary) {
        Toast.makeText(getActivity(),
                "名称: " + distributionSummary.getOrgName() +
                " 数量: " + distributionSummary.getCount(),
                Toast.LENGTH_SHORT).show();
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
        mCurDistributionGrade = -1;
        mDistributionType = -1;
    }

    @Override
    public void onMapStatusChanged() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mDistributionZooming) {
                    mDistributionZooming = false;
                    loadDistributionData(mDistributionType, DrawType.NEW);
                } else {
                    loadDistributionData(mDistributionType, DrawType.UPDATE);
                }
            }
        }, 200);
    }

    public void resume() {
        if(mMapView != null) {
            mMapView.onResume();
        }
    }

    public void setListener(OnMapLogicListener mListener) {
        this.mListener = mListener;
    }

    public interface OnMapLogicListener{
        void onPositionSelected(MapSearchInfo msi);
        void onElementSelected(MapSearchInfo msi);
        void onUnSelected();
        void onPositionDragStart(double lat, double lng);
        void onPositionDragEnd(double lat, double lng);
    }
}
