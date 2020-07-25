package com.moft.xfapply.model.external.dto;

/**
 * fire_facility_foam_generator
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class FireFacilityFoamGeneratorDTO extends FireFacilityBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 5395713787532572476L;

    /** 所属建（构）筑
 */
    private String structureName;

    /** 流量（L/s）
 */
    private Double flow;

    /** 型号
 */
    private String modelNumber;

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public Double getFlow() {
        return flow;
    }

    public void setFlow(Double flow) {
        this.flow = flow;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }
}