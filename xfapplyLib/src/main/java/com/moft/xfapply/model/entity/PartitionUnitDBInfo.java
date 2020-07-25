package com.moft.xfapply.model.entity;


import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.model.base.IGeomElementInfo;
import com.moft.xfapply.model.base.IPartitionInfo;
import com.moft.xfapply.model.common.PropertyDes;
import com.moft.xfapply.utils.StringUtil;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Property;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * partition_unit
 * 
 * @author wangx
 * @version 1.0.0 2018-05-22
 */
@Table(name = "partition_unit")
public class PartitionUnitDBInfo implements IPartitionInfo {
    /** 版本号 */
    @Transient
    private static final long serialVersionUID = -424579462333027969L;

    @Id
    private String uuid; // 唯一标识

    /** name */
    @Property(column = "name")
    private String name;

    /** partitionType */
    @Property(column = "partition_type")
    private String partitionType;

    /** belongToPartitionUuid  */
    @Property(column = "belong_to_partition_uuid")
    private String belongToPartitionUuid;

    /** departmentUuid */
    @Property(column = "department_uuid")
    private String departmentUuid;

    /** address */
    @Property(column = "address")
    private String address;

    /** geometryText */
    @Property(column = "geometry_text")
    private String geometryText;

    /** adjacentEast */
    @Property(column = "adjacent_east")
    private String adjacentEast;

    /** adjacentWest */
    @Property(column = "adjacent_west")
    private String adjacentWest;

    /** adjacentSouth */
    @Property(column = "adjacent_south")
    private String adjacentSouth;

    /** adjacentNorth */
    @Property(column = "adjacent_north")
    private String adjacentNorth;

    /** routeFromStation */
    @Property(column = "route_from_station")
    private String routeFromStation;

    /** timeStationArrive */
    @Property(column = "time_station_arrive")
    private Integer timeStationArrive;

    /** belongtoGroup */
    @Property(column = "belongto_group")
    private String belongtoGroup;

    /** deleted */
    @Property(column = "deleted")
    private Boolean deleted;

    @Property(column = "data_json")
    private String dataJson; //JSON化数据

    /** version */
    @Property(column = "version")
    private Integer version;

    /** createPerson */
    @Property(column = "create_person")
    private String createPerson;

    /** createDate */
    @Property(column = "create_date")
    private Date createDate;

    /** updatePerson */
    @Property(column = "update_person")
    private String updatePerson;

    /** updateDate */
    @Property(column = "update_date")
    private Date updateDate;

    /** longitude */
    @Property(column = "longitude")
    private String longitude;

    /** latitude */
    @Property(column = "latitude")
    private String latitude;

    @Property(column = "geometry_type")
    private String geometryType;//位置类型

    /** remark */
    @Property(column = "remark")
    private String remark;

    /** dummy */
    @Property(column = "dummy")
    private String dummy;

    @Transient
    private List<PropertyDes> pdListDetail = new ArrayList<PropertyDes>();
    @Transient
    private PropertyDes namePropertyDes;

    public PartitionUnitDBInfo() {
        timeStationArrive = 0;
    }

