package com.moft.xfapply.model.external.dto;/*
 * Welcome to use the TableGo Tools.
 * 
 * http://vipbooks.iteye.com
 * http://blog.csdn.net/vipbooks
 * http://www.cnblogs.com/vipbooks
 * 
 * Author:bianj
 * Email:edinsker@163.com
 * Version:5.8.8
 */

import java.util.Date;

/**
 * 知识库(knowledge_base)
 * 
 * @author zhaochaoqi
 * @version 1.0.0 2019-08-07
 */
public class KnowledgeBaseDTO extends RestDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -846157857560766760L;

    /** uuid */
    private String uuid;

    /** 组织机构uuid */
    private String departmentUuid;

    /** 处置要点名称 */
    private String name;

    /** （用户不可见）自定义关键字 + tags */
    private String keywords;

    /** 简述 */
    private String digest;

    /** 内容 */
    private String content;

    /** 外部链接 */
    private String url;

    /** 媒体库uuid */
    private String mediaUuid;

    /** 文件名称 */
    private String mediaName;

    /** 文件格式（后缀） */
    private String mediaFormat;

    /** 描述 */
    private String mediaDescription;

    /** 发布路径 */
    private String mediaUrl;

    /** thumbnailUrl */
    private String thumbnailUrl;

    /** 提交人 */
    private String createPerson;

    /** 创建时间 */
    private Date createDate;

    /** 更新人 */
    private String updatePerson;

    /** 更新时间 */
    private Date updateDate;

    /** 删除标识 */
    private Boolean deleted;

    /** 备注 */
    private String remark;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMediaUuid() {
        return mediaUuid;
    }

    public void setMediaUuid(String mediaUuid) {
        this.mediaUuid = mediaUuid;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public String getMediaFormat() {
        return mediaFormat;
    }

    public void setMediaFormat(String mediaFormat) {
        this.mediaFormat = mediaFormat;
    }

    public String getMediaDescription() {
        return mediaDescription;
    }

    public void setMediaDescription(String mediaDescription) {
        this.mediaDescription = mediaDescription;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}