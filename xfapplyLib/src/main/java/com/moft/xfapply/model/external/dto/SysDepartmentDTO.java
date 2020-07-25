/**
 * Copyright (C), 2012-2018, 大连云帆科技有限公司
 * FileName: SysDepartmentDTO
 * Author:   ZhangShouyan
 * Date:     2018/3/22 0022 19:28
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.moft.xfapply.model.external.dto;

import java.util.Date;

/**
 * 组织结构(APP_ORGANIZATION)
 * 
 * @author wangxy
 * @version 1.0.0 2017-04-14
 */
public class SysDepartmentDTO extends RestDTO implements java.io.Serializable {
    
    private static final long serialVersionUID = 8611077616488564353L;
    /**
     *唯一标识
     */
    private String  uuid;
    /**
     *行政区划
     */
    private String  districtCode;
    /**
     *父uuid
     */
    private String  parentUuid;
    /**
     *编码
     */
    private String  code;
    /**
     *名称
     */
    private String  name;
    /**
     *等级
     */
    private Integer grade;
    /**
     *地址
     */
    private String address;
    /**
     *联系人
     */
    private String contacts;
    /**
     * 联系方式
     */
    private String contactNumber;
    private String createPerson;
    private Date createDate;
    private String updatePerson;
    private Date updateDate;
    private Boolean deleted;
    private String remark;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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