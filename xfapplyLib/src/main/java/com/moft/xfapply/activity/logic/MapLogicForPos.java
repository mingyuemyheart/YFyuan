package com.moft.xfapply.activity.logic;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.moft.xfapply.R;
import com.moft.xfapply.model.common.ComLocation;
import com.moft.xfapply.utils.StringUtil;

public class MapLogicForPos extends MapBaseLogic {
    public MapLogicForPos(View v, Activity a) {
        super(v, a);
    }

    private RelativeLayout re_address;
    private TextView tv_address;
    private ImageView iv_cur_pos;

    private Marker mCurPosMark = null;
    private ComLocation comLocation = new ComLocation();
    private boolean isEditable;

    public void init(boolean isEditable) {
        this.isEditable = isEditable;
        mMapView = (MapView) getView().findViewById(R.id.bmapView);
        initMap(mMapView);

        re_address = (RelativeLayout) getView().findViewById(R.id.re_address);
        tv_address = (TextView) getView().findViewById(R.id.tv_address);
        iv_cur_pos = (ImageView)getView().findViewById(R.id.iv_cur_pos);
        if(!isEditable) {
            re_address.setVisibility(View.GONE);
            tv_address.setVisibility(View.GONE);
            iv_cur_pos.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onMapSetting() {
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
                if(isEditable) {
                    tv_address.setText("定位中...");
                    re_address.setVisibility(View.GONE);
                    tv_address.setVisibility(View.GONE);
                }
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                if(isEditable) {
                    LatLng center = mBaiduMap.getMapStatus().target;
                    getAddressAync(center.latitude, center.longitude);
                }
            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }
        });
    }

    public void setMapCenter(Double lat, Double lon) {
        super.setMapCenter(lat, lon);
        comLocation.setLat(lat);
        comLocation.setLng(lon);
        if(!isEditable) {
            drawMarker(R.drawable.cur_pos_icon);
        }
    }

    @Override
    protected void onLocationFinish(BDLocation location) {
        super.setMapCenter(location.getLatitude(), location.getLongitude());
        locClient.stop();

        if(isEditable) {
            Message msg = new Message();
            msg.what = 0;
            msg.obj = location;
            mHandler.sendMessage(msg);
        }
    }

    @Override
    protected void onGetAddressFinish(double latitude, double longitude, String address) {
        comLocation.setLat(latitude);
        comLocation.setLng(longitude);
        if(isEditable) {
            Message msg = new Message();
            msg.what = 1;
            msg.obj = address;
            mHandler.sendMessage(msg);
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String address = "";
            switch (msg.what) {
                case 0:
                    BDLocation loc = (BDLocation)msg.obj;
                    comLocation.setLat(loc.getLatitude());
                    comLocation.setLng(loc.getLongitude());
                    address = loc.getAddrStr();
                    if (StringUtil.isEmpty(address)) {
                        getAddressAync(loc.getLatitude(), loc.getLongitude());
                    } else {
                        setShowPos(address);
                    }
                    break;
                case 1:
                    address = (String) msg.obj;
                    setShowPos(address);
                    break;
                default:
                    break;
            }
        }
    };

    private void drawMarker(int resId) {
        if(mCurPosMark != null) {
            mCurPosMark.remove();
        }
        LatLng point = new LatLng(comLocation.getLat(), comLocation.getLng());

        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(resId);

        OverlayOptions option = new MarkerOptions().position(point)
                .icon(bitmap);

        mCurPosMark = (Marker)mBaiduMap.addOverlay(option);
    }

    private void setShowPos(String address) {
        if (!StringUtil.isEmpty(address)) {
            tv_address.setText(address);
            re_address.setVisibility(View.VISIBLE);
            tv_address.setVisibility(View.VISIBLE);
        } else {
            tv_address.setText("定位中...");
            re_address.setVisibility(View.GONE);
            tv_address.setVisibility(View.GONE);
        }
        comLocation.setAddress(address);
    }

    public ComLocation getComLocation() {
        return comLocation;
    }
}