    public List<PropertyDes> getPdListDetail() {
        pdListDetail.clear();

        pdListDetail.add(new PropertyDes("自定义分区", uuid, PropertyDes.TYPE_NONE, PartitionUnitDBInfo.class));
        namePropertyDes = new PropertyDes("名称*", "setName", String.class, name, PropertyDes.TYPE_EDIT, true);
        pdListDetail.add(namePropertyDes);

        pdListDetail.add(new PropertyDes("位置", "setAddress", String.class, address, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("毗邻情况(东)", "setAdjacentEast", String.class, adjacentEast, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("毗邻情况(西)", "setAdjacentWest", String.class, adjacentWest, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("毗邻情况(南)", "setAdjacentSouth", String.class, adjacentSouth, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("毗邻情况(北)", "setAdjacentNorth", String.class, adjacentNorth, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("辖区中队行车路线", "setRouteFromStation", String.class, routeFromStation,PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("预计到达时长", "setTimeStationArrive", Integer.class, timeStationArrive, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("备注", "setRemark", String.class, remark, PropertyDes.TYPE_EDIT));

        return pdListDetail;
    }

    public PropertyDes getNamePropertyDes() {
        return namePropertyDes;
    }

    public void setAdapter(IGeomElementInfo info) {
        if(StringUtil.isEmpty(departmentUuid)) {
            departmentUuid = info.getDepartmentUuid();
        }
    }

    /**
     * 获取name
     * 
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置name
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取partitionType
     * 
     * @return partitionType
     */
    public String getPartitionType() {
        return this.partitionType;
    }

    /**
     * 设置partitionType
     * 
     * @param partitionType
     */
    public void setPartitionType(String partitionType) {
        this.partitionType = partitionType;
    }

    /**
     * 获取belongToPartitionUuid 
     * 
     * @return belongToPartitionUuid 
     */
    public String getBelongToPartitionUuid() {
        return this.belongToPartitionUuid;
    }

    /**
     * 设置belongToPartitionUuid 
     * 
     * @param belongToPartitionUuid 
     */
    public void setBelongToPartitionUuid(String belongToPartitionUuid) {
        this.belongToPartitionUuid = belongToPartitionUuid;
    }

    /**
     * 获取departmentUuid
     * 
     * @return departmentUuid
     */
    public String getDepartmentUuid() {
        return this.departmentUuid;
    }

    /**
     * 设置departmentUuid
     * 
     * @param departmentUuid
     */
    public void setDepartmentUuid(String departmentUuid) {
        this.departmentUuid = departmentUuid;
    }

    /**
     * 获取address
     * 
     * @return address
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * 设置address
     * 
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取geometryText
     * 
     * @return geometryText
     */
    public String getGeometryText() {
        return this.geometryText;
    }

    /**
     * 设置geometryText
     * 
     * @param geometryText
     */
    public void setGeometryText(String geometryText) {
        this.geometryText = geometryText;
    }

    @Override
    public String getLongitude() {
        return longitude;
    }

    @Override
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String getLatitude() {
        return latitude;
    }

    @Override
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getGeometryType() {
        return geometryType;
    }

    public void setGeometryType(String geometryType) {
        this.geometryType = geometryType;
    }

    /**
     * 获取adjacentEast
     * 
     * @return adjacentEast
     */
    public String getAdjacentEast() {
        return this.adjacentEast;
    }

    /**
     * 设置adjacentEast
     * 
     * @param adjacentEast
     */
    public void setAdjacentEast(String adjacentEast) {
        this.adjacentEast = adjacentEast;
    }

    /**
     * 获取adjacentWest
     * 
     * @return adjacentWest
     */
    public String getAdjacentWest() {
        return this.adjacentWest;
    }

    /**
     * 设置adjacentWest
     * 
     * @param adjacentWest
     */
    public void setAdjacentWest(String adjacentWest) {
        this.adjacentWest = adjacentWest;
    }

    /**
     * 获取adjacentSouth
     * 
     * @return adjacentSouth
     */
    public String getAdjacentSouth() {
        return this.adjacentSouth;
    }

    /**
     * 设置adjacentSouth
     * 
     * @param adjacentSouth
     */
    public void setAdjacentSouth(String adjacentSouth) {
        this.adjacentSouth = adjacentSouth;
    }

    /**
     * 获取adjacentNorth
     * 
     * @return adjacentNorth
     */
    public String getAdjacentNorth() {
        return this.adjacentNorth;
    }

    /**
     * 设置adjacentNorth
     * 
     * @param adjacentNorth
     */
    public void setAdjacentNorth(String adjacentNorth) {
        this.adjacentNorth = adjacentNorth;
    }

    /**
     * 获取routeFromStation
     * 
     * @return routeFromStation
     */
    public String getRouteFromStation() {
        return this.routeFromStation;
    }

    /**
     * 设置routeFromStation
     * 
     * @param routeFromStation
     */
    public void setRouteFromStation(String routeFromStation) {
        this.routeFromStation = routeFromStation;
    }

    /**
     * 获取timeStationArrive
     * 
     * @return timeStationArrive
     */
    public Integer getTimeStationArrive() {
        return this.timeStationArrive;
    }

    /**
     * 设置timeStationArrive
     * 
     * @param timeStationArrive
     */
    public void setTimeStationArrive(Integer timeStationArrive) {
        this.timeStationArrive = timeStationArrive;
    }

    /**
     * 获取deleted
     * 
     * @return deleted
     */
    public Boolean getDeleted() {
        return this.deleted;
    }

    /**
     * 设置deleted
     * 
     * @param deleted
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }

    /**
     * 获取version
     * 
     * @return version
     */
    public Integer getVersion() {
        return this.version;
    }

    /**
     * 设置version
     * 
     * @param version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 获取createPerson
     * 
     * @return createPerson
     */
    public String getCreatePerson() {
        return this.createPerson;
    }

    /**
     * 设置createPerson
     * 
     * @param createPerson
     */
    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    /**
     * 获取createDate
     * 
     * @return createDate
     */
    public Date getCreateDate() {
        return this.createDate;
    }

    /**
     * 设置createDate
     * 
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取updatePerson
     * 
     * @return updatePerson
     */
    public String getUpdatePerson() {
        return this.updatePerson;
    }

    /**
     * 设置updatePerson
     * 
     * @param updatePerson
     */
    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    /**
     * 获取updateDate
     * 
     * @return updateDate
     */
    public Date getUpdateDate() {
        return this.updateDate;
    }

    /**
     * 设置updateDate
     * 
     * @param updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取remark
     * 
     * @return remark
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置remark
     * 
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取dummy
     * 
     * @return dummy
     */
    public String getDummy() {
        return this.dummy;
    }

    /**
     * 设置dummy
     * 
     * @param dummy
     */
    public void setDummy(String dummy) {
        this.dummy = dummy;
    }

    /**
     * 获取belongtoGroup
     * 
     * @return belongtoGroup
     */
    public String getBelongtoGroup() {
        return this.belongtoGroup;
    }

    /**
     * 设置belongtoGroup
     * 
     * @param belongtoGroup
     */
    public void setBelongtoGroup(String belongtoGroup) {
        this.belongtoGroup = belongtoGroup;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}