package com.moft.xfapply.model.external.dto;

/**
 * fire_facility_device
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class FireFacilityDeviceDTO extends FireFacilityBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 8051304093930470531L;

    /** 描述
 */
    private String description;

    /** 位置
 */
    private String location;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}