package com.moft.xfapply.service;

import com.baidu.location.BDLocation;
import com.moft.xfapply.logic.location.BaiduLocation;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class GpsService extends Service {
    private BaiduLocation bdLocation = null;
    private boolean threadDisable = false;

    @Override
    public void onCreate() {
        super.onCreate();
        
        bdLocation = new BaiduLocation(GpsService.this);
        bdLocation.start();
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!threadDisable) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (bdLocation == null) {
                        continue;
                    }
                    // 获取经纬度
                    BDLocation location = bdLocation.getLocation();
                    if (location == null) {
                        continue;
                    }
                    
                    Intent intent = new Intent();
                    intent.putExtra("type", "百度定位");
                    intent.putExtra("lat", location.getLatitude() + "");
                    intent.putExtra("lon", location.getLongitude() + "");
                    intent.putExtra("alt", location.getAltitude() + "");
                    intent.putExtra("acc", location.getRadius() + "");
                    intent.putExtra("time", location.getTime() + "");
                    intent.putExtra("satiCount", location.getSatelliteNumber() + "");
                    intent.setAction("com.moft.xfapply.location.GpsService");
                    sendBroadcast(intent);
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        threadDisable = true;
        if (bdLocation != null) {
            bdLocation.close();
            bdLocation = null;
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}
