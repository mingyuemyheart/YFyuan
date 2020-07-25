package com.moft.xfapply.model.entity;

import com.moft.xfapply.activity.bussiness.CommonBussiness;
import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
import com.moft.xfapply.app.Constant;
import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.logic.setting.PrefUserInfo;
import com.moft.xfapply.model.base.IGeomElementInfo;
import com.moft.xfapply.model.common.Dictionary;
import com.moft.xfapply.model.common.PrimaryAttribute;
import com.moft.xfapply.model.common.PropertyConditon;
import com.moft.xfapply.model.common.PropertyDes;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.utils.DictionaryUtil;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.Utils;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Property;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * station_battalion
 * 
 * @author wangx
 * @version 1.0.0 2018-05-22
 */
@Table(name = "station_battalion")
public class StationBattalionDBInfo implements IGeomElementInfo {
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
    /** 辖区范围 */
    @Property(column = "jurisdiction_range")
    private String jurisdictionRange;
    /** 辖区面积（平方公里） */
    @Property(column = "jurisdiction_acreage")
    private Double jurisdictionAcreage;
    /** 是否独立接警 */
    @Property(column = "independent")
    private Boolean independent;
    /** 大队长姓名 */
    @Property(column = "captain_name")
    private String captainName;
    /** 大队长联系方式 */
    @Property(column = "captain_tel")
    private String captainTel;
    /** 教导员姓名 */
    @Property(column = "instructor_name")
    private String instructorName;
    /** 教导员联系方式 */
    @Property(column = "instructor_tel")
    private String instructorTel;
    /** 副大队长姓名 */
    @Property(column = "vice_captain_name")
    private String viceCaptainName;
    /** 副大队长联系方式 */
    @Property(column = "vice_captain_tel")
    private String viceCaptainTel;
    /** 副教导员姓名 */
    @Property(column = "vice_instructor_name")
    private String viceInstructorName;
    /** 副教导员联系方式 */
    @Property(column = "vice_instructor_tel")
    private String viceInstructorTel;
    /** 消防文员数 */
    @Property(column = "firefighting_clerk_num")
    private Integer firefightingClerkNum;

    @Transient
    private List<PropertyDes> pdListDetail = new ArrayList<PropertyDes>();

    /**构造函数**/
    public StationBattalionDBInfo() {
        serviceNum = 0;
        firefighterNum = 0;
        firefightingClerkNum = 0;
    }

