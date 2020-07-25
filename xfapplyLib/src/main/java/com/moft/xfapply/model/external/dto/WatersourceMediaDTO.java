package com.moft.xfapply.model.external.dto;

import com.moft.xfapply.model.external.base.IMediaDTO;

import java.util.Date;
import java.util.List;

/**
 * watersource_media
 * 
 * @author zhangshy
 * @version 1.0.0 2018-06-06
 */
public class WatersourceMediaDTO implements IMediaDTO {
    /** 版本号 */
    private static final long serialVersionUID = 6275279291511868756L;

    /** 主键标识 */
    private String uuid;

    /** 水源类型 */
    private String type;

    /** 水源主键 */
    private String wsUuid;

    private String belongtoGroup;

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

    /** 是否删除 */
    private Boolean deleted;

    /** 数据版本 */
    private Integer version;

    /** 创建人 */
    private String createPerson;

    /** 创建时间 */
    private Date createDate;

    /** 更新人 */
    private String updatePerson;

    /** 更新时间 */
    private Date updateDate;

    /** 备注 */
    private String remark;

    /**
     * 获取主键标识
     * 
     * @return 主键标识
     */
    public String getUuid() {
        return this.uuid;
    }

    /**
     * 设置主键标识
     * 
     * @param uuid
     *          主键标识
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获取水源类型
     * 
     * @return 水源类型
     */
    public String getType() {
        return this.type;
    }

    /**
     * 设置水源类型
     * 
     * @param type
     *          水源类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取水源主键
     *
     * @return 水源主键
     */
    public String getWsUuid() {
        return wsUuid;
    }

    /**
     * 设置水源主键
     *
     * @param wsUuid
     *          水源主键
     */
    public void setWsUuid(String wsUuid) {
        this.wsUuid = wsUuid;
    }

    /**
     * 获取媒体库uuid
     * 
     * @return 媒体库uuid
     */
    public String getMediaUuid() {
        return this.mediaUuid;
    }

    /**
     * 设置媒体库uuid
     * 
     * @param mediaUuid
     *          媒体库uuid
     */
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

    /**
     * 获取是否删除
     * 
     * @return 是否删除
     */
    public Boolean getDeleted() {
        return this.deleted;
    }

    /**
     * 设置是否删除
     * 
     * @param deleted
     *          是否删除
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取数据版本
     * 
     * @return 数据版本
     */
    public Integer getVersion() {
        return this.version;
    }

    /**
     * 设置数据版本
     * 
     * @param version
     *          数据版本
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 获取创建人
     * 
     * @return 创建人
     */
    public String getCreatePerson() {
        return this.createPerson;
    }

    /**
     * 设置创建人
     * 
     * @param createPerson
     *          创建人
     */
    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    /**
     * 获取创建时间
     * 
     * @return 创建时间
     */
    public Date getCreateDate() {
        return this.createDate;
    }

    /**
     * 设置创建时间
     * 
     * @param createDate
     *          创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdatePerson() {
        return this.updatePerson;
    }

    public void setUpdatePerson(String updatePersion) {
        this.updatePerson = updatePersion;
    }

    /**
     * 获取更新时间
     * 
     * @return 更新时间
     */
    public Date getUpdateDate() {
        return this.updateDate;
    }

    /**
     * 设置更新时间
     * 
     * @param updateDate
     *          更新时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取备注
     * 
     * @return 备注
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置备注
     * 
     * @param remark
     *          备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String getBelongToUuid() {
        return wsUuid;
    }

    @Override
    public void setBelongToUuid(String belongToUuid) {
        wsUuid = belongToUuid;
    }
}