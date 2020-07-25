package com.moft.xfapply.model.external.dto;

import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.base.ISourceDataDTO;
import com.moft.xfapply.utils.StringUtil;

public class WatersourceNatureIntakeDTO extends WatersourceBaseDTO implements ISourceDataDTO {

    private static final long serialVersionUID = -3582514706140544816L;

    private String natureType;        //天然水源类型
    private Boolean hasWharf;        //有无消防码头
    private Integer vehiclesNum;        //同时取水车辆数(辆)
    private Double heightDiff;        //取水口与水面高差（m）
    private Boolean hasDry;        //有无枯水期
    private String drySeason;        //枯水期
    private String waterQuality;        //水质

    public String getNatureType() {
        return natureType;
    }

    public void setNatureType(String natureType) {
        this.natureType = natureType;
    }

    public Boolean getHasWharf() {
        return hasWharf;
    }

    public void setHasWharf(Boolean hasWharf) {
        this.hasWharf = hasWharf;
    }

    public Integer getVehiclesNum() {
        return vehiclesNum;
    }

    public void setVehiclesNum(Integer vehiclesNum) {
        this.vehiclesNum = vehiclesNum;
    }

    public Double getHeightDiff() {
        return heightDiff;
    }

    public void setHeightDiff(Double heightDiff) {
        this.heightDiff = heightDiff;
    }

    public Boolean getHasDry() {
        return hasDry;
    }

    public void setHasDry(Boolean hasDry) {
        this.hasDry = hasDry;
    }

    public String getDrySeason() {
        return drySeason;
    }

    public void setDrySeason(String drySeason) {
        this.drySeason = drySeason;
    }

    public String getWaterQuality() {
        return waterQuality;
    }

    public void setWaterQuality(String waterQuality) {
        this.waterQuality = waterQuality;
    }

    @Override
    public String getEleType() {
        if(StringUtil.isEmpty(eleType)) {
            eleType = AppDefs.CompEleType.WATERSOURCE_NATURE_INTAKE.toString();
        }
        return eleType;
    }
}
