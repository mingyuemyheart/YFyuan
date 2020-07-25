package com.moft.xfapply.model.external.dto;

/**
 * key_part_storage
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class KeyPartStorageDTO extends KeyPartBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -5090859428515182982L;

    /** 危险性分析
 */
    private String riskAnalysis;

    /** 注意事项
 */
    private String attention;

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