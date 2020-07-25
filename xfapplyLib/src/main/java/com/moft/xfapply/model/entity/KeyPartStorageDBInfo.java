package com.moft.xfapply.model.entity;

import com.moft.xfapply.model.base.IGeomElementInfo;
import com.moft.xfapply.model.base.IKeyPartInfo;
import com.moft.xfapply.model.common.PropertyDes;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.utils.StringUtil;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Property;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * key_part_storage
 * 
 * @author wangx
 * @version 1.0.0 2018-05-22
 */
@Table(name = "key_part_storage")
public class KeyPartStorageDBInfo implements IKeyPartInfo {
    /** 版本号 */
    @Transient
    private static final long serialVersionUID = -1662253544196464076L;

    @Id
    private String uuid;
    @Property(column = "department_uuid")
    private String departmentUuid; // 机构标识
    private String name; // 名称
    @Property(column = "partition_uuid")
    private String partitionUuid; // 所属分区标识
    @Property(column = "key_part_type")
    private String keyPartType; //类型
    @Property(column = "geometry_text")
    private String geometryText;
    @Property(column = "longitude")
    private String longitude;
    @Property(column = "latitude")
    private String latitude;
    @Property(column = "geometry_type")
    private String geometryType;//位置类型
    private String address;
    private String location; // 位置
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
    private String remark; // 备注
    @Property(column = "belongto_group")
    private String  belongtoGroup; //城市

    /** 所在平面图
     */
    @Property(column = "plan_diagram")
    private String planDiagram;
    /** 危险性分析
     */
    @Property(column = "risk_analysis")
    private String riskAnalysis;
    /** 注意事项
     */
    @Property(column = "attention")
    private String attention;

    @Transient
    private List<PropertyDes> pdListDetail = new ArrayList<PropertyDes>();

    public KeyPartStorageDBInfo() {

    }

    public List<PropertyDes> getPdListDetail() {
        pdListDetail.clear();

        pdListDetail.add(new PropertyDes("重点部位-储罐类", uuid, PropertyDes.TYPE_NONE, KeyPartStorageDBInfo.class));
        pdListDetail.add(new PropertyDes("名称*", "setName", String.class, name, PropertyDes.TYPE_EDIT, true));

        pdListDetail.add(new PropertyDes("危险性分析", "setRiskAnalysis", String.class, riskAnalysis, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("注意事项", "setAttention", String.class, attention, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("平面位置", "setLocation", String.class, location, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("位置", "setAddress", String.class, address, PropertyDes.TYPE_EDIT));

        pdListDetail.add(new PropertyDes("备注", "setRemark", String.class, remark, PropertyDes.TYPE_EDIT));

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

    public String getDepartmentUuid() {
        return departmentUuid;
    }

    public void setDepartmentUuid(String departmentUuid) {
        this.departmentUuid = departmentUuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartitionUuid() {
        return partitionUuid;
    }

    public void setPartitionUuid(String partitionUuid) {
        this.partitionUuid = partitionUuid;
    }

    public String getKeyPartType() {
        return keyPartType;
    }

    public void setKeyPartType(String keyPartType) {
        this.keyPartType = keyPartType;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDataJson() {
        return dataJson;
    }

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

    public String getRiskAnalysis() {
        return riskAnalysis;
    }

    public void setRiskAnalysis(String riskAnalysis) {
        this.riskAnalysis = riskAnalysis;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getPlanDiagram() {
        return planDiagram;
    }

    public void setPlanDiagram(String planDiagram) {
        this.planDiagram = planDiagram;
    }
}