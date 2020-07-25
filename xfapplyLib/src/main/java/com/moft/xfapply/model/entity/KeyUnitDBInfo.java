package com.moft.xfapply.model.entity;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Date;

import net.tsz.afinal.annotation.sqlite.Transient;
import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Property;
import net.tsz.afinal.annotation.sqlite.Table;

import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
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

import java.util.ArrayList;
import java.util.List;

/**
 * key_unit
 * 
 * @author wangx
 * @version 1.0.0 2018-05-22
 */
@Table(name = "key_unit")
public class KeyUnitDBInfo implements IGeomElementInfo {
    /** 版本号 */
    @Transient
    private static final long serialVersionUID = 8627085012885883103L;

    //base fileds
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


    /** 防火级别
 */
    @Property(column = "prevention_grade")
    private String preventionGrade;
    /** 防火队站uuid
 */
    @Property(column = "prevention_station_uuid")
    private String preventionStationUuid;
    /** 灭火队站uuid
 */
    @Property(column = "extinguish_station_uuid")
    private String extinguishStationUuid;
    /** 单位性质标识
 */
    @Property(column = "character_uuid")
    private String characterUuid;

    /** 行政区划编码
 */
    @Property(column = "district_code")
    private String districtCode;
    /** 路/街
 */
    @Property(column = "addr_road")
    private String addrRoad;
    /** 号
 */
    @Property(column = "addr_number")
    private String addrNumber;
    /** 消防负责人
 */
    @Property(column = "responsible_person")
    private String responsiblePerson;
    /** 消防负责人联系方式
 */
    @Property(column = "responsible_person_contact")
    private String responsiblePersonContact;
    /** 消防负责人身份证
     */
    @Property(column = "responsible_person_id_card")
    private String responsiblePersonIdCard;
    /** 消防管理人
 */
    @Property(column = "manage_person")
    private String managePerson;
    /** 消防管理人联系方式
 */
    @Property(column = "manage_person_contact")
    private String managePersonContact;
    /** 消防管理人身份证
     */
    @Property(column = "manage_person_id_card")
    private String managePersonIdCard;
    /** 单位值班电话
 */
    @Property(column = "duty_telephone")
    private String dutyTelephone;
    /** 防火监督员
     */
    @Property(column = "supervisor_person")
    private String supervisorPerson;
    /** 防火监督员联系方式
     */
    @Property(column = "supervisor_person_contact")
    private String supervisorPersonContact;

    /** 有无消防力量
     */
    @Property(column = "has_fire_force")
    private Boolean hasFireForce;

    // 消防力量-类型
    @Property(column = "fire_force_type")
    private String fireForceType;

    // 消防力量-联系方式
    @Property(column = "fire_force_unit_tel")
    private String fireForceUnitTel;

    // 消防力量-执勤车辆
    @Property(column = "fire_force_duty_vehicle")
    private Integer fireForceDutyVehicle;

    // 消防力量-消防队员人数
    @Property(column = "fire_force_duty_num")
    private Integer fireForceDutyNum;

    /** 单位概况
 */
    @Property(column = "unit_overview")
    private String unitOverview;
    /** 功能分区描述
     */
    @Property(column = "functional_description")
    private String functionalDescription;
    /** 建筑情况
 */
    @Property(column = "structure_flag")
    private String structureFlag;
    /** 有无全景
 */
    @Property(column = "has_pano")
    private Boolean hasPano;
    /** 有无预案
 */
    @Property(column = "has_rescue")
    private Boolean hasRescue;
    /** 有无三维
 */
    @Property(column = "has_mx")
    private Boolean hasMx;
    /** 数据版本
 */
    @Property(column = "version")
    private Integer version;

    /** 毗邻情况-东
     */
    @Property(column = "adjacent_east")
    private String adjacentEast;

