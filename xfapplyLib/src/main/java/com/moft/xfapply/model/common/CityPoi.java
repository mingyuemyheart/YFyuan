package com.moft.xfapply.model.common;

import com.baidu.mapapi.search.core.PoiInfo;

import java.util.List;

/**
 * Created by sxf on 2019-04-22.
 */

public class CityPoi {
    private String cityInfo;
    private int totalCnt;
    private List<PoiInfo> poiList;

    public String getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(String cityInfo) {
        this.cityInfo = cityInfo;
    }

    public int getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(int totalCnt) {
        this.totalCnt = totalCnt;
    }

    public List<PoiInfo> getPoiList() {
        return poiList;
    }

    public void setPoiList(List<PoiInfo> poiList) {
        this.poiList = poiList;
    }
}
