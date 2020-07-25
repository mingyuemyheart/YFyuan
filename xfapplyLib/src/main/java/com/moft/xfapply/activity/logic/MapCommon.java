package com.moft.xfapply.activity.logic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapBaseIndoorMapInfo;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.mapapi.search.district.DistrictSearch;
import com.baidu.mapapi.search.district.DistrictSearchOption;
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.moft.xfapply.R;
import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.base.ViewLogic;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.base.IGeomElementInfo;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.common.EleCondition;
import com.moft.xfapply.model.common.LayerSelect;
import com.moft.xfapply.model.common.OrgDataCountSummary;
import com.moft.xfapply.model.entity.GeomElementDBInfo;
import com.moft.xfapply.model.entity.MapSearchInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.dto.VehicleRealDataDTO;
import com.moft.xfapply.utils.CoordinateUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.Utils;
import com.moft.xfapply.utils.cluster.UtilMap;
import com.moft.xfapply.utils.cluster.clustering.Cluster;
import com.moft.xfapply.utils.cluster.clustering.ClusterItem;
import com.moft.xfapply.utils.cluster.clustering.ClusterManager;
import com.moft.xfapply.widget.indoor.BaseStripAdapter;
import com.moft.xfapply.widget.indoor.StripListView;
import com.moft.xfapply.widget.route.DrivingRouteOverlay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sxf on 2019-04-23.
 */

