package com.moft.xfapply.model.external.dto;

import java.util.Date;

/**
 * (plan_library_catalog)
 * 
 * @author zhangshy
 * @version 1.0.0
 * @data 2019-09-02 16:35:50
 */
public class PlanLibraryCatalogDTO  extends RestDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 7849208958190183136L;

    /** uuid */
    private String uuid;

    /** 分类名称 */
    private String name;

    /** 组织机构uuid */
    private String departmentUuid;

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

    /**
     * 获取uuid
     * 
     * @return uuid
     */
    public String getUuid() {
        return this.uuid;
    }

    /**
     * 设置uuid
     * 
     * @param uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获取分类名称
     * 
     * @return 分类名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置分类名称
     * 
     * @param name
     *          分类名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取组织机构uuid
     * 
     * @return 组织机构uuid
     */
    public String getDepartmentUuid() {
        return this.departmentUuid;
    }

    /**
     * 设置组织机构uuid
     * 
     * @param departmentUuid
     *          组织机构uuid
     */
    public void setDepartmentUuid(String departmentUuid) {
        this.departmentUuid = departmentUuid;
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
}