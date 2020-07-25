package com.moft.xfapply.model.external.dto;

/**
 * 泡沫系统-泵房(fire_facility_foam_pump_room)
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class FireFacilityFoamPumpRoomDTO extends FireFacilityBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1273608642810272741L;

    /** 所属建（构）筑 */
    private String structureName;

    /** 位置 */
    private String location;

    /** 泡沫液类型 */
    private String foamType;

    /** 泡沫液储量（吨） */
    private Double foamStorage;

    /** 泡沫泵最大流量（L/s） */
    private Double pumpMaximumFlow;

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

    public String getFoamType() {
        return foamType;
    }

    public void setFoamType(String foamType) {
        this.foamType = foamType;
    }

    public Double getFoamStorage() {
        return foamStorage;
    }

    public void setFoamStorage(Double foamStorage) {
        this.foamStorage = foamStorage;
    }

    public Double getPumpMaximumFlow() {
        return pumpMaximumFlow;
    }

    public void setPumpMaximumFlow(Double pumpMaximumFlow) {
        this.pumpMaximumFlow = pumpMaximumFlow;
    }
}