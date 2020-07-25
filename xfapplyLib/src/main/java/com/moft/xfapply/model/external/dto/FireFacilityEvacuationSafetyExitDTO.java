package com.moft.xfapply.model.external.dto;

/**
 * fire_facility_evacuation_safety_exit
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class FireFacilityEvacuationSafetyExitDTO extends FireFacilityBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -864490161866109407L;

    /** 所属建（构）筑
 */
    private String structureName;

    /** 位置
 */
    private String location;
    private Boolean firstFloor; //用于标记是否是首层

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

    public Boolean getFirstFloor() {
        return firstFloor;
    }

    public void setFirstFloor(Boolean firstFloor) {
        this.firstFloor = firstFloor;
    }
}