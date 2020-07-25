package com.moft.xfapply.service;

import java.util.Date;

import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.utils.DateUtil;
import com.moft.xfapply.utils.StringUtil;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;

public class LocationService extends Service {
    private MyReceiver receiver = null;
    private boolean threadDisable = false;
    private String lon = "";
    private String lat = "";
    private Date time = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 启动服务
        startService(new Intent(this, GpsService.class));
        
        // 注册广播
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.moft.xfapply.location.GpsService");
        registerReceiver(receiver, filter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!threadDisable) {
                    int sleepTime = Constant.UPLOAD_POSITION_INTERVAL;
                    
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        threadDisable = true;

        // 结束GPS服务
        unregisterReceiver(receiver);        
        stopService(new Intent(this, GpsService.class));

        super.onDestroy();
    }

    // 获取广播数据
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String lonTemp = bundle.getString("lon");
            if (!StringUtil.isEmpty(lonTemp)) {
                lon = lonTemp;
                lat = bundle.getString("lat");
                time = DateUtil.toDate(bundle.getString("time"));
                
                LvApplication.getInstance().setCurLat(lat);
                LvApplication.getInstance().setCurLng(lon);
            }
        }
    }
}
