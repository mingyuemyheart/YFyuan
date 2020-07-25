package com.moft.xfapply.model.external.dto;

/**
 * fire_facility_auto_alarm_system
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class FireFacilityAutoSprinklerSystemDTO extends FireFacilityBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 4244237635934145430L;

    /** 位置
 */
    private String location;

    /** 描述
 */
    private String description;


    /** 是否可用
     */
    private Boolean usable;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getUsable() {
        return usable;
    }

    public void setUsable(Boolean usable) {
        this.usable = usable;
    }
}