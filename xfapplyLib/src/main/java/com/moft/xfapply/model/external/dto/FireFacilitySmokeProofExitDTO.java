package com.moft.xfapply.model.external.dto;

/**
 * fire_facility_smoke_proof_exit
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class FireFacilitySmokeProofExitDTO extends FireFacilityBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 5981591950161687204L;

    /** 位置
 */
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}