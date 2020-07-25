package com.moft.xfapply.model.external.params;

public class SnapshotForm {
    private String uuid;  //snapshot uuid
    private Boolean editable;  // 保存true、提交:false
    private String operateType; //NEW,MODIFY,DELETE
    private String maintainData;
    private String operateReason;
    private String entityType;   //实体类别

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getMaintainData() {
        return maintainData;
    }

    public void setMaintainData(String maintainData) {
        this.maintainData = maintainData;
    }

    public String getOperateReason() {
        return operateReason;
    }

    public void setOperateReason(String operateReason) {
        this.operateReason = operateReason;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }
}
