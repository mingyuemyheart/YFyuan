package com.moft.xfapply.model.external.dto;

/**
 * fire_facility_water_cooling_system
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class FireFacilityWaterCoolingSystemDTO extends FireFacilityBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -4697918026038057394L;

    /** 所属建（构）筑
 */
    private String structureName;

    /** 位置
 */
    private String location;

    /** 供水强度（L/min.m2）
 */
    private Double supplyStrength;

    /** 有无
 */
    private Boolean installed;

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getSupplyStrength() {
        return supplyStrength;
    }

    public void setSupplyStrength(Double supplyStrength) {
        this.supplyStrength = supplyStrength;
    }

    public Boolean getInstalled() {
        return installed;
    }

    public void setInstalled(Boolean installed) {
        this.installed = installed;
    }
}