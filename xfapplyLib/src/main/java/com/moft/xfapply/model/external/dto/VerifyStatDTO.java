package com.moft.xfapply.model.external.dto;


/**
 * 
 * @author wangxy
 * @version 1.0.0 2017-04-14
 */
public class VerifyStatDTO extends RestDTO implements java.io.Serializable {
    
    private static final long serialVersionUID = 1881366363365529346L;

    private String  departmentCode;
    private String  departmentName;

    private String brigadeCode;
    private String brigadeName;

    private String detachmentCode;
    private String detachmentName;

    private String battalionCode;
    private String battalionName;

    private String squadronCode;
    private String squadronName;

    private String  entityType;
    private Integer newCount;
    private Integer modifyCount;
    private Integer deleteCount;
    private Integer verifyCount;
    private Integer updateCount; // 资源变更数
    private Integer submitCount;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getBrigadeCode() {
        return brigadeCode;
    }

    public void setBrigadeCode(String brigadeCode) {
        this.brigadeCode = brigadeCode;
    }

    public String getBrigadeName() {
        return brigadeName;
    }

    public void setBrigadeName(String brigadeName) {
        this.brigadeName = brigadeName;
    }

    public String getDetachmentCode() {
        return detachmentCode;
    }

    public void setDetachmentCode(String detachmentCode) {
        this.detachmentCode = detachmentCode;
    }

    public String getDetachmentName() {
        return detachmentName;
    }

    public void setDetachmentName(String detachmentName) {
        this.detachmentName = detachmentName;
    }

    public String getBattalionCode() {
        return battalionCode;
    }

    public void setBattalionCode(String battalionCode) {
        this.battalionCode = battalionCode;
    }

    public String getBattalionName() {
        return battalionName;
    }

    public void setBattalionName(String battalionName) {
        this.battalionName = battalionName;
    }

    public String getSquadronCode() {
        return squadronCode;
    }

    public void setSquadronCode(String squadronCode) {
        this.squadronCode = squadronCode;
    }

    public String getSquadronName() {
        return squadronName;
    }

    public void setSquadronName(String squadronName) {
        this.squadronName = squadronName;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Integer getNewCount() {
        return newCount;
    }
    
    public void setNewCount(Integer newCount) {
        this.newCount = newCount;
    }
    
    public Integer getModifyCount() {
        return modifyCount;
    }
    
    public void setModifyCount(Integer modifyCount) {
        this.modifyCount = modifyCount;
    }
    
    public Integer getDeleteCount() {
        return deleteCount;
    }
    
    public void setDeleteCount(Integer deleteCount) {
        this.deleteCount = deleteCount;
    }
    
    public Integer getVerifyCount() {
        return verifyCount;
    }
    
    public void setVerifyCount(Integer verifyCount) {
        this.verifyCount = verifyCount;
    }

    public Integer getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(Integer updateCount) {
        this.updateCount = updateCount;
    }
}