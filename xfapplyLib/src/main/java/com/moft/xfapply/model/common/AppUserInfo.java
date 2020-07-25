package com.moft.xfapply.model.common;

import java.util.Date;

public class AppUserInfo {
    private String uuid;
    private String name;
    private String address;
    private String companyUuid;
    private String mobile;
    private String email;
    private String title;
    private String userName;
    private String password;
    private Date createDate;
    private Date updateDate;
    private String remark;
    
    private String devType;
    private String devId;
    private Boolean devStatus;

    private String companyName;
    private String provinceCode;
    private String registryCode;
    
    private Date lastReportPositionTime;
    private String lastPositionDesc;
    private Double lastGpsLon;
    private Double lastGpsLat;
    private String lastDistrict;

    public AppUserInfo() {
    }

    public AppUserInfo(String uuid, String name, String userName, String password) {
        this.uuid = uuid;
        this.name = name;
        this.userName = userName;
        this.password = password;
    }

    public AppUserInfo(String uuid, String name, String address, String mobile, String email,
                       String title, String userName, String password, Date createDate, Date updateDate, String remark) {
        this.uuid = uuid;
        this.name = name;
        this.address = address;
        this.mobile = mobile;
        this.email = email;
        this.title = title;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.remark = remark;
    }

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCompanyUuid() {
        return companyUuid;
    }

    public void setCompanyUuid(String companyUuid) {
        this.companyUuid = companyUuid;
    }

    public String getRegistryCode() {
        return registryCode;
    }

    public void setRegistryCode(String registryCode) {
        this.registryCode = registryCode;
    }

    public Date getLastReportPositionTime() {
        return lastReportPositionTime;
    }

    public void setLastReportPositionTime(Date lastReportPositionTime) {
        this.lastReportPositionTime = lastReportPositionTime;
    }

    public String getLastPositionDesc() {
        return lastPositionDesc;
    }

    public void setLastPositionDesc(String lastPositionDesc) {
        this.lastPositionDesc = lastPositionDesc;
    }

    public Double getLastGpsLon() {
        return lastGpsLon;
    }

    public void setLastGpsLon(Double lastGpsLon) {
        this.lastGpsLon = lastGpsLon;
    }

    public Double getLastGpsLat() {
        return lastGpsLat;
    }

    public void setLastGpsLat(Double lastGpsLat) {
        this.lastGpsLat = lastGpsLat;
    }

    public String getLastDistrict() {
        return lastDistrict;
    }

    public void setLastDistrict(String lastDistrict) {
        this.lastDistrict = lastDistrict;
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public Boolean getDevStatus() {
        return devStatus;
    }

    public void setDevStatus(Boolean devStatus) {
        this.devStatus = devStatus;
    }

}
