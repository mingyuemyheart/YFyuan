package com.moft.xfapply.model.entity;

import com.moft.xfapply.app.LvApplication;
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
 * fire_facility_auto_sprinkler_system
 *
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
@Table(name = "fire_facility_auto_sprinkler_system")
public class FireFacilityAutoSprinklerSystemDBInfo implements IFireFacilityInfo {
    /** 版本号 */
    @Transient
    private static final long serialVersionUID = 4244237635934145430L;

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
    private String address;

    /** 位置
 */
    private String location;

    /** 描述
 */
    private String description;


    /** 是否可用
     */
    private Boolean usable;

    @Transient
    private List<PropertyDes> pdListDetail = new ArrayList<PropertyDes>();

    public FireFacilityAutoSprinklerSystemDBInfo() {
        count = 1;
        usable = true;
    }

    public List<PropertyDes> getPdListDetail() {
        pdListDetail.clear();

        pdListDetail.add(new PropertyDes("自动喷淋系统", uuid, PropertyDes.TYPE_NONE, FireFacilityAutoSprinklerSystemDBInfo.class));
        pdListDetail.add(new PropertyDes("名称*", "setName", String.class, name, PropertyDes.TYPE_EDIT, true));
        pdListDetail.add(new PropertyDes("描述", "setDescription", String.class, description, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("是否可用", "setUsable", Boolean.class, usable, LvApplication.getInstance().getUsableDicList()));
        pdListDetail.add(new PropertyDes("平面位置", "setLocation", String.class, location, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("位置", "setAddress", String.class, address, PropertyDes.TYPE_EDIT));

        return pdListDetail;
    }

    public void setAdapter(IGeomElementInfo info) {
        if(StringUtil.isEmpty(departmentUuid)) {
            departmentUuid = info.getDepartmentUuid();
        }
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getDepartmentUuid() {
        return departmentUuid;
    }

    @Override
    public void setDepartmentUuid(String departmentUuid) {
        this.departmentUuid = departmentUuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPartitionUuid() {
        return partitionUuid;
    }

    @Override
    public void setPartitionUuid(String partitionUuid) {
        this.partitionUuid = partitionUuid;
    }

    @Override
    public String getFacilityType() {
        return facilityType;
    }

    @Override
    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    @Override
    public String getGeometryText() {
        return geometryText;
    }

    @Override
    public void setGeometryText(String geometryText) {
        this.geometryText = geometryText;
    }

    @Override
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
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
    public Integer getCount() {
        return count;
    }

    @Override
    public void setCount(Integer count) {
        this.count = count;
    }

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String getCreatePerson() {
        return createPerson;
    }

    @Override
    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    @Override
    public String getUpdatePerson() {
        return updatePerson;
    }

    @Override
    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public Date getUpdateDate() {
        return updateDate;
    }

    @Override
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String getBelongtoGroup() {
        return belongtoGroup;
    }

    @Override
    public void setBelongtoGroup(String belongtoGroup) {
        this.belongtoGroup = belongtoGroup;
    }

    @Override
    public String getPlanDiagram() {
        return planDiagram;
    }

    @Override
    public void setPlanDiagram(String planDiagram) {
        this.planDiagram = planDiagram;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getUsable() {
        return usable;
    }

    public void setUsable(Boolean usable) {
        this.usable = usable;
    }
}