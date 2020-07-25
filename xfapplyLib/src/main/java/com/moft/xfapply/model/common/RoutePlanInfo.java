package com.moft.xfapply.model.common;

import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.moft.xfapply.model.entity.GeomElementDBInfo;
import com.moft.xfapply.utils.CoordinateUtil;
import com.moft.xfapply.utils.StringUtil;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by sxf on 2019-04-26.
 */

public class RoutePlanInfo implements Serializable
{
    private GeomElementDBInfo geoElement = null;
    private int lightNum = -1;
    private int congestionDistance = -1;
    private int distance = -1;
    private int duration = -1;
    private List<DrivingRouteLine> routeLines = null;
    private boolean isDurationPriority = true;
    private DrivingRouteLine curRouteLine = null;

    public void iniRouteInfo() {
        lightNum = -1;
        congestionDistance = -1;
        distance = -1;
        duration = -1;
        routeLines = null;
        isDurationPriority = true;
    }

    public GeomElementDBInfo getGeoElement() {
        return geoElement;
    }

    public void setGeoElement(GeomElementDBInfo geoElement) {
        this.geoElement = geoElement;
    }

    public int getLightNum() {
        return lightNum;
    }

    public void setLightNum(int lightNum) {
        this.lightNum = lightNum;
    }

    public int getCongestionDistance() {
        return congestionDistance;
    }

    public void setCongestionDistance(int congestionDistance) {
        this.congestionDistance = congestionDistance;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<DrivingRouteLine> getRouteLines() {
        return routeLines;
    }

    public void setRouteLines(List<DrivingRouteLine> routeLines) {
        this.routeLines = routeLines;
    }

    public boolean isDurationPriority() {
        return isDurationPriority;
    }

    public int getDurationForSort() {
        if (-1 == duration) {
            return 268435455;
        }
        return duration;
    }

    public int getDistanceForSort() {
        if (-1 == distance) {
            return 268435455;
        }
        return distance;
    }

    public void setDurationPriority(boolean durationPriority) {
        isDurationPriority = durationPriority;
        if (routeLines == null) {
            return;
        }
        int minValue = -1;
        if (isDurationPriority) {
            for (DrivingRouteLine routeLine : routeLines) {
                if (minValue == -1 || routeLine.getDuration() < minValue) {
                    minValue = routeLine.getDuration();
                    lightNum = routeLine.getLightNum();
                    congestionDistance = routeLine.getCongestionDistance();
                    distance = routeLine.getDistance();
                    duration = routeLine.getDuration();
                    curRouteLine = routeLine;
                }
            }
        } else {
            for (DrivingRouteLine routeLine : routeLines) {
                if (minValue == -1 || routeLine.getDistance() < minValue) {
                    minValue = routeLine.getDistance();
                    lightNum = routeLine.getLightNum();
                    congestionDistance = routeLine.getCongestionDistance();
                    distance = routeLine.getDistance();
                    duration = routeLine.getDuration();
                    curRouteLine = routeLine;
                }
            }
        }
    }

    public String getNameDesc() {
        String desc = "未知名称";
        if (getGeoElement() == null) {
            return desc;
        }
        if (!StringUtil.isEmpty(getGeoElement().getName())) {
            desc = getGeoElement().getName();
        } else if (!StringUtil.isEmpty(getGeoElement().getAddress())) {
            desc = getGeoElement().getAddress();
        } else {
        }
        return desc;
    }

    public String getDurationDesc() {
        return StringUtil.getDurationDesc(duration);
    }

    public String getDistanceDesc() {
        return StringUtil.getDistanceDesc(distance);
    }

    public String getLightNumDesc() {
        if (-1 == lightNum) {
            return "未知";
        }
        return lightNum + "个";
    }

    public String getCongestionDistanceDesc() {
        if (-1 == congestionDistance) {
            return "未知";
        }
        String desc = ":";
        if (congestionDistance < 1000) {
            desc += congestionDistance + "米";
        } else {
            DecimalFormat df = new DecimalFormat("#.##");
            desc += df.format(congestionDistance/1000) + "千米";
        }
        return desc;
    }

    public double getDistance(double lng, double lat) {
        double dis = -1;
        if (!StringUtil.isEmpty(getGeoElement().getLatitude()) && !StringUtil.isEmpty(getGeoElement().getLongitude())) {
            try {
                double geoLat = Double.valueOf(getGeoElement().getLatitude());
                double geoLng = Double.valueOf(getGeoElement().getLongitude());

                dis = CoordinateUtil.getInstance().getDisTwo(
                        geoLng, geoLat, lng, lat);

            } catch (NumberFormatException ex) {
                dis  = -1;
            }
        } else {
            dis  = -1;
        }
        return dis;
    }

    public DrivingRouteLine getCurRouteLine() {
        return curRouteLine;
    }
}
