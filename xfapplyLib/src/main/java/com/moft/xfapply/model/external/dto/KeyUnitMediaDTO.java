package com.moft.xfapply.model.external.dto;

import com.moft.xfapply.model.external.base.IMediaDTO;

import java.util.Date;

/**
 * key_unit_media
 * 
 * @author wangx
 * @version 1.0.0 2018-06-02
 */
public class KeyUnitMediaDTO extends RestDTO implements IMediaDTO {
    /** 版本号 */
    private static final long serialVersionUID = 1322598449518838585L;

    /** 唯一标识 */
    private String uuid;

    /** 所属对象类型 */
    private String belongToType;

    /** 所属对象标识 */
    private String belongToUuid;

    /** 文件分类
    数据字典：总体平面图、楼层平面图、附加图片 */
    private String classification;

    /** 媒体库uuid */
    private String mediaUuid;

    /** 文件名称 */
    private String mediaName;

    /** 文件格式（后缀） */
    private String mediaFormat;

    /** 外部访问路径 */
    private String mediaUrl;

    /** 概要描述 */
    private String mediaDescription;

    private String thumbnailUrl;

    /** 删除标识 */
    private Boolean deleted;

    /** 数据版本 */
    private Integer version;

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

    private String keyUnitUuid;

    private String belongtoGroup;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getBelongToType() {
        return belongToType;
    }

    public void setBelongToType(String belongToType) {
        this.belongToType = belongToType;
    }

    public String getBelongToUuid() {
        return belongToUuid;
    }

    public void setBelongToUuid(String belongToUuid) {
        this.belongToUuid = belongToUuid;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
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

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public String getKeyUnitUuid() {
        return keyUnitUuid;
    }

    public void setKeyUnitUuid(String keyUnitUuid) {
        this.keyUnitUuid = keyUnitUuid;
    }

    public String getMediaDescription() {
        return mediaDescription;
    }

    public void setMediaDescription(String mediaDescription) {
        this.mediaDescription = mediaDescription;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getBelongtoGroup() {
        return belongtoGroup;
    }

    public void setBelongtoGroup(String belongtoGroup) {
        this.belongtoGroup = belongtoGroup;
    }
}