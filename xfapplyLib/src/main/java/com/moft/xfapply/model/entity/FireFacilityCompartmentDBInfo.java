package com.moft.xfapply.model.entity;

import com.moft.xfapply.model.base.IFireFacilityInfo;
import com.moft.xfapply.model.base.IGeomElementInfo;
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
 * 防火分区-防火分区(fire_facility_compartment)
 * 
 * @author wangx
 * @version 1.0.0 2018-05-22
 */
@Table(name = "fire_facility_compartment")
public class FireFacilityCompartmentDBInfo implements IFireFacilityInfo {
    /** 版本号 */
    @Transient
    private static final long serialVersionUID = -3833422235798823723L;

    @Id
    private String uuid;
    @Property(column = "department_uuid")
    private String departmentUuid; // 机构标识
    private String name; // 名称
    @Property(column = "partition_uuid")
    private String partitionUuid; // 所属分区标识
    @Property(column = "facility_type")
    private String facilityType; // 设施类型
    @Property(column = "geometry_text")
    private String geometryText;
    @Property(column = "longitude")
    private String longitude;
    @Property(column = "latitude")
    private String latitude;
    @Property(column = "geometry_type")
    private String geometryType;//位置类型
    private Integer count; //数量
    @Property(column = "data_json")
    private String dataJson; //JSON化数据
    private Integer version; // 数据版本
    private Boolean deleted; // 删除标识
    @Property(column = "create_person")
    private String createPerson; // 提交人
    @Property(column = "update_person")
    private String updatePerson; // 最新更新者
    @Property(column = "create_date")
    private Date createDate; // 创建时间
    @Property(column = "update_date")
    private Date updateDate; // 更新时间
    private String remark; //备注
    @Property(column = "belongto_group")
    private String  belongtoGroup; //城市

    /** 所在平面图
     */
    @Property(column = "plan_diagram")
    private String planDiagram;
    /** 区域面积
 */
    private Double area;
    private String address;
    /** 位置
 */
    private String location;
    /** 分隔设施
 */
    @Property(column = "separate_facility")
    private String separateFacility;
    /** 分隔位置
 */
    @Property(column = "separate_location")
    private String separateLocation;



    @Transient
    private List<PropertyDes> pdListDetail = new ArrayList<PropertyDes>();

    public FireFacilityCompartmentDBInfo() {
        count = 1;
        area = 0d;
    }

    public List<PropertyDes> getPdListDetail() {
        pdListDetail.clear();

        pdListDetail.add(new PropertyDes("防火分区(层)", uuid, PropertyDes.TYPE_NONE, FireFacilityCompartmentDBInfo.class));
        pdListDetail.add(new PropertyDes("名称*", "setName", String.class, name, PropertyDes.TYPE_EDIT, true));
        pdListDetail.add(new PropertyDes("区域面积", "setArea", Double.class, area, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("区域位置", "setLocation", String.class, location, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("分隔设施", "setSeparateFacility", String.class, separateFacility, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("分隔位置", "setSeparateLocation", String.class, separateLocation, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("位置", "setAddress", String.class, address, PropertyDes.TYPE_EDIT));

        return pdListDetail;
    }

    public void setAdapter(IGeomElementInfo info) {
        if(StringUtil.isEmpty(departmentUuid)) {
            departmentUuid = info.getDepartmentUuid();
        }
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
     * 获取partitionUuid
     * 
     * @return partitionUuid
     */
    public String getPartitionUuid() {
        return this.partitionUuid;
    }

    /**
     * 设置partitionUuid
     * 
     * @param partitionUuid
     */
    public void setPartitionUuid(String partitionUuid) {
        this.partitionUuid = partitionUuid;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public String getGeometryText() {
        return geometryText;
    }

    public void setGeometryText(String geometryText) {
        this.geometryText = geometryText;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

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
     * 获取count
     * 
     * @return count
     */
    public Integer getCount() {
        return this.count;
    }

    /**
     * 设置count
     * 
     * @param count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 获取dataJson
     * 
     * @return dataJson
     */
    public String getDataJson() {
        return this.dataJson;
    }

    /**
     * 设置dataJson
     * 
     * @param dataJson
     */
    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

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
     * 获取区域面积

     * 
     * @return 区域面积

     */
    public Double getArea() {
        return this.area;
    }

    /**
     * 设置区域面积

     * 
     * @param area
     *          区域面积

     */
    public void setArea(Double area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取位置

     * 
     * @return 位置

     */
    public String getLocation() {
        return this.location;
    }

    /**
     * 设置位置

     * 
     * @param location
     *          位置

     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * 获取分隔设施

     * 
     * @return 分隔设施

     */
    public String getSeparateFacility() {
        return this.separateFacility;
    }

    /**
     * 设置分隔设施

     * 
     * @param separateFacility
     *          分隔设施

     */
    public void setSeparateFacility(String separateFacility) {
        this.separateFacility = separateFacility;
    }

    /**
     * 获取分隔位置

     * 
     * @return 分隔位置

     */
    public String getSeparateLocation() {
        return this.separateLocation;
    }

    /**
     * 设置分隔位置

     * 
     * @param separateLocation
     *          分隔位置

     */
    public void setSeparateLocation(String separateLocation) {
        this.separateLocation = separateLocation;
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

    public String getPlanDiagram() {
        return planDiagram;
    }

    public void setPlanDiagram(String planDiagram) {
        this.planDiagram = planDiagram;
    }
}