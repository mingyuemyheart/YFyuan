package com.moft.xfapply.model.external.dto;

/**
 * fire_facility_water_pump_adapter
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class FireFacilityWaterPumpAdapterDTO extends FireFacilityBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -4941197687134253775L;

    /** 所属建（构）筑
 */
    private String structureName;

    /** 位置
 */
    private String location;

    /** 型号
 */
    private String modelNumber;

    /** 安装形式
 */
    private String installation;

    /** 进水口尺寸（mm）
 */
    private Double intakeSize;

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

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getInstallation() {
        return installation;
    }

    public void setInstallation(String installation) {
        this.installation = installation;
    }

    public Double getIntakeSize() {
        return intakeSize;
    }

    public void setIntakeSize(Double intakeSize) {
        this.intakeSize = intakeSize;
    }
}