package com.moft.xfapply.model.common;

import com.moft.xfapply.model.entity.MapSearchInfo;

import java.io.Serializable;

/**
 * Created by sxf on 2019-04-23.
 */

public class DisasterInfo implements Serializable {
    private String uuid;
    private String name;
    private String address;
    private double lat;
    private double lng;
    private boolean isImportant = false;
    //GeoElement用
    private String type;
    private String key;
    private String city;
    private MapSearchInfo msi = null;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public MapSearchInfo getMsi() {
        return msi;
    }

    public void setMsi(MapSearchInfo msi) {
        this.msi = msi;
    }
}