    /**文言**/
    public List<PropertyDes> getPdListDetail() {
        pdListDetail.clear();

        pdListDetail.add(new PropertyDes("队站编码", "setCode", String.class, code, PropertyDes.TYPE_TEXT));
        pdListDetail.add(new PropertyDes("队站名称", "setName", String.class, name, PropertyDes.TYPE_EDIT));
//        pdListDetail.add(new PropertyDes("组织机构", "", String.class, DictionaryUtil.getValueByCode(departmentUuid, LvApplication.getInstance().getCurrentOrgList()), PropertyDes.TYPE_TEXT));
        Dictionary departmentDic = DictionaryUtil.getDictionaryByCode(departmentUuid, LvApplication.getInstance().getCurrentOrgList());
        String parentDepartment = "";
        if(departmentDic != null) {
            parentDepartment = DictionaryUtil.getValueByCode(departmentDic.getParentCode(), LvApplication.getInstance().getCurrentOrgList());
            pdListDetail.add(new PropertyDes("所属支队", "", String.class, parentDepartment, PropertyDes.TYPE_TEXT));
        }
        pdListDetail.add(new PropertyDes("行政区域*", "setDistrictCode", String.class, districtCode, LvApplication.getInstance().getCurrentRegionDicList(), true, true));

        // 900379【应用终端】中大队 添加 “市本级/外区县”字段，其显示的内容与关联的大队一致。 smy 2019-03-05 开始
        String relegation = getRelegation(belongtoGroup, departmentUuid);
        pdListDetail.add(new PropertyDes("市本级/外区县", "", String.class, relegation, PropertyDes.TYPE_TEXT));
        // 900379【应用终端】中大队 添加 “市本级/外区县”字段，其显示的内容与关联的大队一致 smy 2019-03-05 结束

        pdListDetail.add(new PropertyDes("地址", "setAddress", String.class, address, PropertyDes.TYPE_EDIT));
//        pdListDetail.add(new PropertyDes("路/街", "setRoad", String.class, road, PropertyDes.TYPE_EDIT));
//        pdListDetail.add(new PropertyDes("号", "setNo", String.class, no, PropertyDes.TYPE_EDIT));

        pdListDetail.add(new PropertyDes("下辖中队数", "", Integer.class, GeomEleBussiness.getInstance().getStationStatisticsDataByDepartment(departmentUuid).get("cntSquadron"), PropertyDes.TYPE_TEXT));
        pdListDetail.add(new PropertyDes("大队现役干部人数", "setServiceNum", Integer.class, serviceNum, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("政府专职消防员数", "setFirefighterNum", Integer.class, firefighterNum, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("消防文员数", "setFirefightingClerkNum", Integer.class, firefightingClerkNum, PropertyDes.TYPE_EDIT));
        // 获下辖中队现役官兵总数 = 所有中队现役人数之和
        Integer overallSquadronDutyNum = GeomEleBussiness.getInstance().getOverallSquadronDutyNum(departmentUuid);
        pdListDetail.add(new PropertyDes("下辖中队现役官兵总数", "", Integer.class, overallSquadronDutyNum, PropertyDes.TYPE_TEXT));
        // 计算大队总人数
        Integer serviceNum = getServiceNum() == null ? 0 : getServiceNum();
        Integer fireFighterNum = getFirefighterNum() == null ? 0 : getFirefighterNum();
        Integer clerkNum = getFirefightingClerkNum() == null ? 0 : getFirefightingClerkNum();
        Integer overallPersonNumber = overallSquadronDutyNum + serviceNum + fireFighterNum + clerkNum;
        pdListDetail.add(new PropertyDes("大队总人数", "", Integer.class, overallPersonNumber, PropertyDes.TYPE_TEXT));
        // 计算水源数
        Map<String, Object> wsStatistics = GeomEleBussiness.getInstance().getWsStatisticsDataByDepartment(departmentUuid);
        Integer wsCount = (Integer)wsStatistics.get("watersourceCrane") + (Integer)wsStatistics.get("watersourceFireHydrant")
                + (Integer)wsStatistics.get("watersourceFirePool") + (Integer)wsStatistics.get("watersourceNatureIntake");
        pdListDetail.add(new PropertyDes("水源数(个)", "", Integer.class, wsCount, PropertyDes.TYPE_TEXT));
        // 计算物资数量
        Map<String, Object> materialStatistics = GeomEleBussiness.getInstance().getMaterialStatisticsDataByDepartment(departmentUuid);
        pdListDetail.add(new PropertyDes("车辆数(台)", "", Integer.class, materialStatistics.get("cntFireVehicle"), PropertyDes.TYPE_TEXT));
        DecimalFormat df = new DecimalFormat("0.00");
        pdListDetail.add(new PropertyDes("灭火剂储量(吨)", "", Double.class, df.format(materialStatistics.get("cntExtinguishingAgent")), PropertyDes.TYPE_TEXT));
        pdListDetail.add(new PropertyDes("器材储量(个)", "", Integer.class, materialStatistics.get("cntEquipment"), PropertyDes.TYPE_TEXT));
        // 计算重点单位
        pdListDetail.add(new PropertyDes("重点单位数", "", Integer.class, GeomEleBussiness.getInstance().getKeyUnitStatisticsDataByDepartment(departmentUuid).get("cntKeyUnit"), PropertyDes.TYPE_TEXT));

        //        pdListDetail.add(new PropertyDes("辖区范围", "setJurisdictionRange", String.class, jurisdictionRange, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("辖区面积(km²)", "setJurisdictionAcreage", Double.class, jurisdictionAcreage, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("值班电话", "setContactNum", String.class, contactNum, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("是否独立接警", "setIndependent", Boolean.class, independent, LvApplication.getInstance().getWhetherDicList()));
        pdListDetail.add(new PropertyDes("大队长姓名", "setCaptainName", String.class, captainName, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("大队长联系方式", "setCaptainTel", String.class, captainTel, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("教导员姓名", "setInstructorName", String.class, instructorName, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("教导员联系方式", "setInstructorTel", String.class, instructorTel, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("副大队长姓名", "setViceCaptainName", String.class, viceCaptainName, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("副大队长联系方式", "setViceCaptainTel", String.class, viceCaptainTel, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("副教导员姓名", "setViceInstructorName", String.class, viceInstructorName, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("副教导员联系方式", "setViceInstructorTel", String.class, viceInstructorTel, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("备注", "setRemark", String.class, remark, PropertyDes.TYPE_EDIT));

        //TODO:下辖中队数需要写统计方法
//        pdListDetail.add(new PropertyDes("下辖中队数", "", Integer.class, , PropertyDes.TYPE_TEXT));


        return pdListDetail;
    }

    @Override
    public String getOutline() {
        DecimalFormat df = new DecimalFormat("0.00");
        Map<String, Object> materialStatistics = GeomEleBussiness.getInstance().getMaterialStatisticsDataByDepartment(departmentUuid);
        Map<String, Object> wsStatistics = GeomEleBussiness.getInstance().getWsStatisticsDataByDepartment(departmentUuid);
        String outline = "";

        outline += "值班电话：<font color=#0074C7>" + StringUtil.get(contactNum) +
                "</font>&nbsp;&nbsp;车辆数(台)：<font color=#0074C7>" + StringUtil.get((Integer) materialStatistics.get("cntFireVehicle")) + "</font>" + Constant.OUTLINE_DIVIDER;
        outline += "大队长及联系方式：<font color=#0074C7>" + StringUtil.get(captainName) + StringUtil.get(captainTel) + "</font>" + Constant.OUTLINE_DIVIDER;
        outline += "重点单位数(个)：<font color=#0074C7>" + StringUtil.get((Integer) GeomEleBussiness.getInstance().getKeyUnitStatisticsDataByDepartment(departmentUuid).get("cntKeyUnit")) +
                "</font>&nbsp;&nbsp;水源数(个)：<font color=#0074C7>" + StringUtil.get((Integer)wsStatistics.get("watersourceCrane") + (Integer)wsStatistics.get("watersourceFireHydrant")
                + (Integer)wsStatistics.get("watersourceFirePool") + (Integer)wsStatistics.get("watersourceNatureIntake")) + Constant.OUTLINE_DIVIDER;
        outline += "灭火剂储量(吨)：<font color=#0074C7>" + StringUtil.get(df.format(materialStatistics.get("cntExtinguishingAgent"))) +
                "</font>&nbsp;&nbsp;器材储量(个)：<font color=#0074C7>" + StringUtil.get((Integer) materialStatistics.get("cntEquipment")) + Constant.OUTLINE_DIVIDER;
        outline += "&" + "值班电话：" + StringUtil.get(contactNum) +  "&"
                + "大队长(" + StringUtil.get(captainName) + ")：" + StringUtil.get(captainTel) + "&"
                + "教导员(" + StringUtil.get(instructorName) + ")：" + StringUtil.get(instructorTel) + "&"
                + "副大队长(" + StringUtil.get(viceCaptainName) + ")：" + StringUtil.get(viceCaptainTel) + "&"
                + "副教导员(" + StringUtil.get(viceInstructorName) + ")：" + StringUtil.get(viceInstructorTel) + "&"
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
        pdListDetailForFilter.add(new PropertyConditon("大队现役干部人数", "station_battalion", "service_num", Integer.class, PropertyConditon.TYPE_EDIT_NUMBER));
        pdListDetailForFilter.add(new PropertyConditon("政府专职消防员数", "station_battalion", "firefighter_num", Integer.class, PropertyConditon.TYPE_EDIT_NUMBER));

        return pdListDetailForFilter;
    }

    public void setAdapter() {
        if(StringUtil.isEmpty(departmentUuid)) {
            PrefUserInfo pui = PrefUserInfo.getInstance();
            departmentUuid = pui.getUserInfo("department_uuid");
        }
        type = AppDefs.CompEleType.BATTALION.toString();
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

    // 900379【应用终端】中大队 添加 “市本级/外区县”字段，其显示的内容与关联的大队一致 smy 2019-03-05 开始
    private String getRelegation(String belongtoGroup, String departmentUuid) {
        String relegation = "";
        if (StringUtil.isEmpty(belongtoGroup) || StringUtil.isEmpty(departmentUuid)) {
            return relegation;
        }
        // 获得组织数据
        //支持省离线数据库查询
        SysDepartmentDBInfo dbInfo = CommonBussiness.getInstance().getSysDepartmentByCode(LvApplication.getInstance().getCityName(), departmentUuid);
        if (dbInfo != null) {
            String relegationStr = dbInfo.getRelegation();
            relegation = DictionaryUtil.getValueByCode(relegationStr, LvApplication.getInstance().getCompDicMap().get("ZJJG_GS"));
        }
        return relegation;
    }
    // 900379【应用终端】中大队 添加 “市本级/外区县”字段，其显示的内容与关联的大队一致 smy 2019-03-05 结束

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

    public String getJurisdictionRange() {
        return jurisdictionRange;
    }

    public void setJurisdictionRange(String jurisdictionRange) {
        this.jurisdictionRange = jurisdictionRange;
    }

    public Double getJurisdictionAcreage() {
        return jurisdictionAcreage;
    }

    public void setJurisdictionAcreage(Double jurisdictionAcreage) {
        this.jurisdictionAcreage = jurisdictionAcreage;
    }

    public Boolean getIndependent() {
        return independent;
    }

    public void setIndependent(Boolean independent) {
        this.independent = independent;
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

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorTel() {
        return instructorTel;
    }

    public void setInstructorTel(String instructorTel) {
        this.instructorTel = instructorTel;
    }

    public String getViceCaptainName() {
        return viceCaptainName;
    }

    public void setViceCaptainName(String viceCaptainName) {
        this.viceCaptainName = viceCaptainName;
    }

    public String getViceCaptainTel() {
        return viceCaptainTel;
    }

    public void setViceCaptainTel(String viceCaptainTel) {
        this.viceCaptainTel = viceCaptainTel;
    }

    public String getViceInstructorName() {
        return viceInstructorName;
    }

    public void setViceInstructorName(String viceInstructorName) {
        this.viceInstructorName = viceInstructorName;
    }

    public String getViceInstructorTel() {
        return viceInstructorTel;
    }

    public void setViceInstructorTel(String viceInstructorTel) {
        this.viceInstructorTel = viceInstructorTel;
    }

    public Integer getFirefightingClerkNum() {
        return firefightingClerkNum;
    }

    public void setFirefightingClerkNum(Integer firefightingClerkNum) {
        this.firefightingClerkNum = firefightingClerkNum;
    }

    @Override
    public String toString() {
        String str = StringUtil.get(code) + String.format("|%s", StringUtil.get(name)) + "|消防大队" +
                String.format("|%s",  StringUtil.get(address) + StringUtil.get(road) + StringUtil.get(no)) +
                String.format("|%s",  StringUtil.get(jurisdictionRange)) +
                String.format("|%s",  StringUtil.get(captainName)) +
                String.format("|%s",  StringUtil.get(instructorName)) +
                String.format("|%s",  StringUtil.get(viceCaptainName)) +
                String.format("|%s",  StringUtil.get(viceInstructorName));
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