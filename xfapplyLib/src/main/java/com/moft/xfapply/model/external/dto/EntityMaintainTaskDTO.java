package com.moft.xfapply.model.external.dto;

import java.util.Date;

/**
 * 数据审核(ACTIVITY_COMP_VERIFY)
 * 
 * @author wangxy
 * @version 1.0.0 2017-04-14
 */
public class EntityMaintainTaskDTO extends RestDTO implements java.io.Serializable {
    
    private static final long serialVersionUID = 2915948751655254789L;

    private String  uuid; //主键
    private String  departmentUuid;      // 所属机构
    private String  maintainUuid;      // 要素标识
    private String  entityUuid;      // 要素标识
    private String  entityType;      // 类别
    private String  operateType;   // 操作
    private String  state;
    private String  maintainDescription;
    private String  submitWay;
    private Date    submitTime;   // 提交时间
    private String    submitName;   // 提交人
    private String submitNote;
    private String  verifyName;   // 审核人
    private Date    verifyTime;   // 审核时间
    private String  verifyNote;   // 审核意见
    private Date createDate;
    private Date updateDate;
    private String shortTime;
    private Boolean deleted;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDepartmentUuid() {
        return departmentUuid;
    }

    public void setDepartmentUuid(String departmentUuid) {
        this.departmentUuid = departmentUuid;
    }

    public String getMaintainUuid() {
        return maintainUuid;
    }

    public void setMaintainUuid(String maintainUuid) {
        this.maintainUuid = maintainUuid;
    }

    public String getEntityUuid() {
        return entityUuid;
    }

    public void setEntityUuid(String entityUuid) {
        this.entityUuid = entityUuid;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMaintainDescription() {
        return maintainDescription;
    }

    public void setMaintainDescription(String maintainDescription) {
        this.maintainDescription = maintainDescription;
    }

    public String getSubmitWay() {
        return submitWay;
    }

    public void setSubmitWay(String submitWay) {
        this.submitWay = submitWay;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getSubmitName() {
        return submitName;
    }

    public void setSubmitName(String submitName) {
        this.submitName = submitName;
    }

    public String getSubmitNote() {
        return submitNote;
    }

    public void setSubmitNote(String submitNote) {
        this.submitNote = submitNote;
    }

    public String getVerifyName() {
        return verifyName;
    }

    public void setVerifyName(String verifyName) {
        this.verifyName = verifyName;
    }

    public Date getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(Date verifyTime) {
        this.verifyTime = verifyTime;
    }

    public String getVerifyNote() {
        return verifyNote;
    }

    public void setVerifyNote(String verifyNote) {
        this.verifyNote = verifyNote;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getShortTime() {
        return shortTime;
    }

    public void setShortTime(String shortTime) {
        this.shortTime = shortTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}