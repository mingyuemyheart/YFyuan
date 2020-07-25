package com.moft.xfapply.model.external.dto;

/**
 * key_part_structure
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class KeyPartStructureDTO extends KeyPartBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 4740877770690428417L;

    /** 区域面积（㎡）
 */
    private Double area;

    /** 功能描述
 */
    private String functionDescription;

    /** 危险介质
 */
    private String hazardMediaJson;

    /** 危险性分析
 */
    private String riskAnalysis;

    /** 注意事项
 */
    private String attention;

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getFunctionDescription() {
        return functionDescription;
    }

    public void setFunctionDescription(String functionDescription) {
        this.functionDescription = functionDescription;
    }

    public String getHazardMediaJson() {
        return hazardMediaJson;
    }

    public void setHazardMediaJson(String hazardMediaJson) {
        this.hazardMediaJson = hazardMediaJson;
    }

    public String getRiskAnalysis() {
        return riskAnalysis;
    }

    public void setRiskAnalysis(String riskAnalysis) {
        this.riskAnalysis = riskAnalysis;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

}