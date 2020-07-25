package com.moft.xfapply.model.external.dto;

import com.moft.xfapply.model.external.base.IMediaDTO;

import java.util.Date;

/**
 * joint_force_media
 * 
 * @author zhangshy
 * @version 1.0.0 2018-06-06
 */
public class JointForceMediaDTO implements IMediaDTO {
    /** 版本号 */
    private static final long serialVersionUID = 437256187752950312L;

    /** 主键标识 */
    private String uuid;

    /** 联动力量主键 */
    private String jointForceUuid;

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

    private String belongtoGroup;

    /** 删除标识 */
    private Boolean deleted;

    /** 版本号 */
    private Integer version;

    /** 提交人 */
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
     * 获取联动力量主键标识
     *
     * @return 联动力量主键标识
     */
    public String getJointForceUuid() {
        return jointForceUuid;
    }

    /**
     * 设置联动力量主键标识
     *
     * @param jointForceUuid
     *          联动力量主键标识
     */
    public void setJointForceUuid(String jointForceUuid) {
        this.jointForceUuid = jointForceUuid;
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

    @Override
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @Override
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
     * 获取删除标识
     * 
     * @return 删除标识
     */
    public Boolean getDeleted() {
        return this.deleted;
    }

    /**
     * 设置删除标识
     * 
     * @param deleted
     *          删除标识
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取版本号
     * 
     * @return 版本号
     */
    public Integer getVersion() {
        return this.version;
    }

    /**
     * 设置版本号
     * 
     * @param version
     *          版本号
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 获取提交人
     * 
     * @return 提交人
     */
    public String getCreatePerson() {
        return this.createPerson;
    }

    /**
     * 设置提交人
     * 
     * @param createPerson
     *          提交人
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

    /**
     * 获取更新人
     * 
     * @return 更新人
     */
    public String getUpdatePerson() {
        return this.updatePerson;
    }

    /**
     * 设置更新人
     * 
     * @param updatePerson
     *          更新人
     */
    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
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
        return jointForceUuid;
    }

    @Override
    public void setBelongToUuid(String belongToUuid) {
        jointForceUuid = belongToUuid;
    }
}