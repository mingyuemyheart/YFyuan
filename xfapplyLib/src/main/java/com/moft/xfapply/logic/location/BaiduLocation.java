package com.moft.xfapply.logic.location;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import android.content.Context;

public class BaiduLocation {
    private Context mContext = null;
    private LocationClient mLocClient = null;
    private MyLocationListenner myListener = new MyLocationListenner();
    private BDLocation location = null;
    
    public BaiduLocation(Context cxt) {
        mContext= cxt;
    }
    
    public void start() {        
        if (mLocClient != null && mLocClient.isStarted()) {
            return;
        }
        
        // 定位初始化
        mLocClient = new LocationClient(mContext);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(2000);
        option.setIsNeedAddress(true);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null)
                return;       

            location = bdLocation;
        }
    }
    
    public void close() {
        mLocClient.stop();
        mLocClient = null;
    }

    public BDLocation getLocation() {
        return location;
    }
}
