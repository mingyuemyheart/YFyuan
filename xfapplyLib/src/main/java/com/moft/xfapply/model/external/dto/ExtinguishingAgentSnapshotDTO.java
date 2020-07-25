package com.moft.xfapply.model.external.dto;

import com.moft.xfapply.model.external.base.ISourceDataSnapshotDTO;

import java.util.Date;

/**
 * material_fire_vehicle_snapshot
 * 
 * @author wangx
 * @version 1.0.0 2018-05-22
 */
public class ExtinguishingAgentSnapshotDTO extends RestDTO implements ISourceDataSnapshotDTO {
    /** 版本号 */
    private static final long serialVersionUID = 8321730242445447064L;

    private String uuid;                     //唯一标识

    /** 源uuid */
    private String entityUuid;

    /** 是否入库 */
    private Boolean used = false;

    /** 操作类型 */
    private String operateType;

    /** 实体数据，json格式 */
    private String entityData;

    /** 存放副本，json格式 */
    private String jsonData;

    /** 删除标识 */
    private Boolean deleted;

    /** 提交人 */
    private String createPerson;

    /** 创建时间 */
    private Date createDate;

    /** 最新更新者 */
    private String updatePerson;

    /** 更新时间 */
    private Date updateDate;

    /** 备注 */
    private String remark;

    /** 数据是否可编辑 */
    private Boolean editable;

    /** 操作原因备注 */
    private String operateReason;

    private String maintainTaskUuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEntityUuid() {
        return entityUuid;
    }

    public void setEntityUuid(String entityUuid) {
        this.entityUuid = entityUuid;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getEntityData() {
        return entityData;
    }

    public void setEntityData(String entityData) {
        this.entityData = entityData;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public String getOperateReason() {
        return operateReason;
    }

    public void setOperateReason(String operateReason) {
        this.operateReason = operateReason;
    }

    public String getMaintainTaskUuid() {
        return maintainTaskUuid;
    }

    public void setMaintainTaskUuid(String maintainTaskUuid) {
        this.maintainTaskUuid = maintainTaskUuid;
    }
}