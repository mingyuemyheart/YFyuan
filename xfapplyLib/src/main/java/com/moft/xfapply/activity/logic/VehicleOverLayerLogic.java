package com.moft.xfapply.activity.logic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.moft.xfapply.R;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.logic.network.OkHttpClientManager;
import com.moft.xfapply.logic.setting.PrefSetting;
import com.moft.xfapply.model.external.dto.VehicleRealDataDTO;
import com.moft.xfapply.utils.GsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Request;

/**
 * Created by Administrator on 2017/11/10 0010.
 */

public class VehicleOverLayerLogic {
    private static final int INTERVAL = 10 * 1000;
    private static final int MSG_UPDATE_VEHICLE = 0;

    private BaiduMap baiduMap;
    private Timer timer;
    private List<Marker> markerList = new ArrayList<>();
    private boolean visible;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_UPDATE_VEHICLE:
                    requestGetVehicles();
                    break;
            }
        }
    };

    public VehicleOverLayerLogic(BaiduMap baiduMap) {
        this.baiduMap = baiduMap;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;

        if (this.visible) {
            if(timer == null) {
                timer = new Timer();
            }
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(MSG_UPDATE_VEHICLE);
                }
            }, 0, INTERVAL);
        } else {
            if(timer != null) {
                timer.cancel();
                timer = null;
            }
            for(Marker marker : markerList) {
                marker.remove();
            }
            markerList.clear();
        }
    }

    public void destroy() {
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void requestGetVehicles() {
        final Gson gson = GsonUtil.create();
        OkHttpClientManager http = OkHttpClientManager.getInstance();
        http.postAsyn(http.convertToURL(Constant.VEHICLES_GPS_PORT, Constant.METHOD_GET_VEHICLES_GPS), new OkHttpClientManager.ResultCallback<List<VehicleRealDataDTO>>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(List<VehicleRealDataDTO> list) {
                if (list != null && list.size() > 0) {
                    if(markerList.size() > 0) {
                        for(Marker marker : markerList) {
                            marker.remove();
                        }
                        markerList.clear();
                    }
                    for (VehicleRealDataDTO item : list) {
                        markerList.add(drawVehicleMarker(item));
                    }
                }
            }
        }, gson.toJson(new Object()));
    }

    private final String convertToURL(String methodName) {
        return "http://" + PrefSetting.getInstance().getServerIP() + ":" + Constant.VEHICLES_GPS_PORT + methodName;
    }

    public void clearMarker() {
        if(timer != null) {
            timer.cancel();
            timer = null;
        }

        for(Marker marker : markerList) {
            marker.remove();
        }
        markerList.clear();
    }

    private Marker drawVehicleMarker(VehicleRealDataDTO vehicle) {
        if (vehicle.getbLat() == null || vehicle.getbLon() == null) {
            return null;
        }

        Marker marker = drawMarker(vehicle.getbLat(), vehicle.getbLon(), R.drawable.marker_xfc_map);
        marker.setZIndex(Constant.ZINDEX_0);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.GEO_TYPE_VEHICLE_GPS, vehicle);
        marker.setExtraInfo(bundle);

        return marker;
    }

    private Marker drawMarker(double lat, double lng, int resId) {
        LatLng point = new LatLng(lat, lng);
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(resId);
        OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
        Marker marker = (Marker)baiduMap.addOverlay(option);
        marker.setVisible(visible);

        return marker;
    }
}
