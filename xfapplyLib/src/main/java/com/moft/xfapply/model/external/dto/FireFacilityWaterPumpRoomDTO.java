package com.moft.xfapply.model.external.dto;
/**
 * fire_facility_water_pump_room
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class FireFacilityWaterPumpRoomDTO extends FireFacilityBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -2175557873182737147L;

    /** 所属建（构）筑
 */
    private String structureName;

    /** 位置
 */
    private String location;

    /** 消火栓泵数量
 */
    private Integer hydrantPumpCount;

    /** 消火栓泵最大流量（L/s）
 */
    private Double hydrantPumpMaximumFlow;

    /** 喷淋泵数量 */
    private Integer sprayPumpCount;

    /** 喷淋泵最大流量（L/s）
 */
    private Double sprayPumpMaximumFlow;

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

    public Integer getHydrantPumpCount() {
        return hydrantPumpCount;
    }

    public void setHydrantPumpCount(Integer hydrantPumpCount) {
        this.hydrantPumpCount = hydrantPumpCount;
    }

    public Double getHydrantPumpMaximumFlow() {
        return hydrantPumpMaximumFlow;
    }

    public void setHydrantPumpMaximumFlow(Double hydrantPumpMaximumFlow) {
        this.hydrantPumpMaximumFlow = hydrantPumpMaximumFlow;
    }

    public Integer getSprayPumpCount() {
        return sprayPumpCount;
    }

    public void setSprayPumpCount(Integer sprayPumpCount) {
        this.sprayPumpCount = sprayPumpCount;
    }

    public Double getSprayPumpMaximumFlow() {
        return sprayPumpMaximumFlow;
    }

    public void setSprayPumpMaximumFlow(Double sprayPumpMaximumFlow) {
        this.sprayPumpMaximumFlow = sprayPumpMaximumFlow;
    }
}