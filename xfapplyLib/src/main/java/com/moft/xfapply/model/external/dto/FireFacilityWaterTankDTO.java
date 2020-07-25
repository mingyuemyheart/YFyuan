package com.moft.xfapply.model.external.dto;

/**
 * fire_facility_water_tank
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class FireFacilityWaterTankDTO extends FireFacilityBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -6949127808368304219L;

    /** 所属建（构）筑
 */
    private String structureName;

    /** 位置
 */
    private String location;

    /** 容量（m³）
 */
    private Double capacity;

    /** 补给速度（L/s）
 */
    private Double supplySpeed;

    /** 补给方式
     */
    private String supplyWay;

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

    public Double getCapacity() {
        return capacity;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public Double getSupplySpeed() {
        return supplySpeed;
    }

    public void setSupplySpeed(Double supplySpeed) {
        this.supplySpeed = supplySpeed;
    }

    public String getSupplyWay() {
        return supplyWay;
    }

    public void setSupplyWay(String supplyWay) {
        this.supplyWay = supplyWay;
    }
}