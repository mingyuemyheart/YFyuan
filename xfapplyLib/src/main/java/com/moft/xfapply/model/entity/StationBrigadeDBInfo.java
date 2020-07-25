package com.moft.xfapply.model.entity;

import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.base.IGeomElementInfo;
import com.moft.xfapply.model.common.PrimaryAttribute;
import com.moft.xfapply.model.common.PropertyConditon;
import com.moft.xfapply.model.common.PropertyDes;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.Utils;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Property;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * station_brigade
 *
 * @author wangx
 * @version 1.0.0 2018-05-22
 */
@Table(name = "station_brigade")
public class StationBrigadeDBInfo implements IGeomElementInfo {
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

    /** districtCode */
    @Property(column = "district_code")
    private String districtCode;
    /** type */
    @Property(column = "type")
    private String type;
    /** road */
    @Property(column = "road")
    private String road;
    /** no */
    @Property(column = "no")
    private String no;
    /** serviceNum */
    @Property(column = "service_num")
    private Integer serviceNum;
    /** firefighterNum */
    @Property(column = "firefighter_num")
    private Integer firefighterNum;
    /** contactNum */
    @Property(column = "contact_num")
    private String contactNum;
    /** version */
    @Property(column = "version")
    private Integer version;
    /** 文职雇员数量 */
    @Property(column = "employee_num")
    private Integer employeeNum;
    /** 传真 */
    @Property(column = "fax")
    private String fax;
    /** 总队长姓名 */
    @Property(column = "captain_name")
    private String captainName;
    /** 总队长联系方式 */
    @Property(column = "captain_tel")
    private String captainTel;
    /** 总队政委姓名 */
    @Property(column = "political_commissar_name")
    private String politicalCommissarName;
    /** 总队政委联系方式 */
    @Property(column = "political_commissar_tel")
    private String politicalCommissarTel;

    /** 辖区面积（平方公里） */
    @Property(column = "jurisdiction_acreage")
    private Double jurisdictionAcreage;

    @Transient
    private List<PropertyDes> pdListDetail = new ArrayList<PropertyDes>();


    /**构造函数**/
    public StationBrigadeDBInfo() {
        serviceNum = 0;
        firefighterNum = 0;
        employeeNum = 0;
    }

