package com.moft.xfapply.model.entity;

import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.base.IGeomElementInfo;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.common.PrimaryAttribute;
import com.moft.xfapply.model.common.PropertyConditon;
import com.moft.xfapply.model.common.PropertyDes;
import com.moft.xfapply.utils.DictionaryUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.Utils;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Property;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * station_battalion
 * 
 * @author wangx
 * @version 1.0.0 2018-05-22
 */
@Table(name = "enterprise_fource")
public class EnterpriseFourceDBInfo implements IGeomElementInfo {
    /** 版本号 */
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
    private String keywords; //关键字查询

    @Property(column = "type")
    private String type;
    @Property(column = "key_unit_uuid")
    private String keyUnitUuid;
    @Property(column = "key_unit_name")
    private String keyUnitName;
    @Property(column = "unit")
    private String unit;
    @Property(column = "road")
    private String road;
    @Property(column = "no")
    private String no;
    @Property(column = "total_num")
    private Integer totalNum;
    @Property(column = "duty_num")
    private Integer dutyNum;
    @Property(column = "duty_vehicle")
    private Integer dutyVehicle;
    @Property(column = "extinguishing_dose")
    private Double extinguishingDose;
    @Property(column = "duty_tel")
    private String dutyTel;
    @Property(column = "fax")
    private String fax;
    @Property(column = "header_name")
    private String headerName;
    @Property(column = "header_tel")
    private String headerTel;
    @Property(column = "unit_tel")
    private String unitTel;
    @Property(column = "version")
    private Integer version;



    @Transient
    private List<PropertyDes> pdListDetail = new ArrayList<PropertyDes>();

    /**构造函数**/
    public EnterpriseFourceDBInfo() {
        totalNum = 0;
        dutyNum = 0;
        dutyVehicle = 0;
    }

    /**文言**/
    public List<PropertyDes> getPdListDetail() {
        pdListDetail.clear();


        pdListDetail.add(new PropertyDes("队伍编码", "setCode", String.class, code, PropertyDes.TYPE_TEXT));
        pdListDetail.add(new PropertyDes("队伍名称*", "setName", String.class, name, PropertyDes.TYPE_EDIT, true));

        pdListDetail.add(new PropertyDes("队伍类型*", "setType", String.class, type, LvApplication.getInstance().getCompDicMap().get("XFLL_DWLX"), true, true));
        pdListDetail.add(new PropertyDes("管辖单位", "setUnit", String.class, unit, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("管辖单位联系方式", "setUnitTel", String.class, unitTel, PropertyDes.TYPE_EDIT));
//        pdListDetail.add(new PropertyDes("所属单位", "setKeyUnitName", String.class, keyUnitName, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("地址", "setAddress", String.class, address, PropertyDes.TYPE_EDIT));
//        pdListDetail.add(new PropertyDes("路/街", "setRoad", String.class, road, PropertyDes.TYPE_EDIT));
//        pdListDetail.add(new PropertyDes("号", "setNo", String.class, no, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("消防队员总人数", "setTotalNum", Integer.class, totalNum, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("每日执勤人数", "setDutyNum", Integer.class, dutyNum, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("执勤车辆数(台)", "setDutyVehicle", Integer.class, dutyVehicle, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("灭火剂总量(t)", "setExtinguishingDose", Double.class, extinguishingDose, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("值班电话", "setDutyTel", String.class, dutyTel, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("传真", "setFax", String.class, fax, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("队长姓名", "setHeaderName", String.class, headerName, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("队长联系方式", "setHeaderTel", String.class, headerTel, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("备注", "setRemark", String.class, remark, PropertyDes.TYPE_EDIT));

        return pdListDetail;
    }

    @Override
    public String getOutline() {
        String outline = "";

        outline += "值班电话：<font color=#0074C7>" + StringUtil.get(dutyTel) +
                "</font>&nbsp;&nbsp;车辆数(台)：<font color=#0074C7>" + StringUtil.get(dutyVehicle) + "</font>" + Constant.OUTLINE_DIVIDER;
        outline += "队长及联系方式：<font color=#0074C7>" + StringUtil.get(headerName) + StringUtil.get(headerTel) + "</font>" + Constant.OUTLINE_DIVIDER;
        outline += "总人数：<font color=#0074C7>" + StringUtil.get(totalNum) +
                "</font>&nbsp;&nbsp;每日执勤人数：<font color=#0074C7>" + StringUtil.get(dutyNum) + "</font>" + Constant.OUTLINE_DIVIDER;
        outline += "灭火剂总量(t)：<font color=#0074C7>" + StringUtil.get(extinguishingDose) + "</font>" + Constant.OUTLINE_DIVIDER;
        outline += "&" + "值班电话：" + StringUtil.get(dutyTel) +  "&"
                + "队长(" + StringUtil.get(headerName) + ")：" + StringUtil.get(headerTel) + "&"
                + Constant.OUTLINE_DIVIDER;

        return outline;
    }

    @Override
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

    @Transient
    private static List<PropertyConditon> pdListDetailForFilter = new ArrayList<PropertyConditon>();
    public static List<PropertyConditon> getPdListDetailForFilter() {
        pdListDetailForFilter.clear();
        pdListDetailForFilter.add(new PropertyConditon("消防队员总人数", "enterprise_fource", "total_num", Integer.class, PropertyConditon.TYPE_EDIT_NUMBER));
        pdListDetailForFilter.add(new PropertyConditon("每日执勤人数", "enterprise_fource", "duty_num", Integer.class, PropertyConditon.TYPE_EDIT_NUMBER));
        pdListDetailForFilter.add(new PropertyConditon("执勤车辆(台)", "enterprise_fource", "duty_vehicle", Integer.class, PropertyConditon.TYPE_EDIT_NUMBER));
        return pdListDetailForFilter;
    }

    public void setAdapter() {
        if(StringUtil.isEmpty(departmentUuid)) {
            PrefUserInfo pui = PrefUserInfo.getInstance();
            String ownerDepCode = pui.getUserInfo("department_code");
            if(ownerDepCode != null && ownerDepCode.length() >= 4) {
                String detachmentCode = ownerDepCode.substring(0, 4) + "0000";
                Dictionary dic = DictionaryUtil.getDictionaryById(detachmentCode, LvApplication.getInstance().getCurrentOrgList());
                if(dic != null) {
                    departmentUuid = dic.getCode();
                } else {
                    departmentUuid = ownerDepCode;
                }
            }
        }
    }

    public PrimaryAttribute getPrimaryValues() {
        PrimaryAttribute attribute = new PrimaryAttribute();
        attribute.setPrimaryValue1(name);
        attribute.setPrimaryValue2("");
        attribute.setPrimaryValue3(address);

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);
        nf.setMinimumFractionDigits(0);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        nf.setGroupingUsed(false);
        attribute.setPrimaryValue4(nf.format(dataQuality*100)+ "%");

        return  attribute;
    }


