package com.moft.xfapply.utils.cluster;

import android.graphics.Point;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.LatLng;
import com.moft.xfapply.app.LvApplication;

/**
 * Created by sxf on 2019-04-03.
 */

public class UtilMap {

    private static UtilMap instance = null;

    private UtilMap() {
    }

    public static UtilMap getInstance() {
        if (instance == null) {
            instance = new UtilMap();
        }
        return instance;
    }

    public static int getScreenWidth(){
        return LvApplication.getInstance().getScreenWidthPixels();
    }

    public static int getScreenHeight(){
        return LvApplication.getInstance().getScreenHeightPixels();
    }

    /*是否在中心点*/
    public static boolean compareLat(LatLng ll, LatLng llr, LatLng mlatlng){
        if (ll == null || llr == null || mlatlng == null) {
            return true;
        }
        /*如果经度大于左上*/
        if(llr.latitude < mlatlng.latitude &&
                mlatlng.latitude < ll.latitude &&
                ll.longitude < mlatlng.longitude &&
                mlatlng.longitude < llr.longitude){
            return true;
        }
        return false;
    }

    public static LatLng getLeftCorner(BaiduMap mBaiduMap) {
        /*百度地图转换的的左上角经纬度*/
        Point pt = new Point();
        pt.x = 0;
        pt.y = 0;
        if (mBaiduMap.getProjection() == null) {
            return null;
        }
        return mBaiduMap.getProjection().fromScreenLocation(pt);
    }

    public static LatLng getRightCorner(BaiduMap mBaiduMap) {
        /*百度地图转换的的右下角经纬度*/
        Point pt = new Point();
        pt.x = getScreenWidth();
        pt.y = getScreenHeight();
        if (mBaiduMap.getProjection() == null) {
            return null;
        }
        return mBaiduMap.getProjection().fromScreenLocation(pt);
    }
}
