package com.moft.xfapply.model.entity;


import com.moft.xfapply.app.Constant;
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
 * partition_storage
 * 
 * @author wangx
 * @version 1.0.0 2018-05-22
 */
@Table(name = "partition_storage")
public class PartitionStorageDBInfo implements IPartitionInfo {
    /** 版本号 */
    @Transient
    private static final long serialVersionUID = -1257072499860973101L;

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

    /** 储罐类型
 */
    @Property(column = "type")
    private String type;

    /** 容量（m³）
 */
    @Property(column = "capacity")
    private Double capacity;

    /** 直径（m）
 */
    @Property(column = "diameter")
    private Double diameter;

    /** 高度（m）
 */
    @Property(column = "height")
    private Double height;

    /** 周长（m）
 */
    @Property(column = "perimeter")
    private Double perimeter;

    /** 罐顶面积（㎡）
 */
    @Property(column = "top_area")
    private Double topArea;

    /** 工作压力
 */
    @Property(column = "pressure")
    private String pressure;

    /** 存储温度
 */
    @Property(column = "temperature")
    private String temperature;

    /** 存储介质
 */
    @Property(column = "storage_media_json")
    private String storageMediaJson;

    @Transient
    private List<PropertyDes> pdListDetail = new ArrayList<PropertyDes>();
    @Transient
    private PropertyDes namePropertyDes;

    public PartitionStorageDBInfo() {
        timeStationArrive = 0;
        capacity = 0d;
        diameter = 0d;
        height = 0d;
        perimeter = 0d;
        topArea = 0d;
    }

    public List<PropertyDes> getPdListDetail() {
        pdListDetail.clear();

        pdListDetail.add(new PropertyDes("储罐", uuid, PropertyDes.TYPE_NONE, PartitionStorageDBInfo.class));
        namePropertyDes = new PropertyDes("名称*", "setName", String.class, name, PropertyDes.TYPE_EDIT, true);
        pdListDetail.add(namePropertyDes);
//        pdListDetail.add(new PropertyDes("毗邻情况(东)", "setAdjacentEast", String.class, adjacentEast, PropertyDes.TYPE_EDIT));
//        pdListDetail.add(new PropertyDes("毗邻情况(西)", "setAdjacentWest", String.class, adjacentWest, PropertyDes.TYPE_EDIT));
//        pdListDetail.add(new PropertyDes("毗邻情况(南)", "setAdjacentSouth", String.class, adjacentSouth, PropertyDes.TYPE_EDIT));
//        pdListDetail.add(new PropertyDes("毗邻情况(北)", "setAdjacentNorth", String.class, adjacentNorth, PropertyDes.TYPE_EDIT));
//        pdListDetail.add(new PropertyDes("辖区中队行车路线", "setRouteFromStation", String.class, routeFromStation,PropertyDes.TYPE_EDIT));
//        pdListDetail.add(new PropertyDes("预计到达时长", "setTimeStationArrive", Integer.class, timeStationArrive, PropertyDes.TYPE_EDIT));

        pdListDetail.add(new PropertyDes("储罐类型", "setType", String.class, type, LvApplication.getInstance().getCompDicMap().get("ZDDW_CGLX")));
        pdListDetail.add(new PropertyDes("储罐容量", "setCapacity", Double.class, capacity, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("直径(m)", "setDiameter", Double.class, diameter, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("高度(m)", "setHeight", Double.class, height, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("周长(m)", "setPerimeter", Double.class, perimeter, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("罐顶面积(㎡)", "setTopArea", Double.class, topArea, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("工作压力(Pa)", "setPressure", String.class, pressure, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("存储温度(℃)", "setTemperature", String.class, temperature, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes(Constant.KEY_UNIT_JSON_TITLE_CCJZ, "setStorageMediaJson", String.class, storageMediaJson, PropertyDes.TYPE_PART_LIST));

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
     * 获取储罐类型

     * 
     * @return 储罐类型

     */
    public String getType() {
        return this.type;
    }

    /**
     * 设置储罐类型

     * 
     * @param type
     *          储罐类型

     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取容量（m³）

     * 
     * @return 容量（m³）

     */
    public Double getCapacity() {
        return this.capacity;
    }

    /**
     * 设置容量（m³）

     * 
     * @param capacity
     *          容量（m³）

     */
    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    /**
     * 获取直径（m）

     * 
     * @return 直径（m）

     */
    public Double getDiameter() {
        return this.diameter;
    }

    /**
     * 设置直径（m）

     * 
     * @param diameter
     *          直径（m）

     */
    public void setDiameter(Double diameter) {
        this.diameter = diameter;
    }

    /**
     * 获取高度（m）

     * 
     * @return 高度（m）

     */
    public Double getHeight() {
        return this.height;
    }

    /**
     * 设置高度（m）

     * 
     * @param height
     *          高度（m）

     */
    public void setHeight(Double height) {
        this.height = height;
    }

    /**
     * 获取周长（m）

     * 
     * @return 周长（m）

     */
    public Double getPerimeter() {
        return this.perimeter;
    }

    /**
     * 设置周长（m）

     * 
     * @param perimeter
     *          周长（m）

     */
    public void setPerimeter(Double perimeter) {
        this.perimeter = perimeter;
    }

    /**
     * 获取罐顶面积（㎡）

     * 
     * @return 罐顶面积（㎡）

     */
    public Double getTopArea() {
        return this.topArea;
    }

    /**
     * 设置罐顶面积（㎡）

     * 
     * @param topArea
     *          罐顶面积（㎡）

     */
    public void setTopArea(Double topArea) {
        this.topArea = topArea;
    }

    /**
     * 获取工作压力

     * 
     * @return 工作压力

     */
    public String getPressure() {
        return this.pressure;
    }

    /**
     * 设置工作压力

     * 
     * @param pressure
     *          工作压力

     */
    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    /**
     * 获取存储温度

     * 
     * @return 存储温度

     */
    public String getTemperature() {
        return this.temperature;
    }

    /**
     * 设置存储温度

     * 
     * @param temperature
     *          存储温度

     */
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    /**
     * 获取存储介质

     * 
     * @return 存储介质

     */
    public String getStorageMediaJson() {
        return this.storageMediaJson;
    }

    /**
     * 设置存储介质

     * 
     * @param storageMediaJson
     *          存储介质

     */
    public void setStorageMediaJson(String storageMediaJson) {
        this.storageMediaJson = storageMediaJson;
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