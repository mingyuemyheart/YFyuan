package com.moft.xfapply.model.common;

import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.Utils;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/4/22 0022.
 */

public class OrgDataCountSummary implements Serializable {
    private String uuid;
    private String orgName;
    private String type;
    private String address;
    private String longitude; // 百度精度
    private String latitude;  // 百度纬度
    private int count;

    public boolean isGeoPosValid() {
        if (StringUtil.isEmpty(getLongitude()) || StringUtil.isEmpty(getLatitude())) {
            return false;
        }

        Double latD = Utils.convertToDouble(getLatitude());
        Double lngD = Utils.convertToDouble(getLongitude());
        if (latD == null || lngD == null) {
            return false;
        }

        if (latD.intValue() == 0 || lngD.intValue() == 0) {
            return false;
        }

        return true;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