    /**set get method**/
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getEleType() {
        return eleType;
    }

    public void setEleType(String eleType) {
        this.eleType = eleType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Double getDataQuality() {
        return dataQuality;
    }

    public void setDataQuality(Double dataQuality) {
        this.dataQuality = dataQuality;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeyUnitUuid() {
        return keyUnitUuid;
    }

    public void setKeyUnitUuid(String keyUnitUuid) {
        this.keyUnitUuid = keyUnitUuid;
    }

    public String getKeyUnitName() {
        return keyUnitName;
    }

    public void setKeyUnitName(String keyUnitName) {
        this.keyUnitName = keyUnitName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getDutyNum() {
        return dutyNum;
    }

    public void setDutyNum(Integer dutyNum) {
        this.dutyNum = dutyNum;
    }

    public Integer getDutyVehicle() {
        return dutyVehicle;
    }

    public void setDutyVehicle(Integer dutyVehicle) {
        this.dutyVehicle = dutyVehicle;
    }

    public Double getExtinguishingDose() {
        return extinguishingDose;
    }

    public void setExtinguishingDose(Double extinguishingDose) {
        this.extinguishingDose = extinguishingDose;
    }

    public String getDutyTel() {
        return dutyTel;
    }

    public void setDutyTel(String dutyTel) {
        this.dutyTel = dutyTel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getHeaderTel() {
        return headerTel;
    }

    public void setHeaderTel(String headerTel) {
        this.headerTel = headerTel;
    }

    public String getUnitTel() {
        return unitTel;
    }

    public void setUnitTel(String unitTel) {
        this.unitTel = unitTel;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        String str = StringUtil.get(code) + String.format("|%s", StringUtil.get(name)) + "|其他消防队伍" +
                String.format("|%s",  StringUtil.get(address)) +
                String.format("|%s",  StringUtil.get(unit)) +
                String.format("|%s",  StringUtil.get(headerName));
        return str;
    }

    public String getSubType() {
        return null;
    }

    public void setSubType(String subType) {

    }

    //对应其他消防队伍分为4类
    public String getResEleType() {
        String retType = eleType;
        if(!StringUtil.isEmpty(type)) {
            retType += type;
        }
        return retType;
    }
}