    /**文言**/
    public List<PropertyDes> getPdListDetail() {
        pdListDetail.clear();

        pdListDetail.add(new PropertyDes("编号", "setCode", String.class, code, PropertyDes.TYPE_TEXT));
        pdListDetail.add(new PropertyDes("名称", "setName", String.class, name, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("地址", "setAddress", String.class, address, PropertyDes.TYPE_EDIT));
//        pdListDetail.add(new PropertyDes("路/街", "setRoad", String.class, road, PropertyDes.TYPE_EDIT));
//        pdListDetail.add(new PropertyDes("号", "setNo", String.class, no, PropertyDes.TYPE_EDIT));
//        pdListDetail.add(new PropertyDes("行政区", "setDistrictCode", String.class, districtCode, LvApplication.getInstance().getCurrentRegionDicList()));

        Map<String, Object> stationStatisticsResult =  GeomEleBussiness.getInstance().getStationStatisticsDataByDepartment(departmentUuid);
        // 下辖支队数
        pdListDetail.add(new PropertyDes("下辖支队数", "", Integer.class, stationStatisticsResult.get("cntDetachment"), PropertyDes.TYPE_TEXT));
        // 下辖中队数
        pdListDetail.add(new PropertyDes("下辖中队数", "", Integer.class, stationStatisticsResult.get("cntSquadron"), PropertyDes.TYPE_TEXT));

        pdListDetail.add(new PropertyDes("现役官兵人数", "setServiceNum", Integer.class, serviceNum, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("政府专职消防员数", "setFirefighterNum", Integer.class, firefighterNum, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("文职雇员数", "setEmployeeNum", Integer.class, employeeNum  , PropertyDes.TYPE_EDIT));

        // 计算物资数量
        Map<String, Object> materialStatistics = GeomEleBussiness.getInstance().getMaterialStatisticsDataByDepartment(departmentUuid);
        pdListDetail.add(new PropertyDes("执勤车辆(台)", "", Integer.class, materialStatistics.get("cntFireVehicle"), PropertyDes.TYPE_TEXT));
        DecimalFormat df = new DecimalFormat("0.00");
        pdListDetail.add(new PropertyDes("灭火剂总量(吨)", "", Double.class, df.format(materialStatistics.get("cntExtinguishingAgent")), PropertyDes.TYPE_TEXT));
        // 计算水源数
        Map<String, Object> wsStatistics = GeomEleBussiness.getInstance().getWsStatisticsDataByDepartment(departmentUuid);
        Integer wsCount = (Integer)wsStatistics.get("watersourceCrane") + (Integer)wsStatistics.get("watersourceFireHydrant")
                + (Integer)wsStatistics.get("watersourceFirePool") + (Integer)wsStatistics.get("watersourceNatureIntake");
        pdListDetail.add(new PropertyDes("水源数(个)", "", Integer.class, wsCount, PropertyDes.TYPE_TEXT));
        // 计算重点单位
        pdListDetail.add(new PropertyDes("管辖重点单位数", "", Integer.class, GeomEleBussiness.getInstance().getKeyUnitStatisticsDataByDepartment(departmentUuid).get("cntKeyUnit"), PropertyDes.TYPE_TEXT));


        pdListDetail.add(new PropertyDes("辖区面积(km²)", "setJurisdictionAcreage", Double.class, jurisdictionAcreage, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("联系电话", "setContactNum", String.class, contactNum, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("传真", "setFax", String.class, fax, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("总队长姓名", "setCaptainName", String.class, captainName, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("总队长联系方式", "setCaptainTel", String.class, captainTel, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("总队政委姓名", "setPoliticalCommissarName", String.class, politicalCommissarName , PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("总队政委联系方式", "setPoliticalCommissarTel", String.class, politicalCommissarTel, PropertyDes.TYPE_EDIT));
//        pdListDetail.add(new PropertyDes("下辖支队数", "setJurisdictionDetachmentNum", Integer.class, jurisdictionDetachmentNum, PropertyDes.TYPE_EDIT));
//        pdListDetail.add(new PropertyDes("下辖中队数", "setJurisdictionSquadronsNum", Integer.class, jurisdictionSquadronsNum, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("备注", "setRemark", String.class, remark, PropertyDes.TYPE_EDIT));

        return pdListDetail;
    }

    @Override
    public String getOutline() {
        DecimalFormat df = new DecimalFormat("0.00");
        Map<String, Object> materialStatistics = GeomEleBussiness.getInstance().getMaterialStatisticsDataByDepartment(departmentUuid);
        Map<String, Object> wsStatistics = GeomEleBussiness.getInstance().getWsStatisticsDataByDepartment(departmentUuid);
        String outline = "";

        outline += "联系电话：<font color=#0074C7>" + StringUtil.get(contactNum) +
                "</font>&nbsp;&nbsp;车辆数(台)：<font color=#0074C7>" + StringUtil.get((Integer) materialStatistics.get("cntFireVehicle")) + "</font>" + Constant.OUTLINE_DIVIDER;
        outline += "总队长及联系方式：<font color=#0074C7>" + StringUtil.get(captainName) + StringUtil.get(captainTel) + "</font>" + Constant.OUTLINE_DIVIDER;
        outline += "重点单位数(个)：<font color=#0074C7>" + StringUtil.get((Integer) GeomEleBussiness.getInstance().getKeyUnitStatisticsDataByDepartment(departmentUuid).get("cntKeyUnit")) +
                "</font>&nbsp;&nbsp;水源数(个)：<font color=#0074C7>" + StringUtil.get((Integer)wsStatistics.get("watersourceCrane") + (Integer)wsStatistics.get("watersourceFireHydrant")
                + (Integer)wsStatistics.get("watersourceFirePool") + (Integer)wsStatistics.get("watersourceNatureIntake")) + Constant.OUTLINE_DIVIDER;
        outline += "灭火剂储量(吨)：<font color=#0074C7>" + StringUtil.get(df.format(materialStatistics.get("cntExtinguishingAgent"))) +
                "</font>" + Constant.OUTLINE_DIVIDER;
        outline += "&" + "联系电话：" + StringUtil.get(contactNum) +  "&"
                + "总队长(" + StringUtil.get(captainName) + ")：" + StringUtil.get(captainTel) + "&"
                + "政委(" + StringUtil.get(politicalCommissarName) + ")：" + StringUtil.get(politicalCommissarTel) + "&"
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

    public void setAdapter() {
        if(StringUtil.isEmpty(departmentUuid)) {
            PrefUserInfo pui = PrefUserInfo.getInstance();
            departmentUuid = pui.getUserInfo("department_uuid");
        }
        type = AppDefs.CompEleType.BRIGADE.toString();
    }

    public PrimaryAttribute getPrimaryValues() {
        PrimaryAttribute attribute = new PrimaryAttribute();
        attribute.setPrimaryValue1(String.format("编号：%s", StringUtil.get(code)));
        attribute.setPrimaryValue2(name);
        attribute.setPrimaryValue3(address);
        return  attribute;
    }

    @Transient
    private static List<PropertyConditon> pdListDetailForFilter = new ArrayList<PropertyConditon>();
    public static List<PropertyConditon> getPdListDetailForFilter() {
        pdListDetailForFilter.clear();
        pdListDetailForFilter.add(new PropertyConditon("现役官兵人数", "station_brigade", "service_num", Integer.class, PropertyConditon.TYPE_EDIT_NUMBER));
        pdListDetailForFilter.add(new PropertyConditon("政府专职消防员数", "station_brigade", "firefighter_num", Integer.class, PropertyConditon.TYPE_EDIT_NUMBER));

        return pdListDetailForFilter;
    }

    /*set get method*/

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

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Integer getServiceNum() {
        return serviceNum;
    }

    public void setServiceNum(Integer serviceNum) {
        this.serviceNum = serviceNum;
    }

    public Integer getFirefighterNum() {
        return firefighterNum;
    }

    public void setFirefighterNum(Integer firefighterNum) {
        this.firefighterNum = firefighterNum;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(Integer employeeNum) {
        this.employeeNum = employeeNum;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCaptainName() {
        return captainName;
    }

    public void setCaptainName(String captainName) {
        this.captainName = captainName;
    }

    public String getCaptainTel() {
        return captainTel;
    }

    public void setCaptainTel(String captainTel) {
        this.captainTel = captainTel;
    }

    public String getPoliticalCommissarName() {
        return politicalCommissarName;
    }

    public void setPoliticalCommissarName(String politicalCommissarName) {
        this.politicalCommissarName = politicalCommissarName;
    }

    public String getPoliticalCommissarTel() {
        return politicalCommissarTel;
    }

    public Double getJurisdictionAcreage() {
        return jurisdictionAcreage;
    }

    public void setJurisdictionAcreage(Double jurisdictionAcreage) {
        this.jurisdictionAcreage = jurisdictionAcreage;
    }

    @Override
    public String toString() {
        String str = StringUtil.get(code) + String.format("|%s", StringUtil.get(name)) + "|消防总队" +
                String.format("|%s",  StringUtil.get(address) + StringUtil.get(road) + StringUtil.get(no)) +
                String.format("|%s",  StringUtil.get(captainName)) +
                String.format("|%s",  StringUtil.get(captainTel));
        return str;
    }

    public String getSubType() {
        return null;
    }

    public void setSubType(String subType) {

    }

    //对应其他消防队伍分为4类
    public String getResEleType() {
        return eleType;
    }
}