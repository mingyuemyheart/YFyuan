package com.moft.xfapply.model.external.dto;

import java.util.Date;

/**
 * fire_facility_base
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class FireFacilityBaseDTO extends RestDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -7037230196643160482L;

    /** 唯一标识
 */
    private String uuid;

    /** 名称
 */
    private String name;

    /** 数据所属区域
 */
    private String departmentUuid;

    /** 所属分区标识
 */
    private String partitionUuid;

    /** 位置
     */
    private String geometryText;

    /** 位置的类型
     */
    private String geometryType;

    /** 经度
     */
    private Double longitude;

    /** 纬度
     */
    private Double latitude;

    /** 设施类型
 */
    private String facilityType;


    /** 数量
 */
    private Integer count;

    /** 备注
 */
    private String remark;

    /** deleted */
    private Boolean deleted;

    /** version */
    private Integer version;

    /** createPerson */
    private String createPerson;

    /** createDate */
    private Date createDate;

    /** updatePerson */
    private String updatePerson;

    /** updateDate */
    private Date updateDate;

    private String belongtoGroup;

    private String address;

    /** 所在平面图
     */
    private String planDiagram;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentUuid() {
        return departmentUuid;
    }

    public void setDepartmentUuid(String departmentUuid) {
        this.departmentUuid = departmentUuid;
    }

    public String getPartitionUuid() {
        return partitionUuid;
    }

    public void setPartitionUuid(String partitionUuid) {
        this.partitionUuid = partitionUuid;
    }

    public String getGeometryText() {
        return geometryText;
    }

    public void setGeometryText(String geometryText) {
        this.geometryText = geometryText;
    }

    public String getGeometryType() {
        return geometryType;
    }

    public void setGeometryType(String geometryType) {
        this.geometryType = geometryType;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public String getBelongtoGroup() {
        return belongtoGroup;
    }

    public void setBelongtoGroup(String belongtoGroup) {
        this.belongtoGroup = belongtoGroup;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlanDiagram() {
        return planDiagram;
    }

    public void setPlanDiagram(String planDiagram) {
        this.planDiagram = planDiagram;
    }
}