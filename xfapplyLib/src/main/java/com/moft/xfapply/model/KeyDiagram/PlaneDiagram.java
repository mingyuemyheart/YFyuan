package com.moft.xfapply.model.KeyDiagram;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/11/22 0022.
 */

public class PlaneDiagram implements Serializable {
    private String planeUrl;
    private String attachUuid;
    private String geoType;
    private String geoDesc;
    private String remark;

    public String getPlaneUrl() {
        return planeUrl;
    }

    public void setPlaneUrl(String planeUrl) {
        this.planeUrl = planeUrl;
    }

    public String getAttachUuid() {
        return attachUuid;
    }

    public void setAttachUuid(String attachUuid) {
        this.attachUuid = attachUuid;
    }

    public String getGeoType() {
        return geoType;
    }

    public void setGeoType(String geoType) {
        this.geoType = geoType;
    }

    public String getGeoDesc() {
        return geoDesc;
    }

    public void setGeoDesc(String geoDesc) {
        this.geoDesc = geoDesc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
