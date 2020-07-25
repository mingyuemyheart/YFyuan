package com.moft.xfapply.model.external.dto;

import java.util.Date;

/**
 * (plan_library)
 * 
 * @author zhangshy
 * @version 1.0.0
 * @data 2019-09-02 16:35:50
 */
public class PlanLibraryDTO  extends RestDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 264825389779558551L;

    /** uuid */
    private String uuid;

    /** 组织机构uuid */
    private String departmentUuid;

    /** 名称 */
    private String name;

    /** （用户不可见）自定义关键字 + tags */
    private String keywords;

    /** 简述 */
    private String digest;

    /** 分类uuid */
    private String catalogUuid;

    /** 文件名称 */
    private String mediaName;

    /** 文件格式（后缀） */
    private String mediaFormat;

    /** 描述 */
    private String mediaDescription;

    /** 发布路径 */
    private String mediaUrl;

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
     * 获取名称
     * 
     * @return 名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置名称
     * 
     * @param name
     *          名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取（用户不可见）自定义关键字 + tags
     * 
     * @return （用户不可见）自定义关键字 + tags
     */
    public String getKeywords() {
        return this.keywords;
    }

    /**
     * 设置（用户不可见）自定义关键字 + tags
     * 
     * @param keywords
     *          （用户不可见）自定义关键字 + tags
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    /**
     * 获取简述
     * 
     * @return 简述
     */
    public String getDigest() {
        return this.digest;
    }

    /**
     * 设置简述
     * 
     * @param digest
     *          简述
     */
    public void setDigest(String digest) {
        this.digest = digest;
    }

    /**
     * 获取分类uuid
     * 
     * @return 分类uuid
     */
    public String getCatalogUuid() {
        return this.catalogUuid;
    }

    /**
     * 设置分类uuid
     * 
     * @param catalogUuid
     *          分类uuid
     */
    public void setCatalogUuid(String catalogUuid) {
        this.catalogUuid = catalogUuid;
    }

    /**
     * 获取文件名称
     * 
     * @return 文件名称
     */
    public String getMediaName() {
        return this.mediaName;
    }

    /**
     * 设置文件名称
     * 
     * @param mediaName
     *          文件名称
     */
    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    /**
     * 获取文件格式（后缀）
     * 
     * @return 文件格式（后缀）
     */
    public String getMediaFormat() {
        return this.mediaFormat;
    }

    /**
     * 设置文件格式（后缀）
     * 
     * @param mediaFormat
     *          文件格式（后缀）
     */
    public void setMediaFormat(String mediaFormat) {
        this.mediaFormat = mediaFormat;
    }

    /**
     * 获取描述
     * 
     * @return 描述
     */
    public String getMediaDescription() {
        return this.mediaDescription;
    }

    /**
     * 设置描述
     * 
     * @param mediaDescription
     *          描述
     */
    public void setMediaDescription(String mediaDescription) {
        this.mediaDescription = mediaDescription;
    }

    /**
     * 获取发布路径
     * 
     * @return 发布路径
     */
    public String getMediaUrl() {
        return this.mediaUrl;
    }

    /**
     * 设置发布路径
     * 
     * @param mediaUrl
     *          发布路径
     */
    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
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