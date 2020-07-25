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
 * partition_storage_zone
 * 
 * @author wangx
 * @version 1.0.0 2018-05-22
 */
@Table(name = "partition_storage_zone")
public class PartitionStorageZoneDBInfo implements IPartitionInfo {
    /** 版本号 */
    @Transient
    private static final long serialVersionUID = -7730970530387689336L;

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

    /** structureType */
    @Property(column = "structure_type")
    private String structureType;

    /** 总容积（m³）
 */
    @Property(column = "total_volume")
    private Double totalVolume;

    /** 储罐数量
 */
    @Property(column = "storage_count")
    private Integer storageCount;

    /** 存储介质
 */
    @Property(column = "storage_media")
    private String storageMedia;

    /** 储罐间隔
 */
    @Property(column = "spacing")
    private Double spacing;

    /** 技术人员
 */
    @Property(column = "technical_person")
    private String technicalPerson;

    /** 技术人员联系方式
 */
    @Property(column = "technical_person_contact")
    private String technicalPersonContact;

    @Transient
    private List<PropertyDes> pdListDetail = new ArrayList<PropertyDes>();
    @Transient
    private PropertyDes namePropertyDes;

    public PartitionStorageZoneDBInfo() {
        timeStationArrive = 0;
        storageCount = 0;
        spacing = 0d;
    }

    public List<PropertyDes> getPdListDetail() {
        pdListDetail.clear();

        pdListDetail.add(new PropertyDes("罐组", uuid, PropertyDes.TYPE_NONE, PartitionStorageZoneDBInfo.class));
        namePropertyDes = new PropertyDes("罐组名称*", "setName", String.class, name, PropertyDes.TYPE_EDIT, true);
        pdListDetail.add(namePropertyDes);
        pdListDetail.add(new PropertyDes("位置", "setAddress", String.class, address, PropertyDes.TYPE_EDIT));

        pdListDetail.add(new PropertyDes("罐组总容积(m³)", "setTotalVolume", Double.class, totalVolume, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("罐组储罐数量", "setStorageCount", Integer.class, storageCount, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("存储介质描述", "setStorageMedia", String.class, storageMedia, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("储罐间距(m)", "setSpacing", Double.class, spacing, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("技术负责人", "setTechnicalPerson", String.class, technicalPerson, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("技术人员联系方式", "setTechnicalPersonContact", String.class, technicalPersonContact, PropertyDes.TYPE_EDIT));

        pdListDetail.add(new PropertyDes("毗邻情况(东)", "setAdjacentEast", String.class, adjacentEast, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("毗邻情况(西)", "setAdjacentWest", String.class, adjacentWest, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("毗邻情况(南)", "setAdjacentSouth", String.class, adjacentSouth, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("毗邻情况(北)", "setAdjacentNorth", String.class, adjacentNorth, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("行车路线", "setRouteFromStation", String.class, routeFromStation,PropertyDes.TYPE_EDIT));
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
     * 获取structureType
     * 
     * @return structureType
     */
    public String getStructureType() {
        return this.structureType;
    }

    /**
     * 设置structureType
     * 
     * @param structureType
     */
    public void setStructureType(String structureType) {
        this.structureType = structureType;
    }

    /**
     * 获取总容积（m³）

     * 
     * @return 总容积（m³）

     */
    public Double getTotalVolume() {
        return this.totalVolume;
    }

    /**
     * 设置总容积（m³）

     * 
     * @param totalVolume
     *          总容积（m³）

     */
    public void setTotalVolume(Double totalVolume) {
        this.totalVolume = totalVolume;
    }

    /**
     * 获取储罐数量

     * 
     * @return 储罐数量

     */
    public Integer getStorageCount() {
        return this.storageCount;
    }

    /**
     * 设置储罐数量

     * 
     * @param storageCount
     *          储罐数量

     */
    public void setStorageCount(Integer storageCount) {
        this.storageCount = storageCount;
    }

    /**
     * 获取存储介质

     * 
     * @return 存储介质

     */
    public String getStorageMedia() {
        return this.storageMedia;
    }

    /**
     * 设置存储介质

     * 
     * @param storageMedia
     *          存储介质

     */
    public void setStorageMedia(String storageMedia) {
        this.storageMedia = storageMedia;
    }

    /**
     * 获取储罐间隔

     * 
     * @return 储罐间隔

     */
    public Double getSpacing() {
        return this.spacing;
    }

    /**
     * 设置储罐间隔

     * 
     * @param spacing
     *          储罐间隔

     */
    public void setSpacing(Double spacing) {
        this.spacing = spacing;
    }

    /**
     * 获取技术人员

     * 
     * @return 技术人员

     */
    public String getTechnicalPerson() {
        return this.technicalPerson;
    }

    /**
     * 设置技术人员

     * 
     * @param technicalPerson
     *          技术人员

     */
    public void setTechnicalPerson(String technicalPerson) {
        this.technicalPerson = technicalPerson;
    }

    /**
     * 获取技术人员联系方式

     * 
     * @return 技术人员联系方式

     */
    public String getTechnicalPersonContact() {
        return this.technicalPersonContact;
    }

    /**
     * 设置技术人员联系方式

     * 
     * @param technicalPersonContact
     *          技术人员联系方式

     */
    public void setTechnicalPersonContact(String technicalPersonContact) {
        this.technicalPersonContact = technicalPersonContact;
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