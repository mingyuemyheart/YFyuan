package com.moft.xfapply.model.external.dto;

/**
 * fire_facility_foam_semifixed_device
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class FireFacilityFoamSemifixedDeviceDTO extends FireFacilityBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 2838704565873453981L;

    /** 所属建（构）筑
 */
    private String structureName;

    /** 位置
 */
    private String location;

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
}