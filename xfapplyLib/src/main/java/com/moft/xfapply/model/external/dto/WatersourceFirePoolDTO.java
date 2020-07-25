package com.moft.xfapply.model.external.dto;


import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.base.ISourceDataDTO;
import com.moft.xfapply.utils.StringUtil;

public class WatersourceFirePoolDTO extends WatersourceBaseDTO implements ISourceDataDTO {

    private static final long serialVersionUID = -1660956069427306555L;

    private Double waterStorage;//储水量（t）
    private Double intakeFlow;//取水最大流量(L/S)
    private Double inletFlow;//进水流量
    private Integer vehiclesNum;//同时取水车辆数(辆)
    private Double heightDiff;//取水口与水面高差（m）
    private Integer replenishTime;//补水时间（h）
    private String networkForm;//进水管网形式

    public Double getWaterStorage() {
        return waterStorage;
    }

    public void setWaterStorage(Double waterStorage) {
        this.waterStorage = waterStorage;
    }

    public Double getIntakeFlow() {
        return intakeFlow;
    }

    public void setIntakeFlow(Double intakeFlow) {
        this.intakeFlow = intakeFlow;
    }

    public Double getInletFlow() {
        return inletFlow;
    }

    public void setInletFlow(Double inletFlow) {
        this.inletFlow = inletFlow;
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

    public Integer getReplenishTime() {
        return replenishTime;
    }

    public void setReplenishTime(Integer replenishTime) {
        this.replenishTime = replenishTime;
    }

    public String getNetworkForm() {
        return networkForm;
    }

    public void setNetworkForm(String networkForm) {
        this.networkForm = networkForm;
    }

    @Override
    public String getEleType() {
        if(StringUtil.isEmpty(eleType)) {
            eleType = AppDefs.CompEleType.WATERSOURCE_FIRE_POOL.toString();
        }
        return eleType;
    }
}
