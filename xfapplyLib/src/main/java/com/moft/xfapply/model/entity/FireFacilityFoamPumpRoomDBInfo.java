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
 * 泡沫系统-泵房(fire_facility_foam_pump_room)
 * 
 * @author wangx
 * @version 1.0.0 2018-05-23
 */
@Table(name = "fire_facility_foam_pump_room")
public class FireFacilityFoamPumpRoomDBInfo implements IFireFacilityInfo {
    /** 版本号 */
    @Transient
    private static final long serialVersionUID = 7380192175924829149L;

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

    /** 所属建（构）筑 */
    @Property(column = "structure_name")
    private String structureName;
    private String address;
    /** 所在平面图
     */
    @Property(column = "plan_diagram")
    private String planDiagram;
    /** 位置 */
    @Property(column = "location")
    private String location;
    /** 泡沫液类型 */
    @Property(column = "foam_type")
    private String foamType;
    /** 泡沫液储量（吨） */
    @Property(column = "foam_storage")
    private Double foamStorage;
    /** 泡沫泵最大流量（L/s） */
    @Property(column = "pump_maximum_flow")
    private Double pumpMaximumFlow;


    @Transient
    private List<PropertyDes> pdListDetail = new ArrayList<PropertyDes>();

    public FireFacilityFoamPumpRoomDBInfo() {
        count = 1;
        foamStorage = 0d;
        pumpMaximumFlow = 0d;
    }

    public List<PropertyDes> getPdListDetail() {
        pdListDetail.clear();

        pdListDetail.add(new PropertyDes("泡沫泵房", uuid, PropertyDes.TYPE_NONE, FireFacilityFoamPumpRoomDBInfo.class));
        pdListDetail.add(new PropertyDes("名称*", "setName", String.class, name, PropertyDes.TYPE_EDIT, true));
        pdListDetail.add(new PropertyDes("泡沫液类型", "setFoamType", String.class, foamType, LvApplication.getInstance().getCompDicMap().get("MHYJ")));
        pdListDetail.add(new PropertyDes("泡沫液储量(吨)", "setFoamStorage", Double.class, foamStorage, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("泡沫泵最大流量(L/s)", "setPumpMaximumFlow", Double.class, pumpMaximumFlow, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("平面位置", "setLocation", String.class, location, PropertyDes.TYPE_EDIT));
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
     * 获取所属建（构）筑
     * 
     * @return 所属建（构）筑
     */
    public String getStructureName() {
        return this.structureName;
    }

    /**
     * 设置所属建（构）筑
     * 
     * @param structureName
     *          所属建（构）筑
     */
    public void setStructureName(String structureName) {
        this.structureName = structureName;
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
     * 获取泡沫液类型
     * 
     * @return 泡沫液类型
     */
    public String getFoamType() {
        return this.foamType;
    }

    /**
     * 设置泡沫液类型
     * 
     * @param foamType
     *          泡沫液类型
     */
    public void setFoamType(String foamType) {
        this.foamType = foamType;
    }

    /**
     * 获取泡沫液储量（吨）
     * 
     * @return 泡沫液储量（吨）
     */
    public Double getFoamStorage() {
        return this.foamStorage;
    }

    /**
     * 设置泡沫液储量（吨）
     * 
     * @param foamStorage
     *          泡沫液储量（吨）
     */
    public void setFoamStorage(Double foamStorage) {
        this.foamStorage = foamStorage;
    }

    /**
     * 获取泡沫泵最大流量（L/s）
     * 
     * @return 泡沫泵最大流量（L/s）
     */
    public Double getPumpMaximumFlow() {
        return this.pumpMaximumFlow;
    }

    /**
     * 设置泡沫泵最大流量（L/s）
     * 
     * @param pumpMaximumFlow
     *          泡沫泵最大流量（L/s）
     */
    public void setPumpMaximumFlow(Double pumpMaximumFlow) {
        this.pumpMaximumFlow = pumpMaximumFlow;
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

    public String getBelongtoGroup() {
        return belongtoGroup;
    }

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