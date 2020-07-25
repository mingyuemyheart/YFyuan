package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.moft.xfapply.base.ViewLogic;

public abstract class MapBaseLogic extends ViewLogic {
    public MapBaseLogic(View v, Activity a) {
        super(v, a);
    }

    protected OnMapLogicListener mListener = null;

    protected MapView mMapView = null;
    protected BaiduMap mBaiduMap = null;
    protected GeoCoder mGeoCoder = GeoCoder.newInstance();
    protected LocationClient locClient;
    protected BDLocationListener mBDLocationListener = null;

    public void setListener(OnMapLogicListener l) {
        mListener = l;
    }

    public BaiduMap getBaiduMap() {
        return mBaiduMap;
    }

    public void zoomIn() {
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomIn());
    }

    public void zoomOut() {
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomOut());
    }

    public void doLocation() {
        if (locClient != null && locClient.isStarted()) {
            Toast.makeText(getActivity(), "正在定位中，请稍后...",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(30 * 1000);
        locClient.setLocOption(option);
        locClient.start();
    }

    protected void initMap(MapView mapView) {
        mMapView = mapView;
        mBaiduMap = mMapView.getMap();

        mMapView.showZoomControls(false);
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL, true, null));
        mBaiduMap.setTrafficEnabled(false);

        onMapSetting();

        initLocation();
    }

    protected void initLocation() {
        locClient = new LocationClient(getActivity());
        mBDLocationListener = new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                if(location == null) {
                    return;
                }

                MyLocationData locData = new MyLocationData.Builder()
                        .direction(100)
                        .latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);

                Toast.makeText(getActivity(), "定位成功",
                        Toast.LENGTH_SHORT).show();
                onLocationFinish(location);
            }
        };
        locClient.registerLocationListener(mBDLocationListener);
    }

    public boolean isMapNormal() {
        return mBaiduMap.getMapType() == BaiduMap.MAP_TYPE_NORMAL;
    }

    public void changeBaseMap() {
        if (mBaiduMap.getMapType() == BaiduMap.MAP_TYPE_NORMAL) {
            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        } else {
            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        }
    }

    public void destroy() {
        if (locClient != null) {
            if (mBDLocationListener != null) {
                locClient.unRegisterLocationListener(mBDLocationListener);
                mBDLocationListener = null;
            }

            if (locClient.isStarted()) {
                locClient.stop();
            }
            locClient = null;
        }
    }

    public void setMapCenter(Double lat, Double lon) {
        LatLng ll = new LatLng(lat, lon);
        MapStatusUpdate status = MapStatusUpdateFactory.newLatLngZoom(ll, 16);
        mBaiduMap.animateMapStatus(status);
    }

    public void getAddressAync(double latitude, double longitude) {
        LatLng mLatLng = new LatLng(latitude, longitude);

        ReverseGeoCodeOption mReverseGeoCodeOption = new ReverseGeoCodeOption();
        mReverseGeoCodeOption.location(mLatLng);

        mGeoCoder.setOnGetGeoCodeResultListener(mOnGetGeoCoderResultListener);
        mGeoCoder.reverseGeoCode(mReverseGeoCodeOption);
    }

    private OnGetGeoCoderResultListener mOnGetGeoCoderResultListener = new OnGetGeoCoderResultListener() {

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                return;
            }
            onGetAddressFinish(result.getLocation().latitude,
                    result.getLocation().longitude, result.getAddress());
        }

        @Override
        public void onGetGeoCodeResult(GeoCodeResult result) {
        }
    };

    abstract protected void onMapSetting();
    abstract protected void onLocationFinish(BDLocation location);
    abstract protected void onGetAddressFinish(double latitude,
                                               double longitude, String address);

    public interface OnMapLogicListener{
        void onPositionSelected(Double lat, Double lng, String address);
    }
}
