package com.moft.xfapply.model.external.dto;


import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.base.IMediaDTO;
import com.moft.xfapply.model.external.base.ISourceDataDTO;
import com.moft.xfapply.utils.StringUtil;

import java.util.List;

public class WatersourceCraneDTO extends WatersourceBaseDTO implements ISourceDataDTO {

    private static final long serialVersionUID = 6198543891664796607L;

    private Double craneHeight;//水鹤高度(m)
    private Double networkDiameter;//管网直径(mm)
    private Double networkPressure;//管网压力(Mpa)
    private Double flow;//流量(L/s)

    public Double getCraneHeight() {
        return craneHeight;
    }

    public void setCraneHeight(Double craneHeight) {
        this.craneHeight = craneHeight;
    }

    public Double getNetworkDiameter() {
        return networkDiameter;
    }

    public void setNetworkDiameter(Double networkDiameter) {
        this.networkDiameter = networkDiameter;
    }

    public Double getNetworkPressure() {
        return networkPressure;
    }

    public void setNetworkPressure(Double networkPressure) {
        this.networkPressure = networkPressure;
    }

    public Double getFlow() {
        return flow;
    }

    public void setFlow(Double flow) {
        this.flow = flow;
    }

    @Override
    public String getEleType() {
        if(StringUtil.isEmpty(eleType)) {
            eleType = AppDefs.CompEleType.WATERSOURCE_CRANE.toString();
        }
        return eleType;
    }
}
