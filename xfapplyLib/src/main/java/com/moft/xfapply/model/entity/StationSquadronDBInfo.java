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
 * 执勤中队(station_squadron)
 * 
 * @author wangx
 * @version 1.0.0 2018-05-22
 */
@Table(name = "station_squadron")
public class StationSquadronDBInfo implements IGeomElementInfo {
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
    @Property(column = "squadron_type")
    private String squadronType; //队伍类型
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
    /** 每日执勤人数 */
    @Property(column = "duty_num")
    private Integer dutyNum;
    /** 中队长姓名 */
    @Property(column = "squadron_leader_name")
    private String squadronLeaderName;
    /** 中队长联系方式 */
    @Property(column = "squadron_leader_tel")
    private String squadronLeaderTel;
    /** 指导员姓名 */
    @Property(column = "instructor_name")
    private String instructorName;
    /** 指导员联系方式 */
    @Property(column = "instructor_tel")
    private String instructorTel;
    /** 副指导员姓名 */
    @Property(column = "vice_instructor_name")
    private String viceInstructorName;
    /** 副指导员联系方式 */
    @Property(column = "vice_instructor_tel")
    private String viceInstructorTel;
    /** 副中队长一姓名 */
    @Property(column = "vice_squadron_leader_one_name")
    private String viceSquadronLeaderOneName;
    /** 副中队长一联系方式 */
    @Property(column = "vice_squadron_leader_first_tel")
    private String viceSquadronLeaderFirstTel;
    /** 副中队长二姓名 */
    @Property(column = "vice_squadron_leader_second_name")
    private String viceSquadronLeaderSecondName;
    /** 副中队长二联系方式 */
    @Property(column = "vice_squadron_leader_second_tel")
    private String viceSquadronLeaderSecondTel;
    /** 副中队长三姓名 */
    @Property(column = "vice_squadron_leader_third_name")
    private String viceSquadronLeaderThirdName;
    /** 副中队长三联系方式 */
    @Property(column = "vice_squadron_leader_third_tel")
    private String viceSquadronLeaderThirdTel;

    @Transient
    private List<PropertyDes> pdListDetail = new ArrayList<PropertyDes>();

    /**构造函数**/
    public StationSquadronDBInfo() {
        serviceNum = 0;
        firefighterNum = 0;
        dutyNum = 0;
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
            pdListDetail.add(new PropertyDes("所属大队", "", String.class, parentDepartment, PropertyDes.TYPE_TEXT));
        }
        pdListDetail.add(new PropertyDes("行政区域*", "setDistrictCode", String.class, districtCode, LvApplication.getInstance().getCurrentRegionDicList(), true, true));

        // 900379【应用终端】中大队 添加 “市本级/外区县”字段，其显示的内容与关联的大队一致 smy 2019-03-05 开始
        String relegation = getRelegation(belongtoGroup, departmentUuid);
        pdListDetail.add(new PropertyDes("市本级/外区县", "", String.class, relegation, PropertyDes.TYPE_TEXT));
        // 900379【应用终端】中大队 添加 “市本级/外区县”字段，其显示的内容与关联的大队一致 smy 2019-03-05 结束

        pdListDetail.add(new PropertyDes("队伍类型*", "setSquadronType", String.class, squadronType, LvApplication.getInstance().getCompDicMap().get("SQUADRON_TYPE"), true, true));
        pdListDetail.add(new PropertyDes("地址", "setAddress", String.class, address, PropertyDes.TYPE_EDIT));
//        pdListDetail.add(new PropertyDes("路/街", "setRoad", String.class, road, PropertyDes.TYPE_EDIT));
//        pdListDetail.add(new PropertyDes("号", "setNo", String.class, no, PropertyDes.TYPE_EDIT));

