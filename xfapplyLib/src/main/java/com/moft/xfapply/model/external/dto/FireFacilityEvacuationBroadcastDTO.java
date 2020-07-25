package com.moft.xfapply.model.external.dto;

/**
 * fire_facility_evacuation_broadcast
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class FireFacilityEvacuationBroadcastDTO extends FireFacilityBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1237438796364435297L;

    /** 有无
 */
    private Boolean installed;

    /** 位置
 */
    private String location;

    public Boolean getInstalled() {
        return installed;
    }

    public void setInstalled(Boolean installed) {
        this.installed = installed;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}