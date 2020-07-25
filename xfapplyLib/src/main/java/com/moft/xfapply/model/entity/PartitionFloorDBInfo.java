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
 * partition_floor
 * 
 * @author wangx
 * @version 1.0.0 2018-05-22
 */
@Table(name = "partition_floor")
public class PartitionFloorDBInfo implements IPartitionInfo {
    /** 版本号 */
    @Transient
    private static final long serialVersionUID = 6222028378434176738L;

    @Id
    private String uuid; // 唯一标识

    /** name */
    @Property(column = "name")
    private String name;

    /** partitionType */
    @Property(column = "partition_type")
    private String partitionType;

    /** belongToPartitionUuid */
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

    /** longitude */
    @Property(column = "longitude")
    private String longitude;

    /** latitude */
    @Property(column = "latitude")
    private String latitude;

    @Property(column = "geometry_type")
    private String geometryType;//位置类型

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

    /** remark */
    @Property(column = "remark")
    private String remark;

    /** 所属建筑标识
 */
    @Property(column = "stucture_uuid")
    private String stuctureUuid;

    /** 楼层分类
 */
    @Property(column = "floor_type")
    private String floorType;

    /** 功能描述
 */
    @Property(column = "function_description")
    private String functionDescription;

    /** 具体楼层描述
 */
    @Property(column = "floors")
    private String floors;

    /** 层数
 */
    @Property(column = "floor_count")
    private Integer floorCount;

    @Transient
    private List<PropertyDes> pdListDetail = new ArrayList<PropertyDes>();
    @Transient
    private PropertyDes namePropertyDes;

    public PartitionFloorDBInfo() {
        timeStationArrive = 0;
        floorCount = 0;
    }

    public List<PropertyDes> getPdListDetail() {
        pdListDetail.clear();

        pdListDetail.add(new PropertyDes("楼层", uuid, PropertyDes.TYPE_NONE, PartitionFloorDBInfo.class));
        namePropertyDes = new PropertyDes("名称*", "setName", String.class, name, PropertyDes.TYPE_EDIT, true);
        pdListDetail.add(namePropertyDes);
        pdListDetail.add(new PropertyDes("楼层分类", "setFloorType", String.class, floorType, LvApplication.getInstance().getCompDicMap().get("FLOOR_TYPE")));
        pdListDetail.add(new PropertyDes("功能描述", "setFunctionDescription", String.class, functionDescription, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("层数", "setFloorCount", Integer.class, floorCount, PropertyDes.TYPE_EDIT));

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
     * 获取所属建筑标识

     * 
     * @return 所属建筑标识

     */
    public String getStuctureUuid() {
        return this.stuctureUuid;
    }

    /**
     * 设置所属建筑标识

     * 
     * @param stuctureUuid 
     *          所属建筑标识

     */
    public void setStuctureUuid(String stuctureUuid) {
        this.stuctureUuid = stuctureUuid;
    }

    /**
     * 获取楼层分类

     * 
     * @return 楼层分类

     */
    public String getFloorType() {
        return this.floorType;
    }

    /**
     * 设置楼层分类

     * 
     * @param floorType
     *          楼层分类

     */
    public void setFloorType(String floorType) {
        this.floorType = floorType;
    }

    /**
     * 获取功能描述

     * 
     * @return 功能描述

     */
    public String getFunctionDescription() {
        return this.functionDescription;
    }

    /**
     * 设置功能描述

     * 
     * @param functionDescription
     *          功能描述

     */
    public void setFunctionDescription(String functionDescription) {
        this.functionDescription = functionDescription;
    }

    /**
     * 获取具体楼层描述

     * 
     * @return 具体楼层描述

     */
    public String getFloors() {
        return this.floors;
    }

    /**
     * 设置具体楼层描述

     * 
     * @param floors
     *          具体楼层描述

     */
    public void setFloors(String floors) {
        this.floors = floors;
    }

    /**
     * 获取层数

     * 
     * @return 层数

     */
    public Integer getFloorCount() {
        return this.floorCount;
    }

    /**
     * 设置层数

     * 
     * @param floorCount
     *          层数

     */
    public void setFloorCount(Integer floorCount) {
        this.floorCount = floorCount;
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