        pdListDetail.add(new PropertyDes("现役人数", "setServiceNum", Integer.class, serviceNum, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("政府专职消防员数", "setFirefighterNum", Integer.class, firefighterNum, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("每日执勤人数", "setDutyNum", Integer.class, dutyNum, PropertyDes.TYPE_EDIT));

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


//        pdListDetail.add(new PropertyDes("辖区范围", "setJurisdictionRange", String.class,jurisdictionRange  , PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("辖区面积(km²)", "setJurisdictionAcreage", Double.class, jurisdictionAcreage, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("值班电话", "setContactNum", String.class, contactNum, PropertyDes.TYPE_EDIT));

        pdListDetail.add(new PropertyDes("中队长姓名", "setSquadronLeaderName", String.class, squadronLeaderName, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("中队长联系方式", "setSqIuadronLeaderTel", String.class, squadronLeaderTel , PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("指导员姓名", "setInstructorName", String.class, instructorName, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("指导员联系方式", "setInstructorTelk", String.class, instructorTel, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("副指导员姓名", "setViceInstructorName", String.class, viceInstructorName, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("副指导员联系方式", "setViceInstructorTel", String.class, viceInstructorTel, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("副中队长一姓名", "setViceSquadronLeaderOneName", String.class, viceSquadronLeaderOneName, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("副中队长一联系方式", "setViceSquadronLeaderFirstTel", String.class, viceSquadronLeaderFirstTel, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("副中队长二姓名", "setViceSquadronLeaderSecondName", String.class, viceSquadronLeaderSecondName, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("副中队长二联系方式", "setViceSquadronLeaderSecondTel", String.class, viceSquadronLeaderSecondTel, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("副中队长三姓名", "setViceSquadronLeaderThirdName", String.class, viceSquadronLeaderThirdName, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("副中队长三联系方式", "setViceSquadronLeaderThirdTel", String.class, viceSquadronLeaderThirdTel, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("备注", "setRemark", String.class, remark, PropertyDes.TYPE_EDIT));

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
        outline += "中队长及联系方式：<font color=#0074C7>" + StringUtil.get(squadronLeaderName) + StringUtil.get(squadronLeaderTel) + "</font>" + Constant.OUTLINE_DIVIDER;
        outline += "重点单位数(个)：<font color=#0074C7>" + StringUtil.get((Integer) GeomEleBussiness.getInstance().getKeyUnitStatisticsDataByDepartment(departmentUuid).get("cntKeyUnit")) +
                "</font>&nbsp;&nbsp;水源数(个)：<font color=#0074C7>" + StringUtil.get((Integer)wsStatistics.get("watersourceCrane") + (Integer)wsStatistics.get("watersourceFireHydrant")
                + (Integer)wsStatistics.get("watersourceFirePool") + (Integer)wsStatistics.get("watersourceNatureIntake")) + Constant.OUTLINE_DIVIDER;
        outline += "灭火剂储量(吨)：<font color=#0074C7>" + StringUtil.get(df.format(materialStatistics.get("cntExtinguishingAgent"))) +
                "</font>&nbsp;&nbsp;器材储量(个)：<font color=#0074C7>" + StringUtil.get((Integer) materialStatistics.get("cntEquipment")) + Constant.OUTLINE_DIVIDER;
        outline += "&" + "值班电话：" + StringUtil.get(contactNum) +  "&"
                + "中队长(" + StringUtil.get(squadronLeaderName) + ")：" + StringUtil.get(squadronLeaderTel) + "&"
                + "指导员(" + StringUtil.get(instructorName) + ")：" + StringUtil.get(instructorTel) + "&"
                + "副中队长(" + StringUtil.get(viceSquadronLeaderOneName) + ")：" + StringUtil.get(viceSquadronLeaderFirstTel) + "&"
                + "副指导员(" + StringUtil.get(viceInstructorName) + ")：" + StringUtil.get(viceInstructorTel) + "&"
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
        pdListDetailForFilter.add(new PropertyConditon("现役人数", "station_squadron", "service_num", Integer.class, PropertyConditon.TYPE_EDIT_NUMBER));
        pdListDetailForFilter.add(new PropertyConditon("政府专职消防员数", "station_squadron", "firefighter_num", Integer.class, PropertyConditon.TYPE_EDIT_NUMBER));

        return pdListDetailForFilter;
    }

    public void setAdapter() {
        if(StringUtil.isEmpty(departmentUuid)) {
            PrefUserInfo pui = PrefUserInfo.getInstance();
            departmentUuid = pui.getUserInfo("department_uuid");
        }
        type = AppDefs.CompEleType.SQUADRON.toString();
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


    /*get set method*/

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

    public String getSquadronType() {
        return squadronType;
    }

    public void setSquadronType(String squadronType) {
        this.squadronType = squadronType;
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

    public Integer getDutyNum() {
        return dutyNum;
    }

    public void setDutyNum(Integer dutyNum) {
        this.dutyNum = dutyNum;
    }

    public String getSquadronLeaderName() {
        return squadronLeaderName;
    }

    public void setSquadronLeaderName(String squadronLeaderName) {
        this.squadronLeaderName = squadronLeaderName;
    }

    public String getSquadronLeaderTel() {
        return squadronLeaderTel;
    }

    public void setSquadronLeaderTel(String squadronLeaderTel) {
        this.squadronLeaderTel = squadronLeaderTel;
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

    public String getViceSquadronLeaderOneName() {
        return viceSquadronLeaderOneName;
    }

    public void setViceSquadronLeaderOneName(String viceSquadronLeaderOneName) {
        this.viceSquadronLeaderOneName = viceSquadronLeaderOneName;
    }

    public String getViceSquadronLeaderFirstTel() {
        return viceSquadronLeaderFirstTel;
    }

    public void setViceSquadronLeaderFirstTel(String viceSquadronLeaderFirstTel) {
        this.viceSquadronLeaderFirstTel = viceSquadronLeaderFirstTel;
    }

    public String getViceSquadronLeaderSecondName() {
        return viceSquadronLeaderSecondName;
    }

    public void setViceSquadronLeaderSecondName(String viceSquadronLeaderSecondName) {
        this.viceSquadronLeaderSecondName = viceSquadronLeaderSecondName;
    }

    public String getViceSquadronLeaderSecondTel() {
        return viceSquadronLeaderSecondTel;
    }

    public void setViceSquadronLeaderSecondTel(String viceSquadronLeaderSecondTel) {
        this.viceSquadronLeaderSecondTel = viceSquadronLeaderSecondTel;
    }

    public String getViceSquadronLeaderThirdName() {
        return viceSquadronLeaderThirdName;
    }

    public void setViceSquadronLeaderThirdName(String viceSquadronLeaderThirdName) {
        this.viceSquadronLeaderThirdName = viceSquadronLeaderThirdName;
    }

    public String getViceSquadronLeaderThirdTel() {
        return viceSquadronLeaderThirdTel;
    }

    public void setViceSquadronLeaderThirdTel(String viceSquadronLeaderThirdTel) {
        this.viceSquadronLeaderThirdTel = viceSquadronLeaderThirdTel;
    }

    @Override
    public String toString() {
        String str = StringUtil.get(code) + String.format("|%s", StringUtil.get(name)) + "|消防中队" +
                String.format("|%s",  StringUtil.get(address) + StringUtil.get(road) + StringUtil.get(no)) +
                String.format("|%s",  StringUtil.get(jurisdictionRange)) +
                String.format("|%s",  StringUtil.get(instructorName)) +
                String.format("|%s",  StringUtil.get(viceInstructorName)) +
                String.format("|%s",  StringUtil.get(viceSquadronLeaderOneName)) +
                String.format("|%s",  StringUtil.get(viceSquadronLeaderSecondName)) +
                String.format("|%s",  StringUtil.get(viceSquadronLeaderThirdName));
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