public class MapCommon extends ViewLogic implements
        BaiduMap.OnBaseIndoorMapListener {
    protected TextureMapView mMapView = null;
    protected BaiduMap mBaiduMap = null;
    private GeoCoder mGeoCoder = GeoCoder.newInstance();
    private StripListView stripView;
    private BaseStripAdapter mFloorListAdapter;
    private MapBaseIndoorMapInfo mMapBaseIndoorMapInfo = null;
    private ClusterManager<MyItem> mClusterManager = null;

    private VehicleOverLayerLogic mVehicleLogic = null;
    private MeasureLogic mMeasureLogic = null;

    // 定位相关
    private LocationClient mLocClient = null;
    private MyLocationListenner locationListener = new MyLocationListenner();

    public enum DrawType {
        NEW, // 重新
        APPEND, // 追加
        UPDATE // 更新
    }
    private Map<String, Integer> resMap;
    // 位置Marker
    private Marker mPositionMark = null;
    private int mPositionMarkResId = R.drawable.cur_pos_icon;

    // POI Marker
    public Marker mPoiMark = null;

    // ELement选中Marker
    private Marker mSelectedMark = null;

    // MarkerList
    protected List<GeomElementDBInfo> mMarkerDataList = new ArrayList<>();
    private List<Marker> mMarkerList = new ArrayList<>();

    // 分布Marker
    private List<Marker> mMarkerDistributionList = new ArrayList<>();
    protected List<OrgDataCountSummary> mDistributionDataList = new ArrayList<>();

    // 聚合
    protected List<GeomElementDBInfo> mClusterDataList = new ArrayList<>();

    // 附近范围
    private String mNearCircleKey = "";
    private List<Overlay> mOverlayNearCircleList = new ArrayList<>();

    // 行车路线
    private List<DrivingRouteOverlay> routeOverlays = new ArrayList<>();

    public MapCommon(View v, Activity a) {
        super(v, a);
    }

    public void setMeasureLogic(MeasureLogic mMeasureLogic) {
        this.mMeasureLogic = mMeasureLogic;
        if (this.mMeasureLogic != null) {
            this.mMeasureLogic.setBaiduMap(mBaiduMap);
        }
    }

    protected void initData() {
        resMap = new HashMap<>();

        for (Dictionary dic : LvApplication.getInstance().getEleTypeGeoDicList()) {
            resMap.put(dic.getCode(), dic.getResMapId());
        }
    }

    protected void initMapSetting(){
        int mapType = PrefSetting.getInstance().getMapType();
        switch (mapType) {
            case PrefSetting.MAP_NONE:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);
                break;
            case PrefSetting.MAP_SATELITE:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case PrefSetting.MAP_NORMAL:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case PrefSetting.MAP_3D:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                float overlook = mBaiduMap.getMapStatus().overlook;
                MapStatus overStatus = new MapStatus.Builder().overlook(overlook-45).build();
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(overStatus));
                break;
        }

        boolean isBaiduLukang = PrefSetting.getInstance().getBaiduLuKuang();
        mBaiduMap.setTrafficEnabled(isBaiduLukang);

        mBaiduMap.showMapPoi(PrefSetting.getInstance().getMapShowPoi());

        mBaiduMap.setOnBaseIndoorMapListener(this);
        mBaiduMap.setIndoorEnable(true); // 设置打开室内图开关
        stripView = new StripListView(getActivity());
        RelativeLayout layout = (RelativeLayout) getView().findViewById(R.id.map_content);
        layout.addView(stripView);
        mFloorListAdapter = new BaseStripAdapter(getActivity());

        stripView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (mMapBaseIndoorMapInfo == null) {
                    stripView.setVisibility(View.GONE);
                    return;
                }
                String floor = (String) mFloorListAdapter.getItem(position);
                mBaiduMap.switchBaseIndoorMapFloor(floor, mMapBaseIndoorMapInfo.getID());
                mFloorListAdapter.setSelectedPostion(position);
                mFloorListAdapter.notifyDataSetInvalidated();
            }
        });

        mMapView.showZoomControls(false);
        mMapView.setLogoPosition(LogoPosition.logoPostionRightBottom);

        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL, true, null));

        // 定义点聚合管理类ClusterManager
        mClusterManager = new ClusterManager<MyItem>(getActivity(), mBaiduMap);
        // 设置地图监听，当地图状态发生改变时，进行点聚合运算
        mBaiduMap.setOnMapStatusChangeListener(mClusterManager);
        // 设置maker点击时的响应
        mBaiduMap.setOnMarkerClickListener(mClusterManager);

        mClusterManager.setOnMapStatusChangeListener(new ClusterManager.OnMapStatusChangeListener() {
            @Override
            public void onChanged() {
                onMapStatusChanged();
            }
        });

        mGeoCoder.setOnGetGeoCodeResultListener(mOnGetGeoCoderResultListener);
    }

    protected void initMapCenterPoint() {
        String departmentUuid = PrefUserInfo.getInstance().getUserInfo("department_uuid");
        LatLng centerPoint = GeomEleBussiness.getInstance().getStationLatLng(departmentUuid);

        if (centerPoint == null) {
            moveTo(LvApplication.getInstance().getCityName());
        } else {
            moveTo(centerPoint);
        }
    }

    protected void initMapOperation() {
        ImageButton btn_zoomin = (ImageButton) getView().findViewById(R.id.zoominBtn);
        ImageButton btn_zoomout = (ImageButton) getView().findViewById(R.id.zoomoutBtn);
        btn_zoomin.setOnClickListener(zoomClickListener);
        btn_zoomout.setOnClickListener(zoomClickListener);

        LinearLayout locate_button = (LinearLayout) getView().findViewById(R.id.locate_button);
        locate_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                doLocation();
            }
        });

        View re_clearmap = getView().findViewById(R.id.re_clearmap);
        re_clearmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearMap();
            }
        });
    }

    protected void initMapClickEvent() {
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng pt) {
                onMapEmptyClick();

                if (mMeasureLogic != null) {
                    mMeasureLogic.addPoint(pt);
                    return;
                }
            }

            @Override
            public boolean onMapPoiClick(MapPoi pt) {
                if (mMeasureLogic != null) {
                    mMeasureLogic.addPoint(pt.getPosition());
                    return true;
                }

                MapSearchInfo msi = new MapSearchInfo();
                msi.setType(Constant.MAP_SEARCH_POI);
                msi.setName(pt.getName().replace("\\",""));
                msi.setUid(pt.getUid());
                msi.setLat((int)pt.getPosition().latitudeE6);
                msi.setLng((int)pt.getPosition().longitudeE6);

                drawMarker(msi);
                onPositionClick(msi);
                return false;
            }
        });

        mBaiduMap.setOnMapLongClickListener(new BaiduMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng ll) {
                if (mMeasureLogic != null) {
                    return;
                }

                requestAddress(ll.latitude, ll.longitude);
            }
        });
    }

    protected void initMarkerEvent() {
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                if (mMeasureLogic != null) {
                    mMeasureLogic.addPoint(marker.getPosition());
                    return true;
                }

                doClickMarker(marker.getExtraInfo(), marker);
                return true;
            }
        });

        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                onPositionDragEnd(marker);
            }

            @Override
            public void onMarkerDragStart(Marker marker) {
                onPositionDragStart(marker);
            }
        });

        mBaiduMap.setOnPolylineClickListener(new BaiduMap.OnPolylineClickListener() {
            @Override
            public boolean onPolylineClick(Polyline polyline) {
                return doClickPolyLine(polyline);
            }
        });

        mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
            @Override
            public boolean onClusterClick(Cluster<MyItem> cluster) {
                if (mMeasureLogic != null) {
                    mMeasureLogic.addPoint(cluster.getPosition());
                    return true;
                }

                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngZoom(cluster.getPosition(),
                        mBaiduMap.getMapStatus().zoom + 1));
                return false;
            }
        });
        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
            @Override
            public boolean onClusterItemClick(MyItem item) {
                if (mMeasureLogic != null) {
                    mMeasureLogic.addPoint(item.getPosition());
                    return true;
                }

                doClickMarker(item.getExtraInfo(), item);
                return false;
            }
        });
    }

    private boolean doClickPolyLine(Polyline polyline) {
        for (DrivingRouteOverlay drivingRouteOverlay : routeOverlays) {
            drivingRouteOverlay.setFocus(false);
            for (Overlay mPolyline : drivingRouteOverlay.mOverlayList) {
                if (mPolyline instanceof Polyline && mPolyline.equals(polyline)) {
                    drivingRouteOverlay.setFocus(true);
                    break;
                }
            }
        }
        for (DrivingRouteOverlay drivingRouteOverlay : routeOverlays) {
            drivingRouteOverlay.removeFromMap();
            drivingRouteOverlay.addToMap();
        }
        return true;
    }

    public void clearMapPosition() {
        clearMarkers();
        clearCluster();
        clearDistributionMarkers();
        clearNearCircle();
        onClearMap();
    }

    public void clearMap() {
        clearMarkers();
        clearCluster();
        clearDistributionMarkers();
        clearVehicleMarkers();
        clearNearCircle();
        clearRouteLine();
        clearPositionMarker();
        clearPoiMarker();
        clearSeletedMarker();
        onClearMap();
    }

    public void clearMarkers() {
        for (Marker marker : mMarkerList) {
            marker.remove();
        }
        mMarkerList.clear();
        mMarkerDataList.clear();
    }

    public void drawMarkers(List<GeomElementDBInfo> list, DrawType drawType) {
        if (DrawType.NEW == drawType) {
            for (Marker marker : mMarkerList) {
                marker.remove();
            }
            mMarkerList.clear();
            mMarkerDataList.clear();
            mMarkerDataList.addAll(list);
        } else if (DrawType.APPEND == drawType) {
            mMarkerDataList.addAll(list);
        } else if (DrawType.UPDATE == drawType) {
            for (Marker marker : mMarkerList) {
                marker.remove();
            }
            mMarkerList.clear();
        }

        Map<String, LayerSelect> layerMap = getMarkerLayer();
        for (GeomElementDBInfo item : mMarkerDataList) {
            if (item.getDeleted() || !item.isGeoPosValid()) {
                continue;
            }
            //对应其他消防队伍分为4类
            LayerSelect ls = layerMap.get(item.getResEleType());
            if (ls == null || !ls.isSelected()) {
                continue;
            }

            Marker marker = drawMarker(item);
            mMarkerList.add(marker);
        }
    }

    public void clearCluster() {
        mClusterManager.clearItems();
        mClusterManager.cluster();
        mClusterDataList.clear();
    }

    public void drawCluster(List<GeomElementDBInfo> list, DrawType drawType) {
        if (list.size() <= 200) {
            drawMarkers(list, drawType);
            return;
        }

        if (DrawType.NEW == drawType) {
            mClusterManager.clearItems();
            mClusterManager.cluster();
            mClusterDataList.clear();
            mClusterDataList.addAll(list);
        } else if (DrawType.APPEND == drawType) {
            mClusterDataList.addAll(list);
        } else if (DrawType.UPDATE == drawType) {
            mClusterManager.clearItems();
            mClusterManager.cluster();
        }

        Map<String, LayerSelect> layerMap = getMarkerLayer();
        List<MyItem> items = new ArrayList<MyItem>();
        for (GeomElementDBInfo item : mClusterDataList) {
            if (item.getDeleted() || !item.isGeoPosValid()) {
                continue;
            }
            //对应其他消防队伍分为4类
            LayerSelect ls = layerMap.get(item.getResEleType());
            if (ls == null || !ls.isSelected()) {
                continue;
            }

            MyItem myItem = getMyItem(item);
            items.add(myItem);
        }

        mClusterManager.addItems(items);
        mClusterManager.cluster();
    }

    public void clearDistributionMarkers() {
        for (Marker marker : mMarkerDistributionList) {
            marker.remove();
        }
        mMarkerDistributionList.clear();
        mDistributionDataList.clear();
    }

    public void drawDistributionMarkers(List<OrgDataCountSummary> list, DrawType drawType) {
        if (DrawType.NEW == drawType) {
            for (Marker marker : mMarkerDistributionList) {
                marker.remove();
            }
            mMarkerDistributionList.clear();
            mDistributionDataList.clear();
            mDistributionDataList.addAll(list);
        } else if (DrawType.APPEND == drawType) {
            mDistributionDataList.addAll(list);
        } else if (DrawType.UPDATE == drawType) {
            for (Marker marker : mMarkerDistributionList) {
                marker.remove();
            }
            mMarkerDistributionList.clear();
        }

        LatLng left = UtilMap.getInstance().getLeftCorner(mBaiduMap);
        LatLng right = UtilMap.getInstance().getRightCorner(mBaiduMap);
        for (OrgDataCountSummary dis : mDistributionDataList) {
            if (!dis.isGeoPosValid()) {
                continue;
            }
            LatLng point = new LatLng(Utils.convertToDouble(dis.getLatitude()),
                    Utils.convertToDouble(dis.getLongitude()));
            if (UtilMap.getInstance().compareLat(left, right, point)) {
                Marker marker = drawMarker(dis);
                mMarkerDistributionList.add(marker);
            }
        }
    }

    public void clearVehicleMarkers() {
        if(mVehicleLogic != null) {
            mVehicleLogic.clearMarker();
        }
    }

    public void drawVehicleMarkers() {
        if(mVehicleLogic == null) {
            mVehicleLogic = new VehicleOverLayerLogic(mBaiduMap);
        }
        Map<String, LayerSelect> layerMap = getMarkerLayer();
        LayerSelect ls = layerMap.get(Constant.GEO_TYPE_VEHICLE_GPS);

        boolean isVisible = true;
        if (ls == null || !ls.isSelected()) {
            isVisible = false;
        }
        mVehicleLogic.setVisible(isVisible);
    }

    public void clearPositionMarker() {
        if (mPositionMark != null) {
            mPositionMark.remove();
            mPositionMark = null;
        }
    }

    public void clearPoiMarker() {
        if (mPoiMark != null) {
            mPoiMark.remove();
            mPoiMark = null;
        }
    }

    public void clearSeletedMarker() {
        if (mSelectedMark != null) {
            mSelectedMark.remove();
            mSelectedMark = null;
        }
    }

    public void drawMarkerAndCenterTo(MapSearchInfo msi) {
        if (!msi.isGeoPosValid()) {
            return;
        }
        drawMarker(msi);
        moveTo(new LatLng(msi.getLat()/1e6, msi.getLng()/1e6));
    }

    public Marker drawMarker(double lat, double lng, int resId) {
        LatLng point = new LatLng(lat, lng);

        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(resId);

        OverlayOptions option = new MarkerOptions().position(point)
                .icon(bitmap);

        Marker marker = (Marker)mBaiduMap.addOverlay(option);
        return marker;
    }

    public Marker drawMarker(MapSearchInfo msi) {
        if (msi == null) {
            return null;
        }
        if (!msi.isGeoPosValid()) {
            return null;
        }

        clearPoiMarker();
        clearSeletedMarker();

        Bundle bundle = new Bundle();
        bundle.putSerializable("MapSearchInfo", msi);

        if (Constant.MAP_SEARCH_POSITION.equals(msi.getType())) {
            if (mPositionMark != null) {
                mPositionMark.remove();
                mPositionMark = null;
            }

            mPositionMark = drawMarker(msi.getLat()/1e6,
                    msi.getLng()/1e6, mPositionMarkResId);
            mPositionMark.setZIndex(Constant.ZINDEX_3);
            mPositionMark.setExtraInfo(bundle);
            return mPositionMark;
        }

        if (Constant.MAP_SEARCH_POI.equals(msi.getType())) {
            if (mPoiMark != null) {
                mPoiMark.remove();
                mPoiMark = null;
            }

            mPoiMark = drawMarker(msi.getLat()/1e6,
                    msi.getLng()/1e6, R.drawable.icon_poi);
            mPoiMark.setAnchor(0.5f, 0.5f);
            mPoiMark.setZIndex(Constant.ZINDEX_2);
            mPoiMark.setExtraInfo(bundle);
            return mPoiMark;
        }

        if (Constant.MAP_SEARCH_FIRE.equals(msi.getType())) {
            if (mSelectedMark != null) {
                mSelectedMark.remove();
                mSelectedMark = null;
            }

            String extra = msi.getExtra();
            String[] exs = extra.split(Constant.OUTLINE_DIVIDER);
            if (exs == null || exs.length <= 0) {
                return mSelectedMark;
            }

            String resEleType = exs[3];
            Dictionary wsDic = null;
            for (Dictionary dic : LvApplication.getInstance().getEleTypeGeoDicList()) {
                if (dic.getCode().equals(resEleType)) {
                    wsDic = dic;
                    break;
                }
            }
            if (wsDic == null) {
                return null;
            }

            int resId = wsDic.getResCurMapId();
            mSelectedMark = drawMarker(msi.getLat()/1e6, msi.getLng()/1e6, resId);
            mSelectedMark.setZIndex(Constant.ZINDEX_2);
            mSelectedMark.setExtraInfo(bundle);
            return mSelectedMark;
        }

        if (Constant.MAP_VEHICLE_GPS.equals(msi.getType())) {
            if (mSelectedMark != null) {
                mSelectedMark.remove();
                mSelectedMark = null;
            }

            mSelectedMark = drawMarker(msi.getLat()/1e6,
                    msi.getLng()/1e6, R.drawable.marker_xfc_cur);
            mSelectedMark.setZIndex(Constant.ZINDEX_2);
            mSelectedMark.setExtraInfo(bundle);
            return mSelectedMark;
        }

        return null;
    }

    public Marker drawMarker(GeomElementDBInfo ws) {
        int resId = getResId(ws);

        Marker marker = drawMarker(Utils.convertToDouble(ws.getLatitude()),
                Utils.convertToDouble(ws.getLongitude()), resId);
        marker.setZIndex(Constant.ZINDEX_1);
        Bundle bundle = new Bundle();
        bundle.putSerializable("eleType", ws.getEleType());
        bundle.putSerializable("uniq_Key", ws.getUuid());
        bundle.putSerializable("city", ws.getBelongtoGroup());
        marker.setExtraInfo(bundle);

        return marker;
    }

    public Marker drawMarker(OrgDataCountSummary org) {
        ViewGroup container = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.text_bubble, null);
        RelativeLayout rotationLayout = (RelativeLayout) container.getChildAt(0);
        TextView textView = (TextView) rotationLayout.findViewById(R.id.text);
        ImageView imageView = (ImageView) rotationLayout.findViewById(R.id.image);

        int bucket = org.getCount();
        int textSize = 0;
        int resId;
        int nStep = 8;
        int nSizeS = 40;
        if (bucket < 30) {
            resId = R.drawable.marker_cluster_10;
            textSize = 14;
            Utils.setImageSize(getActivity(), imageView, nSizeS);
        } else if (bucket >= 30 && bucket < 100) {
            resId = R.drawable.marker_cluster_50;
            textSize = 14;
            Utils.setImageSize(getActivity(), imageView, nSizeS + 1 * nStep);
        } else if (bucket >= 100 && bucket < 1000) {
            resId = R.drawable.marker_cluster_30;
            textSize = 14;
            Utils.setImageSize(getActivity(), imageView, nSizeS + 2 * nStep);
        } else if (bucket >= 1000 && bucket < 10000) {
            resId = R.drawable.marker_cluster_100;
            textSize = 14;
            Utils.setImageSize(getActivity(), imageView, nSizeS + 3 * nStep);
        } else {
            resId = R.drawable.marker_cluster_50;
            textSize = 14;
            Utils.setImageSize(getActivity(), imageView, nSizeS + 4 * nStep);
        }
        textView.setTextSize(textSize);
        imageView.setImageResource(resId);

        textView.setText("" + bucket);

        int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        container.measure(measureSpec, measureSpec);

        Bitmap bitmap = Bitmap.createBitmap(container.getMeasuredWidth(),
                container.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(Color.TRANSPARENT);
        Canvas canvas = new Canvas(bitmap);

        container.layout(0, 0, container.getMeasuredWidth(), container.getMeasuredHeight());
        container.draw(canvas);

        LatLng point = new LatLng(Utils.convertToDouble(org.getLatitude()),
                Utils.convertToDouble(org.getLongitude()));

        BitmapDescriptor descriptor = BitmapDescriptorFactory.fromBitmap(bitmap);

        OverlayOptions option = new MarkerOptions().position(point)
                .icon(descriptor);

        Marker marker = (Marker)mBaiduMap.addOverlay(option);
        marker.setZIndex(Constant.ZINDEX_0);

        Bundle bundle = new Bundle();
        bundle.putSerializable("OrgDataCountSummary", org);
        marker.setExtraInfo(bundle);

        return marker;
    }

    public void refreshByLayer() {
        // refresh Cluster
        drawCluster(mClusterDataList, DrawType.UPDATE);

        // refresh Marker
        drawMarkers(mMarkerDataList, DrawType.UPDATE);

        // refresh Station

        // refresh Vehicle
        drawVehicleMarkers();
    }

    private void doClickMarker(Bundle bundle, Object object) {
        if (bundle == null) {
            return;
        }

        // 分布Marker
        OrgDataCountSummary orgBundle = (OrgDataCountSummary)
                bundle.getSerializable("OrgDataCountSummary");
        if (orgBundle != null) {
            onDistributionClick(orgBundle);
            return;
        }

        // Position Marker
        MapSearchInfo msiBundle = (MapSearchInfo)bundle.getSerializable("MapSearchInfo");
        if (msiBundle != null) {
            onPositionClick(msiBundle);
            return;
        }

        // 资源 Marker
        if (bundle.getString("eleType") != null) {
            EleCondition ec = new EleCondition();
            ec.setUuid(bundle.getString("uniq_Key"));
            ec.setType(bundle.getString("eleType"));
            ec.setCityCode(bundle.getString("city"));

            IGeomElementInfo gei = GeomEleBussiness.getInstance().getSpecGeomEleInfoByUuid(ec);

            MapSearchInfo msi = new MapSearchInfo();
            msi.setType(Constant.MAP_SEARCH_FIRE);
            msi.setName(gei.getName());
            msi.setAddress(gei.getAddress());
            msi.setUid("");
            msi.setLat((int) (Utils.convertToDouble(gei.getLatitude()) * 1e6));
            msi.setLng((int) (Utils.convertToDouble(gei.getLongitude()) * 1e6));
            String extra = gei.getEleType() + Constant.OUTLINE_DIVIDER +
                    gei.getUuid() + Constant.OUTLINE_DIVIDER + ec.getCityCode();
            //对应其他消防队伍分为4类
            extra += Constant.OUTLINE_DIVIDER + gei.getResEleType();
            extra += Constant.OUTLINE_DIVIDER + gei.getOutline();
            msi.setExtra(extra);

            drawMarker(msi);
            onElementClick(msi);
            return;
        }

        // 车辆 Marker
        VehicleRealDataDTO vehicleDTO = (VehicleRealDataDTO)
                bundle.getSerializable(Constant.GEO_TYPE_VEHICLE_GPS);
        if (vehicleDTO != null) {
            MapSearchInfo msi = new MapSearchInfo();
            msi.setType(Constant.MAP_VEHICLE_GPS);
            msi.setName(vehicleDTO.getDepName());
            msi.setAddress(vehicleDTO.getLocation());
            msi.setUid("");
            msi.setLat((int) (vehicleDTO.getbLat() * 1e6));
            msi.setLng((int) (vehicleDTO.getbLon() * 1e6));
            msi.setTag(vehicleDTO);
            String extra = vehicleDTO.getOutline();
            msi.setExtra(extra);

            drawMarker(msi);
            onElementClick(msi);
            return;
        }
    }

    public void clearNearCircle() {
        for (Overlay ol : mOverlayNearCircleList) {
            ol.remove();
        }
        mOverlayNearCircleList.clear();
        mNearCircleKey = "";
    }

    public void drawNearCircle(double lat, double lng) {
        int loadDis = PrefSetting.getInstance().getLoadDisNumber();
        String curKey = StringUtil.formatLatLng(lat) + StringUtil.formatLatLng(lng) + loadDis;
        if (mNearCircleKey.equals(curKey)) {
            return;
        }
        clearNearCircle();
        mNearCircleKey = curKey;

        int alpha = 0;
        List<Integer> disList = PrefSetting.getInstance().getLoadDisNumberList();
        for (Integer dis : disList) {
            drawCircle(lat, lng, dis, alpha);
        }
    }

    public void drawCircle(double lat, double lng, int radius, int alpha) {
        LatLng center = new LatLng(lat, lng);
        CircleOptions mCircleOptions = new CircleOptions().center(center)
                .radius(radius)
                .fillColor(Color.argb(alpha, 180, 180, 180)) //填充颜色
                .stroke(new Stroke(5, Color.rgb(216, 30, 6))); //边框宽和边框颜色

        Overlay mCircle = mBaiduMap.addOverlay(mCircleOptions);
        mCircle.setZIndex(Constant.ZINDEX_0);
        mOverlayNearCircleList.add(mCircle);

        String disText = " " + radius + "米 ";
        if (radius >= 1000) {
            int rr = radius / 1000;
            disText = " " + rr + "千米 ";
        }

        double[] latlng = CoordinateUtil.calLocation(0, lng, lat, radius);
        LatLng llText = new LatLng(latlng[0], latlng[1]);
        OverlayOptions mTextOptions = new TextOptions()
                .text(disText)
                .bgColor(Color.rgb(255, 255, 0))
                .fontSize(48)
                .fontColor(Color.rgb(0, 0, 0))
                .position(llText);
        Overlay mText = mBaiduMap.addOverlay(mTextOptions);
        mText.setZIndex(Constant.ZINDEX_0);
        mOverlayNearCircleList.add(mText);
    }

    public void drawRouteLine(String name, DrivingRouteLine routeLine, boolean focus) {
        DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMap);
        overlay.setName(name);
        overlay.setFocus(focus);
        overlay.setData(routeLine);
        overlay.addToMap();
        overlay.setFocus(false);
        overlay.zoomToSpan();
        routeOverlays.add(overlay);
    }

    public void clearRouteLine() {
        for (DrivingRouteOverlay overlay : routeOverlays) {
            overlay.removeFromMap();
        }
        routeOverlays.clear();
    }

    public void zoomToSpan(List<GeomElementDBInfo> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (GeomElementDBInfo geo : list) {
            if (!geo.isGeoPosValid()) {
                continue;
            }
            builder.include(new LatLng(
                    Utils.convertToDouble(geo.getLatitude()),
                    Utils.convertToDouble(geo.getLongitude())));
        }
        LatLngBounds bounds = builder.build();
        // 设置显示在屏幕中的地图地理范围
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(bounds);
        mBaiduMap.setMapStatus(u);
    }

    public MyItem getMyItem(GeomElementDBInfo item) {
        int resId = getResId(item);
        double lat = Utils.convertToDouble(item.getLatitude());
        double lng = Utils.convertToDouble(item.getLongitude());

        Bundle bundle = new Bundle();
        bundle.putSerializable("eleType", item.getEleType());
        bundle.putSerializable("uniq_Key", item.getUuid());
        bundle.putSerializable("city", item.getBelongtoGroup());

        MyItem myItem = new MyItem(new LatLng(lat,lng));
        myItem.setResId(resId);
        myItem.setExtraInfo(bundle);
        return myItem;
    }

    public void destroy() {
        if (mVehicleLogic != null) {
            mVehicleLogic.destroy();
        }
        stopLocation();
    }

    public Marker getPositionMark() {
        return mPositionMark;
    }

    public void setPositionMarkResId(int mPositionMarkResId) {
        this.mPositionMarkResId = mPositionMarkResId;
    }

    public int getResId(GeomElementDBInfo item) {
        int resId = R.drawable.icon_poi;
        String resType = item.getResEleType();
        if(resMap.containsKey(resType)) {
            resId = resMap.get(resType);
        }

        GeomEleBussiness gb = GeomEleBussiness.getInstance();
        boolean isAvailable = gb.isWaterSourceAvailable(item);
        if (!isAvailable) {
            if(AppDefs.CompEleType.WATERSOURCE_CRANE.toString().equals(item.getEleType())) {
                resId = R.drawable.marker_crane_map_no;
            } else if(AppDefs.CompEleType.WATERSOURCE_FIRE_HYDRANT.toString().equals(item.getEleType())) {
                resId = R.drawable.marker_hydrant_map_no;
            } else if(AppDefs.CompEleType.WATERSOURCE_FIRE_POOL.toString().equals(item.getEleType())) {
                resId = R.drawable.marker_pool_map_no;
            } else if(AppDefs.CompEleType.WATERSOURCE_NATURE_INTAKE.toString().equals(item.getEleType())) {
                resId = R.drawable.marker_naturalwater_map_no;
            }
        }
        return resId;
    }

    /**
     * 每个Marker点，包含Marker点坐标以及图标
     */
    public class MyItem implements ClusterItem {
        private final LatLng mPosition;
        private int resId = R.drawable.icon_poi;
        private Bundle extraInfo = null;

        public MyItem(LatLng latLng) {
            mPosition = latLng;
        }

        @Override
        public LatLng getPosition() {
            return mPosition;
        }

        @Override
        public BitmapDescriptor getBitmapDescriptor() {
            return BitmapDescriptorFactory
                    .fromResource(resId);
        }

        public Bundle getExtraInfo() {
            return extraInfo;
        }

        public void setExtraInfo(Bundle extraInfo) {
            this.extraInfo = extraInfo;
        }

        public int getResId() {
            return resId;
        }

        public void setResId(int resId) {
            this.resId = resId;
        }
    }

    public void doLocation() {
        if (mLocClient != null && mLocClient.isStarted()) {
            return;
        }
        // 定位初始化
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(locationListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(false);
        option.setCoorType("bd09ll");
        option.setScanSpan(10000);
        option.setIsNeedAddress(true);
        option.setIsNeedLocationPoiList(true);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    public void stopLocation() {
        if (mLocClient != null && mLocClient.isStarted()){
            mLocClient.stop();
            mLocClient = null;
        }
    }

    public void moveTo(String cityName) {
        DistrictSearch districtSearch = DistrictSearch.newInstance();
        districtSearch.setOnDistrictSearchListener(new OnGetDistricSearchResultListener() {
            @Override
            public void onGetDistrictResult(DistrictResult result) {
                if(result != null && result.error == SearchResult.ERRORNO.NO_ERROR) {
                    moveTo(result.getCenterPt());
                }
            }
        });
        districtSearch.searchDistrict(new DistrictSearchOption().cityName(cityName));
    }

    public void moveTo(LatLng ll) {
        if (ll == null) {
            return;
        }
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(ll));
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(mBaiduMap.getMaxZoomLevel() - 3));
    }

    public void setTrafficEnabled(boolean isEnabled) {
        mBaiduMap.setTrafficEnabled(isEnabled);
    }

    public void showMapPoi(boolean isEnabled) {
        mBaiduMap.showMapPoi(isEnabled);
        PrefSetting.getInstance().setMapShowPoi(isEnabled);
    }

    public void requestAddress(double lat, double lng) {
        LatLng mLatLng = new LatLng(lat, lng);

        ReverseGeoCodeOption mReverseGeoCodeOption = new ReverseGeoCodeOption();
        mReverseGeoCodeOption.location(mLatLng);

        mGeoCoder.reverseGeoCode(mReverseGeoCodeOption);
    }

    private PoiInfo getNearPoiInfo(List<PoiInfo> poiList) {
        PoiInfo poiTarget = null;
        if (poiList == null) {
            return poiTarget;
        }

        int rank = -1;
        for (PoiInfo item : poiList) {
            if (rank == -1) {
                rank = item.getDistance();
                poiTarget = item;
            } else if (item.getDistance() < rank) {
                rank = item.getDistance();
                poiTarget = item;
            }
        }

        return poiTarget;
    }

    private Poi getNearPoi(List<Poi> poiList) {
        Poi poiTarget = null;
        if (poiList == null) {
            return poiTarget;
        }

        double rank = 0;
        for (Poi item : poiList) {
            if (item.getRank() > rank) {
                rank = item.getRank();
                poiTarget = item;
            }
        }

        return poiTarget;
    }

    protected Map<String, LayerSelect> getMarkerLayer() {
        Map<String, LayerSelect> layerMap = new HashMap<>();

        List<Dictionary> dicList = LvApplication.getInstance().getEleTypeGeoDicList();
        for (Dictionary dic : dicList) {
            layerMap.put(dic.getCode(), new LayerSelect(dic.getResMapId(), dic.getValue(), dic.getCode()));
        }

        return layerMap;
    }

    @Override
    public void onBaseIndoorMapMode(boolean b, MapBaseIndoorMapInfo mapBaseIndoorMapInfo) {

        if (b) {
            if (mapBaseIndoorMapInfo == null) {
                stripView.setVisibility(View.GONE);
                return;
            }
            if (mapBaseIndoorMapInfo.getFloors() == null ||
                    mapBaseIndoorMapInfo.getFloors().size() == 0) {
                stripView.setVisibility(View.GONE);
                return;
            }

            stripView.setVisibility(View.VISIBLE);
            mFloorListAdapter.setmFloorList(mapBaseIndoorMapInfo.getFloors());
            stripView.setStripAdapter(mFloorListAdapter);

        } else {
            stripView.setVisibility(View.GONE);
        }
        mMapBaseIndoorMapInfo = mapBaseIndoorMapInfo;
    }

    // 缩放按钮
    private View.OnClickListener zoomClickListener = new View.OnClickListener() {
        public void onClick(View view) {
            int i = view.getId();
            if (i == R.id.zoominBtn) {
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomIn());
            } else if (i == R.id.zoomoutBtn) {
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomOut());
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            double lat = 0;
            double lng = 0;
            String address = "";
            String name = "";
            String uid = "";
            String city = "";
            switch (msg.what) {
                case 0:
                    ReverseGeoCodeResult result = (ReverseGeoCodeResult) msg.obj;
                    if (result == null) {
                        return;
                    }
                    lat = result.getLocation().latitude;
                    lng = result.getLocation().longitude;
                    address = StringUtil.getSimpleAddress(result.getAddress());
                    city = result.getAddressDetail().city;
                    PoiInfo poiTarget = getNearPoiInfo(result.getPoiList());
                    if (poiTarget != null) {
                        name = poiTarget.name;
                        uid = poiTarget.uid;
                    }
                    break;
                case 1:
                    BDLocation location = (BDLocation) msg.obj;
                    if(location == null) {
                        return;
                    }
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    address = StringUtil.getSimpleAddress(location.getAddrStr());
                    city = location.getCity();
                    Poi poiTarget1 = getNearPoi(location.getPoiList());
                    if (poiTarget1 != null) {
                        name = poiTarget1.getName();
                        uid = poiTarget1.getId();
                    }
                    moveTo(new LatLng(lat, lng));
                    break;
                case 2:
                    Toast.makeText(getActivity(), "位置获取中...",
                            Toast.LENGTH_SHORT).show();
                    return;
                default:
                    break;
            }

            MapSearchInfo msi = new MapSearchInfo();
            msi.setType(Constant.MAP_SEARCH_POSITION);
            msi.setName(name);
            msi.setUid(uid);
            msi.setLat((int)(lat * 1e6));
            msi.setLng((int)(lng* 1e6));
            msi.setAddress(address);

            drawMarker(msi);
            onPositionClick(msi);
        }
    };

    private class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null  || location.getLocType() == BDLocation.TypeServerError) {
                return;
            }
            if (location.getLongitude() < 70 || location.getLongitude() > 140 ||
                    location.getLatitude() < 10 || location.getLatitude() > 60) {
                Message msg = new Message();
                msg.what = 2;
                mHandler.sendMessage(msg);
                return;
            }
            mLocClient.stop();

            Message msg = new Message();
            msg.what = 1;
            msg.obj = location;

            mHandler.sendMessage(msg);
        }
    }

    private OnGetGeoCoderResultListener mOnGetGeoCoderResultListener = new OnGetGeoCoderResultListener() {

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                return;
            }
            Message msg = new Message();
            msg.what = 0;
            msg.obj = result;

            mHandler.sendMessage(msg);
            return;
        }

        @Override
        public void onGetGeoCodeResult(GeoCodeResult result) {
        }
    };

    // 定制RouteOverly
    private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.marker_zqzd_map);
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_fire);
        }

        @Override
        public int getLineColor() {
            return Color.rgb(51, 69, 187);
        }
    }

    protected void onPositionClick(MapSearchInfo msi) {

    };
    protected void onDistributionClick(OrgDataCountSummary distributionSummary) {

    };
    protected void onElementClick(MapSearchInfo msi) {

    };
    protected void onMapStatusChanged() {

    };
    protected void onMapEmptyClick() {

    };
    protected void onClearMap() {

    };
    protected void onPositionDragStart(Marker marker) {

    };
    protected void onPositionDragEnd(Marker marker) {

    };
}
