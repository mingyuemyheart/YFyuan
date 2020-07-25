package com.moft.xfapply.model.external.dto;


import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.base.ISourceDataDTO;
import com.moft.xfapply.utils.StringUtil;

public class WatersourceFireHydrantDTO extends WatersourceBaseDTO implements ISourceDataDTO {

    private static final long serialVersionUID = 6951228844131224875L;

    private String placeForm;//放置形式
    private String networkForm;//管网形式
    private Double networkDiameter;//管网直径(mm)
    private String networkPressureType;//管网压力类型
    private Double networkPressure;//管网压力(Mpa)
    private String interfaceForm;//接口形式
    private Double interfaceCaliber;//接口口径(mm)
    private Double flow;//最大流量（L/s）

    public String getPlaceForm() {
        return placeForm;
    }

    public void setPlaceForm(String placeForm) {
        this.placeForm = placeForm;
    }

    public String getNetworkForm() {
        return networkForm;
    }

    public void setNetworkForm(String networkForm) {
        this.networkForm = networkForm;
    }

    public Double getNetworkDiameter() {
        return networkDiameter;
    }

    public void setNetworkDiameter(Double networkDiameter) {
        this.networkDiameter = networkDiameter;
    }

    public String getNetworkPressureType() {
        return networkPressureType;
    }

    public void setNetworkPressureType(String networkPressureType) {
        this.networkPressureType = networkPressureType;
    }

    public Double getNetworkPressure() {
        return networkPressure;
    }

    public void setNetworkPressure(Double networkPressure) {
        this.networkPressure = networkPressure;
    }

    public String getInterfaceForm() {
        return interfaceForm;
    }

    public void setInterfaceForm(String interfaceForm) {
        this.interfaceForm = interfaceForm;
    }

    public Double getInterfaceCaliber() {
        return interfaceCaliber;
    }

    public void setInterfaceCaliber(Double interfaceCaliber) {
        this.interfaceCaliber = interfaceCaliber;
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
            eleType = AppDefs.CompEleType.WATERSOURCE_FIRE_HYDRANT.toString();
        }
        return eleType;
    }
}
