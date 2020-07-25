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
 * 其他灭火系统-干粉灭火系统(fire_facility_extinguishing_powder)
 * 
 * @author wangx
 * @version 1.0.0 2018-05-22
 */
@Table(name = "fire_facility_extinguishing_powder")
public class FireFacilityExtinguishingPowderDBInfo implements IFireFacilityInfo {
    /** 版本号 */
    @Transient
    private static final long serialVersionUID = -9058713166245747654L;

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

    private String address;
    /** 所在平面图
     */
    @Property(column = "plan_diagram")
    private String planDiagram;
    /** 启闭位置
 */
    @Property(column = "onoff_location")
    private String onoffLocation;
    /** 作用范围
 */
    @Property(column = "action_scope")
    private String actionScope;


    @Transient
    private List<PropertyDes> pdListDetail = new ArrayList<PropertyDes>();

    public FireFacilityExtinguishingPowderDBInfo() {
        count = 1;
    }

    public List<PropertyDes> getPdListDetail() {
        pdListDetail.clear();

        pdListDetail.add(new PropertyDes("干粉灭火系统", uuid, PropertyDes.TYPE_NONE, FireFacilityExtinguishingPowderDBInfo.class));
        pdListDetail.add(new PropertyDes("名称*", "setName", String.class, name, PropertyDes.TYPE_EDIT, true));
        pdListDetail.add(new PropertyDes("启闭位置", "setOnoffLocation", String.class, onoffLocation, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("作用范围", "setActionScope", String.class, actionScope, PropertyDes.TYPE_EDIT));
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

    @Override
    public String getLocation() {
        return null;
    }

    @Override
    public void setLocation(String location) {

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取启闭位置

     * 
     * @return 启闭位置

     */
    public String getOnoffLocation() {
        return this.onoffLocation;
    }

    /**
     * 设置启闭位置

     * 
     * @param onoffLocation
     *          启闭位置

     */
    public void setOnoffLocation(String onoffLocation) {
        this.onoffLocation = onoffLocation;
    }

    /**
     * 获取作用范围

     * 
     * @return 作用范围

     */
    public String getActionScope() {
        return this.actionScope;
    }

    /**
     * 设置作用范围

     * 
     * @param actionScope
     *          作用范围

     */
    public void setActionScope(String actionScope) {
        this.actionScope = actionScope;
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