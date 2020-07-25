package com.moft.xfapply.model.external.dto;

import java.util.Date;

public class ExportIncSqlDTO extends RestDTO implements java.io.Serializable {
    private String departmentUuid;
    private String belongtoGroup;
    private String configUuid;
    private String type;
    private Date startDate;
    private Date endDate;
    private String status;
    private String url;
    private String submitter;
    private String fileSize;
    private Date incStartTime;
    private Date incEndTime;
    private Integer versionNo;

    public String getDepartmentUuid() {
        return departmentUuid;
    }

    public void setDepartmentUuid(String departmentUuid) {
        this.departmentUuid = departmentUuid;
    }

    public String getBelongtoGroup() {
        return belongtoGroup;
    }

    public void setBelongtoGroup(String belongtoGroup) {
        this.belongtoGroup = belongtoGroup;
    }

    public String getConfigUuid() {
        return configUuid;
    }

    public void setConfigUuid(String configUuid) {
        this.configUuid = configUuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Date getIncStartTime() {
        return incStartTime;
    }

    public void setIncStartTime(Date incStartTime) {
        this.incStartTime = incStartTime;
    }

    public Date getIncEndTime() {
        return incEndTime;
    }

    public void setIncEndTime(Date incEndTime) {
        this.incEndTime = incEndTime;
    }

    public Integer getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Integer versionNo) {
        this.versionNo = versionNo;
    }
}