    /** 毗邻情况-西
     */
    @Property(column = "adjacent_west")
    private String adjacentWest;
    /** 毗邻情况-南
     */
    @Property(column = "adjacent_south")
    private String adjacentSouth;
    /** 毗邻情况-北
     */
    @Property(column = "adjacent_north")
    private String adjacentNorth;
    /** 辖区行车路线
     */
    @Property(column = "route_from_station")
    private String routeFromStation;
    /** 到达时长
     */
    @Property(column = "time_station_arrive")
    private Integer timeStationArrive;
    /** 总占地面积
     */
    @Property(column = "floor_area")
    private Double floorArea;
    /** 总建筑面积
     */
    @Property(column = "structure_area")
    private Double structureArea;

    // 消防力量-备注
    @Property(column = "fire_force_remark")
    private String fireForceRemark;

    @Transient
    private List<PropertyDes> pdListDetail = new ArrayList<PropertyDes>();

    public KeyUnitDBInfo() {
        hasFireForce = true;
        fireForceDutyVehicle = 0;
        fireForceDutyNum = 0;
    }

    public List<PropertyDes> getPdListDetail() {
        pdListDetail.clear();

//        pdListDetail.add(new PropertyDes("编号", "setCode", String.class, code, PropertyDes.TYPE_TEXT));
        pdListDetail.add(new PropertyDes("名称*", "setName", String.class, name, PropertyDes.TYPE_EDIT, true));
        pdListDetail.add(new PropertyDes("性质", "setCharacterUuid", String.class, characterUuid, LvApplication.getInstance().getCompDicMap().get("ZDDW_DWXZ")));
        pdListDetail.add(new PropertyDes("行政区", "setDistrictCode", String.class, districtCode, LvApplication.getInstance().getCurrentRegionDicList()));
        pdListDetail.add(new PropertyDes("防火级别*", "setPreventionGrade", String.class, preventionGrade, LvApplication.getInstance().getCompDicMap().get("ZDDW_FHJB"), true, true));
        pdListDetail.add(new PropertyDes("单位概况", "setUnitOverview", String.class, unitOverview, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("功能分区描述", "setFunctionalDescription", String.class, functionalDescription, PropertyDes.TYPE_EDIT));
        //特殊处理重点单位下，建筑情况数据字典
        pdListDetail.add(new PropertyDes("建筑信息分类*", "setStructureFlag", String.class, structureFlag, LvApplication.getInstance().getKeyUnitStructureDicList(), true, true));
        pdListDetail.add(new PropertyDes("总建筑面积", "setStructureArea", Double.class, structureArea, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("总占地面积", "setFloorArea", Double.class, floorArea, PropertyDes.TYPE_EDIT));

        //ID:844641 19-01-14 重点单位防火管辖大队变更 王泉
        pdListDetail.add(new PropertyDes("防火管辖*", "setPreventionStationUuid", String.class, preventionStationUuid, GeomEleBussiness.getInstance().getPreventionStationDic(belongtoGroup), true, true));
        pdListDetail.add(new PropertyDes("灭火责任队站*", "setExtinguishStationUuid", String.class, extinguishStationUuid, GeomEleBussiness.getInstance().getStationSquadronDic(belongtoGroup), true, true));
        pdListDetail.add(new PropertyDes("消防责任人", "setResponsiblePerson", String.class, responsiblePerson, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("联系方式", "setResponsiblePersonContact", String.class, responsiblePersonContact, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("身份证号", "setResponsiblePersonIdCard", String.class, responsiblePersonIdCard, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("消防管理人", "setManagePerson", String.class, managePerson, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("联系方式", "setManagePersonContact", String.class, managePersonContact, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("身份证号", "setManagePersonIdCard", String.class, managePersonIdCard, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("值班电话", "setDutyTelephone", String.class, dutyTelephone, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("防火监督员", "setSupervisorPerson", String.class, supervisorPerson, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("联系方式", "setSupervisorPersonContact", String.class, supervisorPersonContact, PropertyDes.TYPE_EDIT));

        pdListDetail.add(new PropertyDes("单位地址", "setAddress", String.class, address, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("路/街", "setAddrRoad", String.class, addrRoad, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("号", "setAddrNumber", String.class, addrNumber, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("毗邻情况(东)", "setAdjacentEast", String.class, adjacentEast, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("毗邻情况(西)", "setAdjacentWest", String.class, adjacentWest, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("毗邻情况(南)", "setAdjacentSouth", String.class, adjacentSouth, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("毗邻情况(北)", "setAdjacentNorth", String.class, adjacentNorth, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("行车路线", "setRouteFromStation", String.class, routeFromStation,PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("预计到达时长", "setTimeStationArrive", Integer.class, timeStationArrive, PropertyDes.TYPE_EDIT));

        pdListDetail.add(new PropertyDes("有无消防力量", "setHasFireForce", Boolean.class, hasFireForce, LvApplication.getInstance().getHasOrNohasDicList()));
        pdListDetail.add(new PropertyDes("消防力量类型", "setFireForceType", String.class, fireForceType, LvApplication.getInstance().getCompDicMap().get("XFLL_DWLX")));
        pdListDetail.add(new PropertyDes("消防力量联系方式", "setFireForceUnitTel", String.class, fireForceUnitTel, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("消防力量车辆数", "setFireForceDutyVehicle", Integer.class, fireForceDutyVehicle, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("消防力量人数", "setFireForceDutyNum", Integer.class, fireForceDutyNum, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("消防力量备注", "setFireForceRemark", String.class, fireForceRemark, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("备注", "setRemark", String.class, remark, PropertyDes.TYPE_EDIT));

        return pdListDetail;
    }

    @Transient
    private static List<PropertyConditon> pdListDetailForFilter = new ArrayList<PropertyConditon>();
    public static List<PropertyConditon> getPdListDetailForFilter() {
        pdListDetailForFilter.clear();
        //ID:837818 19-01-09 【总分区】创建及用途对应 王泉 开始
        pdListDetailForFilter.add(new PropertyConditon("防火级别", "key_unit", "prevention_grade", String.class, PropertyConditon.TYPE_LIST, LvApplication.getInstance().getCompDicMap().get("ZDDW_FHJB"), true));
        pdListDetailForFilter.add(new PropertyConditon("单位性质", "key_unit", "character_uuid", String.class, PropertyConditon.TYPE_LIST, LvApplication.getInstance().getCompDicMap().get("ZDDW_DWXZ"), true));
        pdListDetailForFilter.add(new PropertyConditon("使用性质", "key_unit", "partition_structure.usage_type;;partition_device.usage_type", String.class, PropertyConditon.TYPE_LIST, LvApplication.getInstance().getCompDicMap().get("ZDDW_SYXZ"), true));
        pdListDetailForFilter.add(new PropertyConditon("建筑结构", "key_unit", "partition_structure.structure_type;;partition_device.structure_type;;partition_storage_zone.structure_type", String.class, PropertyConditon.TYPE_LIST, LvApplication.getInstance().getCompDicMap().get("ZDDW_JZJG"), true));
        pdListDetailForFilter.add(new PropertyConditon("地上高度(m)", "key_unit", "partition_structure.height_overground", Double.class, PropertyConditon.TYPE_EDIT_NUMBER));
        pdListDetailForFilter.add(new PropertyConditon("地下高度(m)", "key_unit", "partition_structure.height_underground", Double.class, PropertyConditon.TYPE_EDIT_NUMBER));
        pdListDetailForFilter.add(new PropertyConditon("地上层数", "key_unit", "partition_structure.floor_overground", Integer.class, PropertyConditon.TYPE_EDIT_NUMBER));
        pdListDetailForFilter.add(new PropertyConditon("地下层数", "key_unit", "partition_structure.floor_underground", Integer.class, PropertyConditon.TYPE_EDIT_NUMBER));
//        pdListDetailForFilter.add(new PropertyConditon("使用性质", "key_unit", "partition_structure_mono.usage_type;;partition_structure_group.usage_type;;partition_device.usage_type", String.class, PropertyConditon.TYPE_LIST, LvApplication.getInstance().getCompDicMap().get("ZDDW_SYXZ"), true));
//        pdListDetailForFilter.add(new PropertyConditon("建筑结构", "key_unit", "partition_structure_mono.structure_type;;partition_structure_group.structure_type;;partition_device.structure_type;;partition_storage_zone.structure_type", String.class, PropertyConditon.TYPE_LIST, LvApplication.getInstance().getCompDicMap().get("ZDDW_JZJG"), true));
//        pdListDetailForFilter.add(new PropertyConditon("地上高度(m)", "key_unit", "partition_structure_mono.height_overground;;partition_structure_group.height_overground", Double.class, PropertyConditon.TYPE_EDIT_NUMBER));
//        pdListDetailForFilter.add(new PropertyConditon("地下高度(m)", "key_unit", "partition_structure_mono.height_underground;;partition_structure_group.height_underground", Double.class, PropertyConditon.TYPE_EDIT_NUMBER));
//        pdListDetailForFilter.add(new PropertyConditon("地上层数", "key_unit", "partition_structure_mono.floor_overground;;partition_structure_group.floor_overground", Integer.class, PropertyConditon.TYPE_EDIT_NUMBER));
//        pdListDetailForFilter.add(new PropertyConditon("地下层数", "key_unit", "partition_structure_mono.floor_underground;;partition_structure_group.floor_underground", Integer.class, PropertyConditon.TYPE_EDIT_NUMBER));
        //ID:837818 19-01-09 【总分区】创建及用途对应 王泉 结束

        return pdListDetailForFilter;
    }
    public static String getTableNameForFilter() {
        //ID:837818 19-01-09 【总分区】创建及用途对应 王泉 开始
        return "key_unit LEFT OUTER JOIN key_unit_region_partition on key_unit_region_partition.key_unit_region_uuid = key_unit.uuid " +
//                "LEFT OUTER JOIN partition_structure_mono on partition_structure_mono.uuid = key_unit_region_partition.partition_uuid " +
//                "LEFT OUTER JOIN partition_structure_group on partition_structure_group.uuid = key_unit_region_partition.partition_uuid " +
                "LEFT OUTER JOIN partition_structure on partition_structure.uuid = key_unit_region_partition.partition_uuid " +
                "LEFT OUTER JOIN partition_device on partition_device.uuid = key_unit_region_partition.partition_uuid " +
                "LEFT OUTER JOIN partition_storage_zone on partition_storage_zone.uuid = key_unit_region_partition.partition_uuid";
        //ID:837818 19-01-09 【总分区】创建及用途对应 王泉 结束
    }
    public static String getSelectColumnForFilter() {
        //ID:837818 19-01-09 【总分区】创建及用途对应 王泉
        //ID:B903427 19-03-06 【统计】重点单位高级查询数据重复 王泉
        return "distinct key_unit.*";
//        return "distinct key_unit.uuid, key_unit.*, partition_structure.*, partition_device.*, partition_storage_zone.*";
    }

    @Override
    public String getOutline() {
        String outline = "";

        outline += "消防管理人：<font color=#0074C7>" + StringUtil.get(managePerson) + "  " + StringUtil.get(managePersonContact) + "</font>" + Constant.OUTLINE_DIVIDER;
        outline += "值班电话：<font color=#0074C7>" + StringUtil.get(dutyTelephone) + "</font>"  +  Constant.OUTLINE_DIVIDER;
        outline += "灭火责任队站：<font color=#0074C7>" + DictionaryUtil.getValueByCode(extinguishStationUuid, GeomEleBussiness.getInstance().getStationSquadronDic(belongtoGroup)) + "</font>"  + Constant.OUTLINE_DIVIDER;
        outline += "防火级别：<font color=#0074C7>" + DictionaryUtil.getValueByCode(preventionGrade, LvApplication.getInstance().getCompDicMap().get("ZDDW_FHJB")) + "  "
                + DictionaryUtil.getValueByCode(structureFlag, LvApplication.getInstance().getKeyUnitStructureDicList()) + "</font>"  + Constant.OUTLINE_DIVIDER;
        outline += "&" + "值班电话：" + StringUtil.get(dutyTelephone) +  "&"
                + "消防责任人(" + StringUtil.get(responsiblePerson) + ")：" + StringUtil.get(responsiblePersonContact) + "&"
                + "消防管理人(" + StringUtil.get(managePerson) + ")：" + StringUtil.get(managePersonContact) + "&"
                + "防火监督员(" + StringUtil.get(supervisorPerson) + ")：" + StringUtil.get(supervisorPersonContact) + "&"
                + "消防力量：" + StringUtil.get(fireForceUnitTel) + "&"
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

    @Override
    public void setAdapter() {
        if(StringUtil.isEmpty(departmentUuid)) {
            PrefUserInfo pui = PrefUserInfo.getInstance();
            departmentUuid = pui.getUserInfo("department_uuid");
        }
    }

    @Override
    public PrimaryAttribute getPrimaryValues() {
        PrimaryAttribute attribute = new PrimaryAttribute();
        attribute.setPrimaryValue1(name);
        attribute.setPrimaryValue2(DictionaryUtil.getValueByCode(extinguishStationUuid,
                GeomEleBussiness.getInstance().getStationSquadronDic(belongtoGroup)));
        attribute.setPrimaryValue3(address);

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);
        nf.setMinimumFractionDigits(0);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        nf.setGroupingUsed(false);
        attribute.setPrimaryValue4(nf.format(dataQuality*100)+ "%");

        return  attribute;
    }

    public String getUuid() {
        return uuid;
        }

    public void setUuid(String uuid) {
        this.uuid = uuid;
        }

    /**
     * 获取编码

     * 
     * @return 编码

     */
    public String getCode() {
        return this.code;
    }

    /**
     * 设置编码

     * 
     * @param code
     *          编码

     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取单位名称

     * 
     * @return 单位名称

     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置单位名称

     * 
     * @param name
     *          单位名称

     */
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

    /**
     * 获取数据所属区域

     * 
     * @return 数据所属区域

     */
    public String getDepartmentUuid() {
        return this.departmentUuid;
    }

    /**
     * 设置数据所属区域

     * 
     * @param departmentUuid
     *          数据所属区域

     */
    public void setDepartmentUuid(String departmentUuid) {
        this.departmentUuid = departmentUuid;
    }

    /**
     * 获取防火级别

     * 
     * @return 防火级别

     */
    public String getPreventionGrade() {
        return this.preventionGrade;
    }

    /**
     * 设置防火级别

     * 
     * @param preventionGrade
     *          防火级别

     */
    public void setPreventionGrade(String preventionGrade) {
        this.preventionGrade = preventionGrade;
    }

    /**
     * 获取防火队站uuid

     * 
     * @return 防火队站uuid

     */
    public String getPreventionStationUuid() {
        return this.preventionStationUuid;
    }

    /**
     * 设置防火队站uuid

     * 
     * @param preventionStationUuid
     *          防火队站uuid

     */
    public void setPreventionStationUuid(String preventionStationUuid) {
        this.preventionStationUuid = preventionStationUuid;
    }

    /**
     * 获取灭火队站uuid

     * 
     * @return 灭火队站uuid

     */
    public String getExtinguishStationUuid() {
        return this.extinguishStationUuid;
    }

    /**
     * 设置灭火队站uuid

     * 
     * @param extinguishStationUuid
     *          灭火队站uuid

     */
    public void setExtinguishStationUuid(String extinguishStationUuid) {
        this.extinguishStationUuid = extinguishStationUuid;
    }

    /**
     * 获取单位性质标识

     * 
     * @return 单位性质标识

     */
    public String getCharacterUuid() {
        return this.characterUuid;
    }

    /**
     * 设置单位性质标识

     * 
     * @param characterUuid
     *          单位性质标识

     */
    public void setCharacterUuid(String characterUuid) {
        this.characterUuid = characterUuid;
    }

    /**
     * 获取行政区划编码

     * 
     * @return 行政区划编码

     */
    public String getDistrictCode() {
        return this.districtCode;
    }

    /**
     * 设置行政区划编码

     * 
     * @param districtCode
     *          行政区划编码

     */
    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    /**
     * 获取单位地址

     *
     * @return 单位地址

     */
    public String getAddress() {
        return this.address;
    }

    /**
     * 设置单位地址

     * 
     * @param address
     *          单位地址

     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取路/街

     * 
     * @return 路/街

     */
    public String getAddrRoad() {
        return this.addrRoad;
    }

    /**
     * 设置路/街

     * 
     * @param addrRoad
     *          路/街

     */
    public void setAddrRoad(String addrRoad) {
        this.addrRoad = addrRoad;
    }

    /**
     * 获取号

     * 
     * @return 号

     */
    public String getAddrNumber() {
        return this.addrNumber;
    }

    /**
     * 设置号

     * 
     * @param addrNumber
     *          号

     */
    public void setAddrNumber(String addrNumber) {
        this.addrNumber = addrNumber;
    }

    /**
     * 获取消防负责人

     * 
     * @return 消防负责人

     */
    public String getResponsiblePerson() {
        return this.responsiblePerson;
    }

    /**
     * 设置消防负责人

     * 
     * @param responsiblePerson
     *          消防负责人

     */
    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    /**
     * 获取消防负责人联系方式

     * 
     * @return 消防负责人联系方式

     */
    public String getResponsiblePersonContact() {
        return this.responsiblePersonContact;
    }

    /**
     * 设置消防负责人联系方式

     * 
     * @param responsiblePersonContact
     *          消防负责人联系方式

     */
    public void setResponsiblePersonContact(String responsiblePersonContact) {
        this.responsiblePersonContact = responsiblePersonContact;
    }

    /**
     * 获取消防管理人

     * 
     * @return 消防管理人

     */
    public String getManagePerson() {
        return this.managePerson;
    }

    /**
     * 设置消防管理人

     * 
     * @param managePerson
     *          消防管理人

     */
    public void setManagePerson(String managePerson) {
        this.managePerson = managePerson;
    }

    /**
     * 获取消防管理人联系方式

     * 
     * @return 消防管理人联系方式

     */
    public String getManagePersonContact() {
        return this.managePersonContact;
    }

    /**
     * 设置消防管理人联系方式

     * 
     * @param managePersonContact
     *          消防管理人联系方式

     */
    public void setManagePersonContact(String managePersonContact) {
        this.managePersonContact = managePersonContact;
    }

    /**
     * 获取单位值班电话

     * 
     * @return 单位值班电话

     */
    public String getDutyTelephone() {
        return this.dutyTelephone;
    }

    /**
     * 设置单位值班电话

     * 
     * @param dutyTelephone
     *          单位值班电话

     */
    public void setDutyTelephone(String dutyTelephone) {
        this.dutyTelephone = dutyTelephone;
    }

    public Boolean getHasFireForce() {
        return hasFireForce;
    }

    public void setHasFireForce(Boolean hasFireForce) {
        this.hasFireForce = hasFireForce;
    }

    public String getFireForceType() {
        return fireForceType;
    }

    public void setFireForceType(String fireForceType) {
        this.fireForceType = fireForceType;
    }

    public String getFireForceUnitTel() {
        return fireForceUnitTel;
    }

    public void setFireForceUnitTel(String fireForceUnitTel) {
        this.fireForceUnitTel = fireForceUnitTel;
    }

    public Integer getFireForceDutyVehicle() {
        return fireForceDutyVehicle;
    }

    public void setFireForceDutyVehicle(Integer fireForceDutyVehicle) {
        this.fireForceDutyVehicle = fireForceDutyVehicle;
    }

    public Integer getFireForceDutyNum() {
        return fireForceDutyNum;
    }

    public void setFireForceDutyNum(Integer fireForceDutyNum) {
        this.fireForceDutyNum = fireForceDutyNum;
    }

    /**
     * 获取单位概况

     * 
     * @return 单位概况

     */
    public String getUnitOverview() {
        return this.unitOverview;
    }

    /**
     * 设置单位概况

     * 
     * @param unitOverview
     *          单位概况

     */
    public void setUnitOverview(String unitOverview) {
        this.unitOverview = unitOverview;
    }

    /**
     * 获取建筑情况

     * 
     * @return 建筑情况

     */
    public String getStructureFlag() {
        return this.structureFlag;
    }

    /**
     * 设置建筑情况

     * 
     * @param structureFlag
     *          建筑情况

     */
    public void setStructureFlag(String structureFlag) {
        this.structureFlag = structureFlag;
    }

    /**
     * 获取有无全景

     * 
     * @return 有无全景

     */
    public Boolean getHasPano() {
        return this.hasPano;
    }

    /**
     * 设置有无全景

     * 
     * @param hasPano
     *          有无全景

     */
    public void setHasPano(Boolean hasPano) {
        this.hasPano = hasPano;
    }

    /**
     * 获取有无预案

     * 
     * @return 有无预案

     */
    public Boolean getHasRescue() {
        return this.hasRescue;
    }

    /**
     * 设置有无预案

     * 
     * @param hasRescue
     *          有无预案

     */
    public void setHasRescue(Boolean hasRescue) {
        this.hasRescue = hasRescue;
    }

    /**
     * 获取有无三维

     * 
     * @return 有无三维

     */
    public Boolean getHasMx() {
        return this.hasMx;
    }

    /**
     * 设置有无三维

     * 
     * @param hasMx
     *          有无三维

     */
    public void setHasMx(Boolean hasMx) {
        this.hasMx = hasMx;
    }

    public String getAdjacentEast() {
        return adjacentEast;
    }

    public void setAdjacentEast(String adjacentEast) {
        this.adjacentEast = adjacentEast;
    }

    public String getAdjacentWest() {
        return adjacentWest;
    }

    public void setAdjacentWest(String adjacentWest) {
        this.adjacentWest = adjacentWest;
    }

    public String getAdjacentSouth() {
        return adjacentSouth;
    }

    public void setAdjacentSouth(String adjacentSouth) {
        this.adjacentSouth = adjacentSouth;
    }

    public String getAdjacentNorth() {
        return adjacentNorth;
    }

    public void setAdjacentNorth(String adjacentNorth) {
        this.adjacentNorth = adjacentNorth;
    }

    public String getRouteFromStation() {
        return routeFromStation;
    }

    public void setRouteFromStation(String routeFromStation) {
        this.routeFromStation = routeFromStation;
    }

    public Integer getTimeStationArrive() {
        return timeStationArrive;
    }

    public void setTimeStationArrive(Integer timeStationArrive) {
        this.timeStationArrive = timeStationArrive;
    }

    public Double getFloorArea() {
        return floorArea;
    }

    public void setFloorArea(Double floorArea) {
        this.floorArea = floorArea;
    }

    public Double getStructureArea() {
        return structureArea;
    }

    public void setStructureArea(Double structureArea) {
        this.structureArea = structureArea;
    }

    /**
     * 获取删除标识
     * 
     * @return 删除标识
     */
    public Boolean getDeleted() {
        return this.deleted;
    }

    /**
     * 设置删除标识
     * 
     * @param deleted
     *          删除标识
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取数据版本

     * 
     * @return 数据版本

     */
    public Integer getVersion() {
        return this.version;
    }

    /**
     * 设置数据版本

     * 
     * @param version
     *          数据版本

     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 获取提交人

     * 
     * @return 提交人

     */
    public String getCreatePerson() {
        return this.createPerson;
    }

    /**
     * 设置提交人

     * 
     * @param createPerson
     *          提交人

     */
    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    /**
     * 获取创建时间

     * 
     * @return 创建时间

     */
    public Date getCreateDate() {
        return this.createDate;
    }

    /**
     * 设置创建时间

     * 
     * @param createDate
     *          创建时间

     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取最新更新者

     * 
     * @return 最新更新者

     */
    public String getUpdatePerson() {
        return this.updatePerson;
    }

    /**
     * 设置最新更新者

     * 
     * @param updatePerson
     *          最新更新者

     */
    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    /**
     * 获取更新时间

     * 
     * @return 更新时间

     */
    public Date getUpdateDate() {
        return this.updateDate;
    }

    /**
     * 设置更新时间

     * 
     * @param updateDate
     *          更新时间

     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取备注

     * 
     * @return 备注

     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置备注

     * 
     * @param remark
     *          备注

     */
    public void setRemark(String remark) {
        this.remark = remark;
    }


    /**
     * 获取关键字查询

     * 
     * @return 关键字查询

     */
    public String getKeywords() {
        return this.keywords;
    }

    /**
     * 设置关键字查询

     * 
     * @param keywords
     *          关键字查询

     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
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

    public Double getDataQuality() {
        return dataQuality;
    }

    public void setDataQuality(Double dataQuality) {
        this.dataQuality = dataQuality;
    }

    public String getFireForceRemark() {
        return fireForceRemark;
    }

    public void setFireForceRemark(String fireForceRemark) {
        this.fireForceRemark = fireForceRemark;
    }

    public String getResponsiblePersonIdCard() {
        return responsiblePersonIdCard;
    }

    public void setResponsiblePersonIdCard(String responsiblePersonIdCard) {
        this.responsiblePersonIdCard = responsiblePersonIdCard;
    }

    public String getManagePersonIdCard() {
        return managePersonIdCard;
    }

    public void setManagePersonIdCard(String managePersonIdCard) {
        this.managePersonIdCard = managePersonIdCard;
    }

    public String getSupervisorPerson() {
        return supervisorPerson;
    }

    public void setSupervisorPerson(String supervisorPerson) {
        this.supervisorPerson = supervisorPerson;
    }

    public String getSupervisorPersonContact() {
        return supervisorPersonContact;
    }

    public void setSupervisorPersonContact(String supervisorPersonContact) {
        this.supervisorPersonContact = supervisorPersonContact;
    }

    public String getFunctionalDescription() {
        return functionalDescription;
    }

    public void setFunctionalDescription(String functionalDescription) {
        this.functionalDescription = functionalDescription;
    }

    @Override
    public String toString() {
        String str = StringUtil.get(code) + String.format("|%s", StringUtil.get(name)) + "|重点单位" +
                String.format("|%s",  DictionaryUtil.getValueByCode(preventionGrade, LvApplication.getInstance().getCompDicMap().get("ZDDW_FHJB"))) +
                String.format("|%s",  DictionaryUtil.getValueByCode(preventionStationUuid, GeomEleBussiness.getInstance().getStationBattalionDic(belongtoGroup))) +
                String.format("|%s",  DictionaryUtil.getValueByCode(extinguishStationUuid, GeomEleBussiness.getInstance().getStationSquadronDic(belongtoGroup))) +
                String.format("|%s",  DictionaryUtil.getValueByCode(characterUuid, LvApplication.getInstance().getCompDicMap().get("ZDDW_DWXZ"))) +
                String.format("|%s",  DictionaryUtil.getValueByCode(structureFlag, LvApplication.getInstance().getKeyUnitStructureDicList())) +
                String.format("|%s",  StringUtil.get(address)) +
                String.format("|%s",  StringUtil.get(addrRoad)) +
                String.format("|%s",  StringUtil.get(addrNumber)) +
                String.format("|%s",  StringUtil.get(responsiblePerson)) +
                String.format("|%s",  StringUtil.get(managePerson)) +
                String.format("|%s",  StringUtil.get(unitOverview)) +
                String.format("|%s",  StringUtil.get(adjacentEast)) +
                String.format("|%s",  StringUtil.get(adjacentWest)) +
                String.format("|%s",  StringUtil.get(adjacentSouth)) +
                String.format("|%s",  StringUtil.get(adjacentNorth)) +
                String.format("|%s",  StringUtil.get(routeFromStation)) +
                String.format("|%s",  StringUtil.get(structureArea)) +
                String.format("|%s",  StringUtil.get(floorArea));
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