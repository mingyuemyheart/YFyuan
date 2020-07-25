package com.moft.xfapply.model.external.dto;

import java.util.Date;

/**
 * 重点单位与处置要点表(key_unit_disposal_point)
 * 
 * @author wangx
 * @version 1.0.0 2019-08-07
 */
public class KeyUnitDisposalPointDTO extends RestDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -8632061999772058858L;

    /** 唯一标识 */
    private String uuid;

    /** 重点单位uuid */
    private String keyUnitUuid;

    /** 要点uuid */
    private String disposalPointUuid;

    /** 要点名称 */
    private String disposalPointName;

    /** mediaUrl */
    private String mediaUrl;

    private String mediaFormat;

    /** 权重 */
    private Integer weight;

    /** 删除标识 */
    private Boolean deleted;

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

    private String publishUrl;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getKeyUnitUuid() {
        return keyUnitUuid;
    }

    public void setKeyUnitUuid(String keyUnitUuid) {
        this.keyUnitUuid = keyUnitUuid;
    }

    public String getDisposalPointUuid() {
        return disposalPointUuid;
    }

    public void setDisposalPointUuid(String disposalPointUuid) {
        this.disposalPointUuid = disposalPointUuid;
    }

    public String getDisposalPointName() {
        return disposalPointName;
    }

    public void setDisposalPointName(String disposalPointName) {
        this.disposalPointName = disposalPointName;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getMediaFormat() {
        return mediaFormat;
    }

    public void setMediaFormat(String mediaFormat) {
        this.mediaFormat = mediaFormat;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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

    public String getPublishUrl() {
        return publishUrl;
    }

    public void setPublishUrl(String publishUrl) {
        this.publishUrl = publishUrl;
    }
}