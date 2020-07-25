package com.moft.xfapply.model.external.dto;

/**
 * fire_facility_extinguishing_powder
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class FireFacilityExtinguishingPowderDTO extends FireFacilityBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 8760076872212875379L;

    /** 启闭位置
 */
    private String onoffLocation;

    /** 作用范围
 */
    private String actionScope;

    public String getOnoffLocation() {
        return onoffLocation;
    }

    public void setOnoffLocation(String onoffLocation) {
        this.onoffLocation = onoffLocation;
    }

    public String getActionScope() {
        return actionScope;
    }

    public void setActionScope(String actionScope) {
        this.actionScope = actionScope;
    }
}