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
 * 处置标签(disposal_tag)
 * 
 * @author zhaochaoqi
 * @version 1.0.0 2019-08-07
 */
public class DisposalTagDTO extends RestDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 6667674341339138104L;

    /** uuid */
    private String uuid;

    /** 处置要点uuid */
    private String disposalPointUuid;

    /** 对象类型（重点单位、水源） */
    private String type;

    /** property */
    private String property;

    /** 属性key(数据字典uuid或者自定义) */
    private String propertyKey;

    /** 属性值(数据字典值或者自定义) */
    private String propertyValue;

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

    /** 删除标识 */
    private Boolean deleted;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDisposalPointUuid() {
        return disposalPointUuid;
    }

    public void setDisposalPointUuid(String disposalPointUuid) {
        this.disposalPointUuid = disposalPointUuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getPropertyKey() {
        return propertyKey;
    }

    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}