package com.moft.xfapply.model.external.dto;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author wangxy
 * @version 1.0.0 2017-04-14
 */
public class VerifyDepDTO extends RestDTO implements java.io.Serializable {
    
    private static final long serialVersionUID = 1881366363365529346L;

    private String departmentName;
    private String departmentCode;

    private String brigadeCode;
    private String brigadeName;

    private String detachmentCode;
    private String detachmentName;

    private String battalionCode;
    private String battalionName;

    private String squadronCode;
    private String squadronName;

    private Date                 startDate;
    private Date                 endDate;
    private List<VerifyStatDTO> actionStats;

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

    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public List<VerifyStatDTO> getActionStats() {
        return actionStats;
    }
    
    public void setActionStats(List<VerifyStatDTO> actionStats) {
        this.actionStats = actionStats;
    }
    
}