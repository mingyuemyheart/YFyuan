package com.moft.xfapply.model.external.dto;

/**
 * fire_facility_steam_semifixed_system
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class FireFacilitySteamSemifixedSystemDTO extends FireFacilityBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -4641629231674270830L;

    /** 所属建（构）筑
 */
    private String structureName;

    /** 位置
 */
    private String location;

    /** 额定压力（MPa）
 */
    private Double specifiedPressure;

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

    public Double getSpecifiedPressure() {
        return specifiedPressure;
    }

    public void setSpecifiedPressure(Double specifiedPressure) {
        this.specifiedPressure = specifiedPressure;
    }
}