package com.moft.xfapply.model.external.dto;

import java.util.Date;

/**
 * partition_base
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class PartitionBaseDTO extends RestDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -5309629764261037026L;

    /** 唯一标识
 */
    private String uuid;

    /** 名称
 */
    private String name;

    /** 功能分区类型
 */
    private String partitionType;

    /** 所属分区标识
 */
    private String belongToPartitionUuid;

    /** 数据所属区域
 */
    private String departmentUuid;

    /** 位置
 */
    private String address;

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

    /** 毗邻情况-东
 */
    private String adjacentEast;

    /** 毗邻情况-西
 */
    private String adjacentWest;

    /** 毗邻情况-南
 */
    private String adjacentSouth;

    /** 毗邻情况-北
 */
    private String adjacentNorth;

    /** 辖区行车路线
 */
    private String routeFromStation;

    /** 到达时长
 */
    private Integer timeStationArrive;

    /** 删除标识
 */
    private Boolean deleted;

    /** 数据版本
 */
    private Integer version;

    /** 提交人
 */
    private String createPerson;

    /** 创建时间
 */
    private Date createDate;

    /** 最新更新者
 */
    private String updatePerson;

    /** 更新时间
 */
    private Date updateDate;

    /** 备注
 */
    private String remark;

    private String belongtoGroup;

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

    public String getPartitionType() {
        return partitionType;
    }

    public void setPartitionType(String partitionType) {
        this.partitionType = partitionType;
    }

    public String getBelongToPartitionUuid() {
        return belongToPartitionUuid;
    }

    public void setBelongToPartitionUuid(String belongToPartitionUuid) {
        this.belongToPartitionUuid = belongToPartitionUuid;
    }

    public String getDepartmentUuid() {
        return departmentUuid;
    }

    public void setDepartmentUuid(String departmentUuid) {
        this.departmentUuid = departmentUuid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getAdjacentEast() {
        return adjacentEast;
    }

    public void setAdjacentEast(String adjacentEast) {
        this.adjacentEast = adjacentEast;
    }

    public String getAdjacentWest() {
        return adjacentWest;
    }

    public void setAdjacentWest(String adjacentWest) {
        this.adjacentWest = adjacentWest;
    }

    public String getAdjacentSouth() {
        return adjacentSouth;
    }

    public void setAdjacentSouth(String adjacentSouth) {
        this.adjacentSouth = adjacentSouth;
    }

    public String getAdjacentNorth() {
        return adjacentNorth;
    }

    public void setAdjacentNorth(String adjacentNorth) {
        this.adjacentNorth = adjacentNorth;
    }

    public String getRouteFromStation() {
        return routeFromStation;
    }

    public void setRouteFromStation(String routeFromStation) {
        this.routeFromStation = routeFromStation;
    }

    public Integer getTimeStationArrive() {
        return timeStationArrive;
    }

    public void setTimeStationArrive(Integer timeStationArrive) {
        this.timeStationArrive = timeStationArrive;
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

    public String getBelongtoGroup() {
        return belongtoGroup;
    }

    public void setBelongtoGroup(String belongtoGroup) {
        this.belongtoGroup = belongtoGroup;
    }
}