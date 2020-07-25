package com.moft.xfapply.model.external.dto;

/**
 * fire_facility_foam_hydrant
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class FireFacilityFoamHydrantDTO extends FireFacilityBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -2362384981124791755L;


    /** 所属建（构）筑
 */
    private String structureName;

    /** 位置
 */
    private String location;

    /** 是否可用
 */
    private Boolean usable;

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

    public Boolean getUsable() {
        return usable;
    }

    public void setUsable(Boolean usable) {
        this.usable = usable;
    }
}