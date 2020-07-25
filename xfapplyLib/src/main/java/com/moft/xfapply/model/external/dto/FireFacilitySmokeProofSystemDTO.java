package com.moft.xfapply.model.external.dto;

/**
 * fire_facility_smoke_proof_system
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class FireFacilitySmokeProofSystemDTO extends FireFacilityBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 2714249233604065394L;

    /** 启闭位置
 */
    private String onoffLocation;

    /** 是否可用
 */
    private Boolean usable;

    public Boolean getUsable() {
        return usable;
    }

    public void setUsable(Boolean usable) {
        this.usable = usable;
    }

    public String getOnoffLocation() {
        return onoffLocation;
    }

    public void setOnoffLocation(String onoffLocation) {
        this.onoffLocation = onoffLocation;
    }
}