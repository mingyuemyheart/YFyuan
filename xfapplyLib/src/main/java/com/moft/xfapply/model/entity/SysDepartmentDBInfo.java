/**
 * Copyright (C), 2012-2018, 大连云帆科技有限公司
 * FileName: SysDepartment
 * Author:   wangx
 * Date:     2018/3/22 0022 19:28
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.moft.xfapply.model.entity;
import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Property;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;

import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;

/**
 * 组织结构(APP_ORGANIZATION)
 * 
 * @author wangxy
 * @version 1.0.0 2017-04-14
 */
@Table(name = "sys_department")
public class SysDepartmentDBInfo implements java.io.Serializable {
    /** 版本号 */
    /**
     * @Fields serialVersionUID :
     */
    @Transient
    private static final long serialVersionUID = 1L;
    @Id
    private String uuid; // 唯一标识
    @Property(column = "district_code")
    private String  districtCode; // 行政区划
    @Property(column = "parent_uuid")
    private String  parentUuid;   // 父uuid
    private String  code;         // 编码
    private String  name;         // 名称
    /**
     * 等级
     * 0：标识 总队
     * 1：标识 支队
     * 2：标识 大队
     * 3：标识 中队
     */
    @Property(column = "grade")
    private Integer grade;        // 等级
    @Property(column = "type")
    private String type;        //类型
    @Property(column = "address")
    private String address;       //
    @Property(column = "contacts")
    private String contacts;
    @Property(column = "contact_number")
    private String contactNumber;
    @Property(column = "create_person")
    private String createPerson;
    @Property(column = "create_date")
    private Date createDate;
    @Property(column = "update_person")
    private String updatePerson;
    @Property(column = "update_date")
    private Date updateDate;
    @Property(column = "deleted")
    private Boolean deleted;
    @Property(column = "remark")
    private String remark;
    @Property(column = "relegation")
    private String relegation;


    /**构造函数**/
    public SysDepartmentDBInfo() {
    }

    /*set get method*/

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getRelegation() {
        return relegation;
    }

    public void setRelegation(String relegation) {
        this.relegation = relegation;
    }
}