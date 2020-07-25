package com.moft.xfapply.logic.location;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.model.common.Coordinate;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.utils.CoordinateUtil;
import com.moft.xfapply.widget.dialog.CustomAlertDialog;
import com.moft.xfapply.widget.dialog.CustomAlertDialog.OnDoingListener;
import com.moft.xfapply.widget.dialog.ListDialog;
import com.moft.xfapply.widget.dialog.ListDialog.OnSelectedListener;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

public class NavService {
    private static NavService instance = null;
    
    private static Context mContext = null;

    public NavService() {
    }

    public static NavService getInstance(Context c) {
        mContext = c;
        if (instance == null) {
            instance = new NavService();
        }

        return instance;
    }

    public List<Dictionary> getNavList() {
        List<Dictionary> names = new ArrayList<Dictionary>();
        if (isAvilible(mContext, "com.baidu.BaiduMap")) {
            names.add(new Dictionary("百度地图导航", "1"));
        }

        if (isAvilible(mContext, "com.autonavi.minimap")) {
            names.add(new Dictionary("高德地图导航", "2"));
        }

        if (isAvilible(mContext, "com.google.android.apps.maps")) {
            names.add(new Dictionary("谷歌地图导航", "3"));
        }

        if (isAvilible(mContext, "com.tencent.map")) {
            names.add(new Dictionary("腾讯地图导航", "4"));
        }
        return names;
    }
    
    public void doNav(final double lat, final double lng, final String endName) {
        final List<Dictionary> names = getNavList();
        
        String content = "";
        if (names.size() == 0) {
            content = "您尚未安装地图客户端或所安装的客户端版本过低。";
            
            CustomAlertDialog.show(mContext, content, new OnDoingListener() {
                @Override
                public void onOK() {  
                }

                @Override
                public void onNG() {
                }
                
            });
        } else {  
            if (names.size() > 1) {          
                ListDialog.show(mContext, names, -1, new OnSelectedListener() {
                    @Override
                    public void onSelected(int position) {
                        doStartNavi(names.get(position).getCode(), lat, lng, endName);
                    }
                });
            } else {
                doStartNavi(names.get(0).getCode(), lat, lng, endName);
            }
        }
    }
    
    public void doStartNavi(String type, double lat, double lng, String endName) {
        if ("1".equals(type)) {
            startNavigationBd(lat, lng, endName);
        } else if ("2".equals(type)) {
            startNavigationGd(lat, lng, endName);
        } else if ("3".equals(type)) {
            startNavigationGG(lat, lng, endName);
        } else if ("4".equals(type)) {
            startNavigationTX(lat, lng, endName);
        } else {
            
        }
    }
    
    private void startNavigationGd(double lat, double lng, String endName)
    {
        try
        {
            Coordinate bdCoordinate = new Coordinate(lng, lat);
            Coordinate wgs84 = CoordinateUtil.getInstance().BD09_Convert_WGS84(bdCoordinate);
            if(wgs84 != null && wgs84.getLat() != null && wgs84.getLon() != null) {
                String uri = "androidamap://navi?sourceApplication=LandVerify&poiname="
                        + endName +
                        "&lat=" + wgs84.getLat() +
                        "&lon=" + wgs84.getLon() +
                        "&dev=1";
                Intent intent = Intent.getIntent(uri);
                if (isAvilible(mContext, "com.autonavi.minimap")) {
                    mContext.startActivity(intent); //启动调用
                }
            }
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
    }

    
    private void startNavigationTX(double lat, double lng, String endName)
    {
        try
        {
            Coordinate bdCoordinate = new Coordinate(lng, lat);
            Coordinate gcj02 = CoordinateUtil.getInstance().BD09_Convert_GCJ02(bdCoordinate);
            if(gcj02 != null && gcj02.getLat() != null && gcj02.getLon() != null) {
                String uri = "qqmap://map/routeplan?type=drive&from=当前位置&fromcoord=CurrentLocation&"
                        + "to="
                        + endName + "&tocoord="
                        + gcj02.getLat() + ","
                        + gcj02.getLon() + "&coord_type=1";
                Intent intent = Intent.getIntent(uri);
                if (isAvilible(mContext, "com.tencent.map")) {
                    mContext.startActivity(intent); //启动调用
                }
            }
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
    }
    
    private void startNavigationGG(double lat, double lng, String endName)
    {
        Coordinate bdCoordinate = new Coordinate(lng, lat);
        Coordinate gcj02 = CoordinateUtil.getInstance().BD09_Convert_GCJ02(bdCoordinate);
        if(gcj02 != null && gcj02.getLat() != null && gcj02.getLon() != null) {
            Uri gmmIntentUri = Uri.parse("google.navigation:q="
                    + gcj02.getLat() + ","
                    + gcj02.getLon());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            if (isAvilible(mContext, "com.google.android.apps.maps")) {
                mContext.startActivity(mapIntent); //启动调用
            }
        }
    }
    
    private void startNavigationBd(double lat, double lng, String endName)
    {
        try
        {
            Coordinate bdCoordinate = new Coordinate(lng, lat);
            Coordinate wgs84 = CoordinateUtil.getInstance().BD09_Convert_WGS84(bdCoordinate);
            if(wgs84 != null && wgs84.getLat() != null && wgs84.getLon() != null) {
                String uri = "intent://map/direction?"
//                        + "origin=latlng:"
//                        + LvApplication.getInstance().getCurLat()
//                        + ","
//                        + LvApplication.getInstance().getCurLng()
//                        + "|name:当前位置"
                        + "destination=name:"
                        + endName
                        + "|latlng:"
                        + wgs84.getLat()
                        + ","
                        + wgs84.getLon()
                        + "&mode=driving"
                        + "&coord_type=wgs84"
                        + "&referer=HopeGis|LandVerify#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end";
                Intent intent = Intent.getIntent(uri);
                if (isAvilible(mContext, "com.baidu.BaiduMap")) {
                    mContext.startActivity(intent); //启动调用
                }
            }
        }
        catch (URISyntaxException e)
        {
            e.printStackTrace();
        }
    }
    
    private boolean isAvilible(Context context, String packageName){ 
        final PackageManager packageManager = context.getPackageManager(); 
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);  
        List<String> packageNames = new ArrayList<String>();  
        if(packageInfos != null){   
            for(int i = 0; i < packageInfos.size(); i++){
                String packName = packageInfos.get(i).packageName;   
                packageNames.add(packName);   
            }   
        }
        return packageNames.contains(packageName);  
  }   


}
