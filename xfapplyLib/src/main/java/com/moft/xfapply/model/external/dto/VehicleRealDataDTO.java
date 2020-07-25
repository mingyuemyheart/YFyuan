package com.moft.xfapply.model.external.dto;

import com.moft.xfapply.app.Constant;
import com.moft.xfapply.utils.StringUtil;

import java.io.Serializable;

public class VehicleRealDataDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer vehicleId;  // 车id
    private String  plateNo;    // 车牌
    private String  vendor;     // 厂家
    private String  depName;    // 部门
    private String  operPermit; // 预案关联
    private String  updateTime; // 时间
    private String  status;     // 状态
    private String  location;   // 位置
    private Double  bLon;       // 经度
    private Double  bLat;       // 纬度
    private Double  altitude;   // 海拔
    private Double  velocity;   // 速度
    private Integer direction;  // 方向
    private Double  gas;        // 油量
    private Double  mileage;    // 里程
    private Boolean online;     // 是否在线

    public String getOutline() {
        String outline = "";

        outline += "车牌号：<font color=#0074C7>" + StringUtil.get(plateNo) + "</font>" + Constant.OUTLINE_DIVIDER;
        outline += "状态：<font color=#0074C7>" + (online ? "在线" : "不在线") + "</font>" + Constant.OUTLINE_DIVIDER;
        outline += "所属队站：<font color=#0074C7>" + StringUtil.get(depName) + "</font>" + Constant.OUTLINE_DIVIDER;

        return outline;
    }
    
    public Integer getVehicleId() {
        return vehicleId;
    }
    
    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }
    
    public String getPlateNo() {
        return plateNo;
    }
    
    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }
    
    public String getVendor() {
        return vendor;
    }
    
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
    
    public String getDepName() {
        return depName;
    }
    
    public void setDepName(String depName) {
        this.depName = depName;
    }
    
    public String getOperPermit() {
        return operPermit;
    }
    
    public void setOperPermit(String operPermit) {
        this.operPermit = operPermit;
    }
    
    public String getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public Double getbLon() {
        return bLon;
    }
    
    public void setbLon(Double bLon) {
        this.bLon = bLon;
    }
    
    public Double getbLat() {
        return bLat;
    }
    
    public void setbLat(Double bLat) {
        this.bLat = bLat;
    }
    
    public Double getAltitude() {
        return altitude;
    }
    
    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }
    
    public Double getVelocity() {
        return velocity;
    }
    
    public void setVelocity(Double velocity) {
        this.velocity = velocity;
    }
    
    public Integer getDirection() {
        return direction;
    }
    
    public void setDirection(Integer direction) {
        this.direction = direction;
    }
    
    public Double getGas() {
        return gas;
    }
    
    public void setGas(Double gas) {
        this.gas = gas;
    }
    
    public Double getMileage() {
        return mileage;
    }
    
    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }
    
    public Boolean getOnline() {
        return online;
    }
    
    public void setOnline(Boolean online) {
        this.online = online;
    }
    
}