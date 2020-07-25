package com.moft.xfapply.model.external.dto;

import java.util.Date;

/**
 * 终端授权表(APP_DEVICE)
 * 
 * @author wangxy
 * @version 1.0.0 2017-04-14
 */
public class DeviceAuthInfoDTO extends RestDTO implements java.io.Serializable {
    
    private static final long serialVersionUID = -3896074405886159171L;
    
    private Integer id;             // 主键
    private String  uuid;           // 唯一标识
    private String  orgUuid;        // 关联组织
    private String  orgCode;        // 组织编号
    private String  orgName;        // 组织名称
    private String  identity;       // 设备标识
    private String  simNo;          // SIM卡号
    private String  model;          // 设备型号
    private String  appIdentity;    // 应用标识
    private Boolean hasAuthorized;  // 是否授权
    private Date    authorizedDate; // 授权时间
    private Date    expiredDate;    // 失效时间
    private Date    createDate;     // 创建时间
    private String  districtCode;   // 标识设备所属的区域
    private String  remark;         // 备注
    
    /*private AppOrganizationDTO appOrganizationDTO;*/
    
    public String getUuid() {
        return uuid;
    }
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    public String getOrgUuid() {
        return orgUuid;
    }
    
    public void setOrgUuid(String orgUuid) {
        this.orgUuid = orgUuid;
    }
    
    public String getOrgCode() {
        return orgCode;
    }
    
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    
    public String getOrgName() {
        return orgName;
    }
    
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    
    public String getIdentity() {
        return identity;
    }
    
    public void setIdentity(String identity) {
        this.identity = identity;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public String getAppIdentity() {
        return appIdentity;
    }
    
    public void setAppIdentity(String appIdentity) {
        this.appIdentity = appIdentity;
    }
    
    public Date getAuthorizedDate() {
        return authorizedDate;
    }
    
    public void setAuthorizedDate(Date authorizedDate) {
        this.authorizedDate = authorizedDate;
    }
    
    public Boolean getHasAuthorized() {
        return hasAuthorized;
    }
    
    public void setHasAuthorized(Boolean hasAuthorized) {
        this.hasAuthorized = hasAuthorized;
    }
    
    public Date getExpiredDate() {
        return expiredDate;
    }
    
    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }
    
    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getSimNo() {
        return simNo;
    }
    
    public void setSimNo(String simNo) {
        this.simNo = simNo;
    }
    
    public String getDistrictCode() {
        return districtCode;
    }
    
    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }
    
/*    public AppOrganizationDTO getAppOrganizationDTO() {
        return appOrganizationDTO;
    }
    
    public void setAppOrganizationDTO(AppOrganizationDTO appOrganizationDTO) {
        this.appOrganizationDTO = appOrganizationDTO;
    }*/
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
}