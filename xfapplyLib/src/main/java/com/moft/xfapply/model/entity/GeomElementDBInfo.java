package com.moft.xfapply.model.entity;

import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.model.base.IGeomElementInfo;
import com.moft.xfapply.model.common.EleCondition;
import com.moft.xfapply.model.common.PrimaryAttribute;
import com.moft.xfapply.model.common.PropertyDes;
import com.moft.xfapply.utils.CoordinateUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.Utils;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Property;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Table(name = "geometry_element")
public class GeomElementDBInfo implements IGeomElementInfo {
    /**
     * @Fields serialVersionUID :
     */
    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    private String uuid; // 唯一标识
    private String code; // 编码
    @Property(column = "department_uuid")
    private String departmentUuid; // 机构标识
    private String name; // 名称
    @Property(column = "ele_type")
    private String eleType; // 分类
    private String address; // 地址
    @Property(column = "geometry_text")
    private String geometryText; // 中心位置Json文本
    @Property(column = "longitude")
    private String longitude; // 百度精度
    @Property(column = "latitude")
    private String latitude; // 百度纬度
    @Property(column = "geometry_type")
    private String geometryType;//位置类型
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
    @Property(column = "data_quality")
    private Double dataQuality;

    @Property(column = "primary_value1")
    private String primaryValue1;
    @Property(column = "primary_value2")
    private String primaryValue2;
    @Property(column = "primary_value3")
    private String primaryValue3;
    @Property(column = "primary_value4")
    private String primaryValue4; // ID:900352【物质器材灭火剂】追加车辆信息显示。 存放车载灭火剂和车载器材的所属车辆的名字
    private String keywords; //关键字查询

    @Transient
    private String subType;

    @Override
    public List<PropertyDes> getPdListDetail() {
        return new ArrayList<>();
    }

    @Override
    public String getOutline() {
        String outline = "";

        return outline;
    }

    public void setAdapter() {

    }

    public PrimaryAttribute getPrimaryValues() {
        PrimaryAttribute attribute = new PrimaryAttribute();
        if(StringUtil.isEmpty(primaryValue1) &&
                StringUtil.isEmpty(primaryValue2) &&
                StringUtil.isEmpty(primaryValue3) &&
                StringUtil.isEmpty(primaryValue4)) {
            Map<String, Class<?>> classMap = GeomEleBussiness.getInstance().getElementClsMap();
            if (classMap.containsKey(eleType)) {
                EleCondition condition = new EleCondition();
                condition.setUuid(uuid);
                condition.setType(eleType);
                condition.setCityCode(belongtoGroup);
                condition.setHistory(false);
                IGeomElementInfo info = GeomEleBussiness.getInstance().getSpecGeomEleInfoByUuid(condition);

                if(info != null) {
                    attribute = info.getPrimaryValues();
                    primaryValue1 = attribute.getPrimaryValue1();
                    primaryValue2 = attribute.getPrimaryValue2();
                    primaryValue3 = attribute.getPrimaryValue3();
                    primaryValue4 = attribute.getPrimaryValue4();
                    GeomEleBussiness.getInstance().updateCommonGeomEleInfo(this);
                }
            }
        } else {
            attribute.setPrimaryValue1(primaryValue1);
            attribute.setPrimaryValue2(primaryValue2);
            attribute.setPrimaryValue3(primaryValue3);
            attribute.setPrimaryValue4(primaryValue4);
        }
        return  attribute;
    }

    public double getDistance(double lng, double lat) {
        double dis = -1;
        if (!StringUtil.isEmpty(getLatitude()) && !StringUtil.isEmpty(getLongitude())) {
            try {
                double geoLat = Double.valueOf(getLatitude());
                double geoLng = Double.valueOf(getLongitude());

                dis = CoordinateUtil.getInstance().getDisTwo(
                        geoLng, geoLat, lng, lat);

            } catch (NumberFormatException ex) {
                dis  = -1;
            }
        } else {
            dis  = -1;
        }
        return dis;
    }

    public boolean isGeoPosValid() {
        if (StringUtil.isEmpty(getLongitude()) || StringUtil.isEmpty(getLatitude())) {
            return false;
        }

        Double latD = Utils.convertToDouble(getLatitude());
        Double lngD = Utils.convertToDouble(getLongitude());
        if (latD == null || lngD == null) {
            return false;
        }

        if (latD.intValue() == 0 || lngD.intValue() == 0) {
            return false;
        }

        return true;
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
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
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
    public String getEleType() {
        return eleType;
    }

    @Override
    public void setEleType(String eleType) {
        this.eleType = eleType;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public Integer getVersion() {
        return null;
    }

    @Override
    public void setVersion(Integer version) {

    }

    public String getCreatePerson() {
        return createPerson;
    }

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

    public Double getDataQuality() {
        return dataQuality;
    }

    public void setDataQuality(Double dataQuality) {
        this.dataQuality = dataQuality;
    }

    public String getPrimaryValue1() {
        return primaryValue1;
    }

    public void setPrimaryValue1(String primaryValue1) {
        this.primaryValue1 = primaryValue1;
    }

    public String getPrimaryValue2() {
        return primaryValue2;
    }

    public void setPrimaryValue2(String primaryValue2) {
        this.primaryValue2 = primaryValue2;
    }

    public String getPrimaryValue3() {
        return primaryValue3;
    }

    public void setPrimaryValue3(String primaryValue3) {
        this.primaryValue3 = primaryValue3;
    }

    public String getPrimaryValue4() {
        return primaryValue4;
    }

    public void setPrimaryValue4(String primaryValue4) {
        this.primaryValue4 = primaryValue4;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    //对应其他消防队伍分为4类
    public String getResEleType() {
        String retType = eleType;
        if(!StringUtil.isEmpty(subType)) {
            retType += subType;
        }
        return retType;
    }
}
