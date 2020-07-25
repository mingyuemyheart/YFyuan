package com.moft.xfapply.logic.location;

import java.util.Iterator;

import android.content.Context;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class Gps {
    private Location location = null;
    private LocationManager locationManager = null;
    private Context context = null;
    
    private GpsStatus.Listener statusListener;
    private GpsStatus gpsStatus;
    private int satiCount = 0;

    public Gps(Context ctx) {
        context = ctx;
    }
    
    public static boolean isGpsOpen(Context ctx) {
        LocationManager lm = (LocationManager) ctx.getSystemService(
                Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(
                android.location.LocationManager.GPS_PROVIDER);
    }
    
    public void start() {
        locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        String provider = getProvider();
        location = locationManager.getLastKnownLocation(provider);
        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                2000, 10, locationListener);
        
        statusListener = new GpsStatus.Listener() 
        {
            public void onGpsStatusChanged(int event)
            {
                if (locationManager == null) {
                    return;
                }
                gpsStatus = locationManager.getGpsStatus(null);
            
                switch(event)
                {
                case GpsStatus.GPS_EVENT_FIRST_FIX:
                    break;       
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                    Iterable<GpsSatellite> allSatellites;
                    allSatellites = gpsStatus.getSatellites();
                     
                    Iterator<GpsSatellite> it = allSatellites.iterator(); 
                    satiCount = 0;
                    while(it.hasNext())   
                    {
                         satiCount++;
                         GpsSatellite gs = it.next();
                    }
                    break;       
                case GpsStatus.GPS_EVENT_STARTED:
                    break;       
                case GpsStatus.GPS_EVENT_STOPPED:
                    break;       
                default :
                    break;
                }
            }
        };
        locationManager.addGpsStatusListener(statusListener);
    }

    // 获取Location Provider
    private String getProvider() {
        // 构建位置查询条件
        Criteria criteria = new Criteria();
        // 查询精度：高
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 是否查询海拨：否
        criteria.setAltitudeRequired(false);
        // 是否查询方位角 : 否
        criteria.setBearingRequired(false);
        // 是否允许付费：是
        criteria.setCostAllowed(true);
        // 电量要求：低
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        // 返回最合适的符合条件的provider，第2个参数为true说明 , 如果只有一个provider是有效的,则返回当前provider
        return locationManager.getBestProvider(criteria, true);
    }

    private LocationListener locationListener = new LocationListener() {
        // 位置发生改变后调用
        public void onLocationChanged(Location l) {
            if (l != null) {
                location = l;
            }
        }

        // provider 被用户关闭后调用
        public void onProviderDisabled(String provider) {
        }

        // provider 被用户开启后调用
        public void onProviderEnabled(String provider) {
            Location l = locationManager.getLastKnownLocation(provider);
            if (l != null) {
                location = l;
            }
        }

        // provider 状态变化时调用
        public void onStatusChanged(String provider, int status,
                Bundle extras) {
        }
    };

    public Location getLocation() {
        return location;
    }
    
    public int getSatiCount() {
        return satiCount;
    }

    public void close() {
        if (locationManager != null) {
            if (locationListener != null) {
                locationManager.removeUpdates(locationListener);
                locationListener = null;
            }
            if (statusListener != null) {
                locationManager.removeGpsStatusListener(statusListener);
                statusListener = null;
            }
            locationManager = null;
        }
    }
}
