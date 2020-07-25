package com.moft.xfapply.model.common;

import com.moft.xfapply.utils.CoordinateUtil;
import com.moft.xfapply.utils.StringUtil;

import java.io.Serializable;
import java.text.DecimalFormat;

public class ComLocation implements Serializable {
    private String name;
    private Double lat;
    private Double lng;
    private String address;
    private String eleUuid;
    private Coordinate wgs = null;

    public boolean isInvalid() {
        return lat == null || lng == null || (lat == 0 && lng == 0);
    }

    public String getDesc() {
        String result = "未定位...";

        if (!isInvalid()) {
            result = "纬度[%s] 经度[%s]";

            DecimalFormat df = new DecimalFormat("#.000000");
            String strLat = lat != 0 ? df.format(lat): "0";
            String strLng = lng != 0 ? df.format(lng): "0";

            result = String.format(result, strLat, strLng);

            if (!StringUtil.isEmpty(address)) {
                result += "\n";
                result += address;
            }
        }

        return result;
    }

    public Double getWgs84Lat() {
        if (wgs == null) {
            Coordinate bd = new Coordinate();
            bd.setLat(lat);
            bd.setLon(lng);
            wgs = CoordinateUtil.getInstance().BD09_Convert_WGS84(bd);
        }

        return wgs.getLat();
    }

    public Double getWgs84Lng() {
        if (wgs == null) {
            Coordinate bd = new Coordinate();
            bd.setLat(lat);
            bd.setLon(lng);
            wgs = CoordinateUtil.getInstance().BD09_Convert_WGS84(bd);
        }

        return wgs.getLon();
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        wgs = null;
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        wgs = null;
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEleUuid() {
        return eleUuid;
    }

    public void setEleUuid(String eleUuid) {
        this.eleUuid = eleUuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
