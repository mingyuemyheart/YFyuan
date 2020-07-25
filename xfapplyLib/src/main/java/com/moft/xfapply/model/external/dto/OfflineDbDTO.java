/**
 * Copyright (C), 2012-2018, 大连云帆科技有限公司
 * FileName: OfflineDbDTO
 * Author:   wangxu
 * Date:     2018/3/20 17:14
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.moft.xfapply.model.external.dto;

import java.util.Date;

/**
 * (DT_OFFLINE_DB)
 * 
 * @author wangxy
 * @version 1.0.0 2017-04-14
 */
public class OfflineDbDTO extends RestDTO implements java.io.Serializable {
    
    private static final long serialVersionUID = 1881366363365529346L;
    private String uuid;            // 唯一编码
    private String departmentUuid;// 部门Uuuid
    private String belongtoGroup;
    private String configUuid;     // 数据类型
    private String type;            // 数据类型
    private Date startDate;        // 开始时间
    private Date endDate;          // 结束时间
    private String status;         // 当前状态
    private String url;            // 路径
    private String submitter;     // 提交者
    private String fileSize;      // db大小
    private Integer versionNo;    // 版本号

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

    public Integer getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Integer versionNo) {
        this.versionNo = versionNo;
    }
}