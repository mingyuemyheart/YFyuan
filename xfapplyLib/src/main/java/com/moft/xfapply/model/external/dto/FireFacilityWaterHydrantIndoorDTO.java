package com.moft.xfapply.model.external.dto;

/**
 * fire_facility_water_hydrant_indoor
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class FireFacilityWaterHydrantIndoorDTO extends FireFacilityBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -6822574037654326999L;

    /** 所属建（构）筑
 */
    private String structureName;

    /** 位置
 */
    private String location;

    /** 是否可用
 */
    private Boolean usable;

    /** 压力型号
     */
    private String pressureModel;

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

    public String getPressureModel() {
        return pressureModel;
    }

    public void setPressureModel(String pressureModel) {
        this.pressureModel = pressureModel;
    }
}