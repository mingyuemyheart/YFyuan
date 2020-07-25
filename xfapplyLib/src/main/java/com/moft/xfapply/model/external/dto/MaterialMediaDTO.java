/**
 * Copyright (C), 2012-2018, 大连云帆科技有限公司
 * FileName: CompAttachFileDTO
 * Author:   wangxu
 * Date:     2018/3/20 17:14
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.moft.xfapply.model.external.dto;

import com.moft.xfapply.model.external.base.IMediaDTO;

import java.util.Date;

/**
 * 附件(MaterialMedia)
 * 
 * @author wangxy
 * @version 1.0.0 2017-04-14
 */
public class MaterialMediaDTO extends RestDTO implements IMediaDTO {

    private static final long serialVersionUID = 3711683303327993022L;

    private String uuid;

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

    /** FIRE_VEHICLE、EXTINGUISHING_AGENT、EQUIPMENT */
    private String type;

    /** 根据type的类型，存储不同的uuid */
    private String materialUuid;

    private String belongtoGroup;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaterialUuid() {
        return materialUuid;
    }

    public void setMaterialUuid(String materialUuid) {
        this.materialUuid = materialUuid;
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

    @Override
    public String getBelongToUuid() {
        return materialUuid;
    }

    @Override
    public void setBelongToUuid(String belongToUuid) {
        materialUuid = belongToUuid;
    }

    public String getBelongtoGroup() {
        return belongtoGroup;
    }

    public void setBelongtoGroup(String belongtoGroup) {
        this.belongtoGroup = belongtoGroup;
    }
}