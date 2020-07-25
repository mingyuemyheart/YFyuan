package com.moft.xfapply.model.entity;

import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
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

/*
 * @Author: 宋满意
 * @Date:   2018-06-01 17:30:53
 * @Last Modified by:   宋满意
 * @Last Modified time: 2019-02-12 16:08:16
 * No.              Date.          Modifier    Description
 * 【S869489】      2019-02-12     宋满意       车辆信息以分类形式展示
 * 【T900369】      2019-03-04     宋满意       车辆的“所属机构”字段隐藏不再显示
 */
@Table(name = "material_fire_vehicle")
public class MaterialFireVehicleDBInfo implements IGeomElementInfo {
    /** 版本号 */
    @Transient
    private static final long serialVersionUID = 8840503960087631779L;

    @Id
    private String  uuid; // 唯一标识
    private String  code; // 编码
    @Property(column = "department_uuid")
    private String  departmentUuid; // 机构标识
    private String  name; // 名称
    @Property(column = "ele_type")
    private String  eleType; // 分类
    private Boolean deleted; // 删除标识
    @Property(column = "create_person")
    private String  createPerson; // 提交人
    @Property(column = "update_person")
    private String  updatePerson; // 最新更新者
    @Property(column = "create_date")
    private Date    createDate; // 创建时间
    @Property(column = "update_date")
    private Date    updateDate; // 更新时间
    private String  remark; // 备注
    @Property(column = "belongto_group")
    private String  belongtoGroup; //城市
    @Property(column = "data_quality")
    private Double dataQuality;
    private String keywords; //关键字查询


    /** 所属队站 */
    @Property(column = "station_uuid")
    private String stationUuid;
    /** 区县 */
    @Property(column = "owner_type")
    private String ownerType;
    /** 车牌号 */
    @Property(column = "plate_number")
    private String plateNumber;
    /** 车辆类型的标识 */
    @Property(column = "type_uuid")
    private String typeUuid;
    /** 水泵流量（L/s） */
    @Property(column = "pump_capacity")
    private Double pumpCapacity;
    /** 消防炮流量（L/s） */
    @Property(column = "fire_artillery_capacity")
    private Double fireArtilleryCapacity;
    /** 水泵额定压力（Mpa） */
    @Property(column = "pump_rated_pressure")
    private Double pumpRatedPressure;
    /** 是否有车载灭火剂 */
    @Property(column = "has_extinguishing_agent")
    private Boolean hasExtinguishingAgent;
    /** 是否有车载器材 */
    @Property(column = "has_equipment")
    private Boolean hasEquipment;
    /** GPS编号 */
    @Property(column = "gps_code")
    private String gpsCode;
    /** 载水量（t） */
    private Double capacity;
    /** 车辆状态标识 */
    @Property(column = "state_uuid")
    private String stateUuid;
    /** 生产厂家 */
    private String manufacturer;
    /** 举高类车辆高度（米） */
    @Property(column = "lift_height")
    private Double liftHeight;
    /** 数据版本 */
    private Integer version;
    /** 生产厂家代码 */
    @Property(column = "manufacturer_code")
    private String manufacturerCode;
    /** 进口装备国内代理商 */
    @Property(column = "domestic_agent")
    private String domesticAgent;
    /** 售后服务 */
    @Property(column = "after_sale_service")
    private String afterSaleService;
    /** 责任人ID */
    @Property(column = "responsible_person_id")
    private String responsiblePersonId;
    /** 责任人姓名 */
    @Property(column = "responsible_person_name")
    private String responsiblePersonName;
    /** 出厂日期 */
    @Property(column = "production_date")
    private Date productionDate;
    /** 装备日期 */
    @Property(column = "equipment_date")
    private Date equipmentDate;
    /** 报废日期 */
    @Property(column = "discarded_date")
    private Date discardedDate;
    /** 有效期至 */
    private Date period;
    /** 累计运输时间 */
    @Property(column = "transport_time")
    private Double transportTime;
    /** 累计使用次数 */
    @Property(column = "cumulative_use")
    private Integer cumulativeUse;
    /** 本次保养日期 */
    @Property(column = "maintenance_date")
    private Date maintenanceDate;
    /** 下次保养日期 */
    @Property(column = "next_maintenance_date")
    private Date nextMaintenanceDate;
    /** 车架号 */
    @Property(column = "frame_number")
    private String frameNumber;
    /** 发动机编号 */
    @Property(column = "engine_number")
    private String engineNumber;
    /** 批次号 */
    @Property(column = "batch_number")
    private String batchNumber;
    /** 计量单位代码 */
    @Property(column = "measurement_unit_code")
    private String measurementUnitCode;
    /** 是否用于训练 */
    private Boolean training;
    /** 锁定状态 */
    @Property(column = "lock_state")
    private String lockState;
    /** 电台呼号 */
    @Property(column = "radio_call_sign")
    private String radioCallSign;
    /** 案件编号 */
    @Property(column = "case_number")
    private String caseNumber;
    /** 是否装配 */
    private Boolean assemble;
    /** 是否跨支队 */
    @Property(column = "cross_the_branch")
    private Boolean crossTheBranch;
    /** 是否跨总队 */
    @Property(column = "cross_the_brigade")
    private Boolean crossTheBrigade;
    /** 车辆简称 */
    @Property(column = "vehicle_abbreviation")
    private String vehicleAbbreviation;
    /** 规格号码 */
    @Property(column = "specification_number")
    private String specificationNumber;
    /** 资产编号 */
    @Property(column = "asset_number")
    private String assetNumber;
    /** 参考价 */
    @Property(column = "reference_price")
    private String referencePrice;
    /** 商标 */
    private String trademark;
    /** 颜色 */
    private String colour;
    /** 国别代码 */
    @Property(column = "country_code")
    private String countryCode;

    @Transient
    private List<PropertyDes> pdListDetail = new ArrayList<PropertyDes>();

    public MaterialFireVehicleDBInfo() {
        pumpCapacity = 0d;
        capacity = 0d;
        liftHeight = 0d;
        fireArtilleryCapacity = 0d;
        pumpRatedPressure = 0d;
        hasExtinguishingAgent = true;
        hasEquipment = true;
    }

    public List<PropertyDes> getPdListDetail() {
        pdListDetail.clear();

        pdListDetail.add(new PropertyDes("车辆编码", "setCode", String.class, code, PropertyDes.TYPE_TEXT));
        // ID :886069  【数据】车辆、器材、灭火剂、输入类别和名称，顺序对调 2019-02-20 王旭 START
        pdListDetail.add(new PropertyDes("车辆类别*", "setTypeUuid", String.class, typeUuid, LvApplication.getInstance().getCompDicMap().get("JTGJ"), true, true));
        // ID :886069  【数据】车辆、器材、灭火剂、输入类别和名称，顺序对调 2019-02-20 王旭 END
        pdListDetail.add(new PropertyDes("车辆名称*", "setName", String.class, name, PropertyDes.TYPE_EDIT, true));
        //TODO:目前获取中队队站，将来修改成获取当前组织下的队站，同步修改getPrimaryValues
        pdListDetail.add(new PropertyDes("所属队站*", "setStationUuid", String.class, stationUuid, GeomEleBussiness.getInstance().getStationSquadronDic(belongtoGroup), true, true));
        pdListDetail.add(new PropertyDes("有无车载灭火剂", "setHasExtinguishingAgent", Boolean.class, hasExtinguishingAgent, LvApplication.getInstance().getHasOrNohasDicList()));
        pdListDetail.add(new PropertyDes("有无车载器材", "setHasEquipment", Boolean.class, hasEquipment, LvApplication.getInstance().getHasOrNohasDicList()));

        // 900369 【移动终端】车辆的“所属机构”字段隐藏不再显示 smy 2019-03-04
        // pdListDetail.add(new PropertyDes("所属机构", "setOwnerType", String.class, ownerType, LvApplication.getInstance().getCompDicMap().get("CL_SSJG")));

        //ID:S869489 19-02-12 车辆信息以分类形式展示（分为基本类信息，作战用信息，管理用信息），同理包括器材、灭火剂（基本信息、详细信息） 宋满意 开始
        pdListDetail.add(new PropertyDes("作战用信息", uuid, true, PropertyDes.TYPE_DETAIL, PropertyDes.PropertyDetail.DETAIL, MaterialFireVehicleDBInfo.class));
        pdListDetail.add(new PropertyDes("水泵流量(L/s)", "setPumpCapacity", Double.class, uuid, PropertyDes.PropertyDetail.DETAIL, pumpCapacity, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("消防炮流量(L/s)", "setFireArtilleryCapacity", Double.class, uuid, PropertyDes.PropertyDetail.DETAIL, fireArtilleryCapacity, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("水泵额定压力(Mpa)", "setPumpRatedPressure", Double.class, uuid, PropertyDes.PropertyDetail.DETAIL, pumpRatedPressure, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("载水量(t)", "setCapacity", Double.class, uuid, PropertyDes.PropertyDetail.DETAIL, capacity, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("举高类车辆高度(m)", "setLiftHeight", Double.class, uuid, PropertyDes.PropertyDetail.DETAIL, liftHeight, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("车辆状态", "setStateUuid", String.class, uuid, PropertyDes.PropertyDetail.DETAIL, stateUuid, LvApplication.getInstance().getCompDicMap().get("CL_ZT")));
        pdListDetail.add(new PropertyDes("备注", "setRemark", String.class, uuid, PropertyDes.PropertyDetail.DETAIL, remark, PropertyDes.TYPE_EDIT));

        pdListDetail.add(new PropertyDes("管理用信息", uuid, false, PropertyDes.TYPE_DETAIL, PropertyDes.PropertyDetail.MANAGEMENT, MaterialFireVehicleDBInfo.class));
        pdListDetail.add(new PropertyDes("GPS编码", "setGpsCode", String.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, gpsCode, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("车牌号", "setPlateNumber", String.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, plateNumber, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("生产厂家名称", "setManufacturer", String.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, manufacturer, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("生产厂家代码", "setManufacturerCode", String.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, manufacturerCode, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("进口装备国内代理商", "setDomesticAgent", String.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, domesticAgent, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("售后服务", "setAfterSaleService", String.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, afterSaleService, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("责任人ID", "setResponsiblePersonId", String.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, responsiblePersonId, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("责任人姓名", "setResponsiblePersonName", String.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, responsiblePersonName, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("出厂日期", "setProductionDate", Date.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, productionDate, PropertyDes.TYPE_DATE));
        pdListDetail.add(new PropertyDes("装备日期", "setEquipmentDate", Date.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, equipmentDate, PropertyDes.TYPE_DATE));
        pdListDetail.add(new PropertyDes("报废日期", "setDiscardedDate", Date.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, discardedDate, PropertyDes.TYPE_DATE));
        pdListDetail.add(new PropertyDes("有效期至", "setPeriod", Date.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, period, PropertyDes.TYPE_DATE));
        pdListDetail.add(new PropertyDes("累计运输时间", "setTransportTime", Double.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, transportTime, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("累计使用次数", "setCumulativeUse", Integer.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, cumulativeUse, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("本次保养日期", "setMaintenanceDate", Date.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, maintenanceDate, PropertyDes.TYPE_DATE));
        pdListDetail.add(new PropertyDes("下次保养日期", "setNextMaintenanceDate", Date.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, nextMaintenanceDate, PropertyDes.TYPE_DATE));
        pdListDetail.add(new PropertyDes("车架号", "setFrameNumber", String.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, frameNumber, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("发动机编号", "setEngineNumber", String.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, engineNumber, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("批次号", "setBatchNumber", String.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, batchNumber, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("计量单位代码", "setMeasurementUnitCode", String.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, measurementUnitCode, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("是否用于训练", "setTraining", Boolean.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, training, LvApplication.getInstance().getWhetherDicList()));
        pdListDetail.add(new PropertyDes("锁定状态", "setLockState", String.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, lockState, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("电台呼号", "setRadioCallSign", String.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, radioCallSign, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("案件编号", "setCaseNumber", String.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, caseNumber, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("是否装配", "setAssemble", Boolean.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, assemble, LvApplication.getInstance().getWhetherDicList()));
        pdListDetail.add(new PropertyDes("是否跨支队", "setCrossTheBranch", Boolean.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, crossTheBranch, LvApplication.getInstance().getWhetherDicList()));
        pdListDetail.add(new PropertyDes("是否跨总队", "setCrossTheBrigade", Boolean.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, crossTheBrigade, LvApplication.getInstance().getWhetherDicList()));
        pdListDetail.add(new PropertyDes("车辆简称", "setVehicleAbbreviation", String.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, vehicleAbbreviation, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("规格号码", "setSpecificationNumber", String.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, specificationNumber, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("资产编号", "setAssetNumber", String.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, assetNumber, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("参考价", "setReferencePrice", String.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, referencePrice, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("商标", "setTrademark", String.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, trademark, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("颜色", "setColour", String.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, colour, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("国别代码", "setCountryCode", String.class, uuid, PropertyDes.PropertyDetail.MANAGEMENT, countryCode, PropertyDes.TYPE_EDIT));
        //ID:S869489 19-02-12 车辆信息以分类形式展示（分为基本类信息，作战用信息，管理用信息），同理包括器材、灭火剂（基本信息、详细信息） 宋满意 结束

        return pdListDetail;
    }

    @Transient
    private static List<PropertyConditon> pdListDetailForFilter = new ArrayList<PropertyConditon>();
    public static List<PropertyConditon> getPdListDetailForFilter() {
        pdListDetailForFilter.clear();

        pdListDetailForFilter.add(new PropertyConditon("车辆类型", "material_fire_vehicle", "type_uuid", String.class, PropertyConditon.TYPE_LIST, LvApplication.getInstance().getCompDicMap().get("JTGJ"), true));
        pdListDetailForFilter.add(new PropertyConditon("水泵流量(L/s)", "material_fire_vehicle", "pump_capacity", Double.class, PropertyConditon.TYPE_EDIT_NUMBER));
        pdListDetailForFilter.add(new PropertyConditon("载水量(t)", "material_fire_vehicle", "capacity", Double.class, PropertyConditon.TYPE_EDIT_NUMBER));
        pdListDetailForFilter.add(new PropertyConditon("举高类车辆高度(m)", "material_fire_vehicle", "lift_height", Double.class, PropertyConditon.TYPE_EDIT_NUMBER));
        pdListDetailForFilter.add(new PropertyConditon("车辆状态", "material_fire_vehicle", "state_uuid", Double.class, PropertyConditon.TYPE_LIST, LvApplication.getInstance().getCompDicMap().get("CL_ZT"), true));

        return pdListDetailForFilter;
    }
    public static String getTableNameForFilter() {
        return "material_fire_vehicle";
    }
    public static String getSelectColumnForFilter() {
        return "*";
    }

    @Override
    public String getOutline() {
        String outline = "";

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
        PrefUserInfo pui = PrefUserInfo.getInstance();

        if(StringUtil.isEmpty(departmentUuid)) {
            departmentUuid = pui.getUserInfo("department_uuid");
        }
        code = StringUtil.get(pui.getUserInfo("department_code")) + "-" + getTypeCode();
    }

    public PrimaryAttribute getPrimaryValues() {
        PrimaryAttribute attribute = new PrimaryAttribute();
        attribute.setPrimaryValue1(name);
        if(!StringUtil.isEmpty(plateNumber)) {
            attribute.setPrimaryValue2("[" + plateNumber + "]");
        }
        attribute.setPrimaryValue3(DictionaryUtil.getValueByCode(stationUuid,
                GeomEleBussiness.getInstance().getStationSquadronDic(belongtoGroup)));

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);
        nf.setMinimumFractionDigits(0);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        nf.setGroupingUsed(false);
        attribute.setPrimaryValue4(nf.format(dataQuality*100)+ "%");

        return  attribute;
    }

    /**
     * 获取唯一标识
     *
     * @return 唯一标识
     */
    public String getUuid() {
        return this.uuid;
    }

    /**
     * 设置唯一标识
     *
     * @param uuid 唯一标识
     */
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
    public String getEleType() {
        return eleType;
    }

    @Override
    public void setEleType(String eleType) {
        this.eleType = eleType;
    }

    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public void setAddress(String address) {

    }

    @Override
    public String getGeometryText() {
        return null;
    }

    @Override
    public void setGeometryText(String geometryText) {

    }

    @Override
    public String getLongitude() {
        return null;
    }

    @Override
    public void setLongitude(String longitude) {

    }

    @Override
    public String getLatitude() {
        return null;
    }

    @Override
    public void setLatitude(String latitude) {

    }

    public String getGeometryType() {
        return null;
    }

    public void setGeometryType(String geometryType) {

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
    public Double getDataQuality() {
        return dataQuality;
    }

    @Override
    public void setDataQuality(Double dataQuality) {
        this.dataQuality = dataQuality;
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
     * 获取名称
     * 
     * @return 名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置名称
     * 
     * @param name
     *          名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取所属队站
     * 
     * @return 所属队站
     */
    public String getStationUuid() {
        return this.stationUuid;
    }

    /**
     * 设置所属队站
     * 
     * @param stationUuid
     *          所属队站
     */
    public void setStationUuid(String stationUuid) {
        this.stationUuid = stationUuid;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    /**
     * 获取车牌号
     * 
     * @return 车牌号
     */
    public String getPlateNumber() {
        return this.plateNumber;
    }

    /**
     * 设置车牌号
     * 
     * @param plateNumber
     *          车牌号
     */
    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    /**
     * 获取车辆类型的标识
     * 
     * @return 车辆类型的标识
     */
    public String getTypeUuid() {
        return this.typeUuid;
    }

    /**
     * 设置车辆类型的标识
     * 
     * @param typeUuid
     *          车辆类型的标识
     */
    public void setTypeUuid(String typeUuid) {
        this.typeUuid = typeUuid;
    }

    /**
     * 获取水泵流量（L/s）
     * 
     * @return 水泵流量（L/s）
     */
    public Double getPumpCapacity() {
        return this.pumpCapacity;
    }

    /**
     * 设置水泵流量（L/s）
     * 
     * @param pumpCapacity
     *          水泵流量（L/s）
     */
    public void setPumpCapacity(Double pumpCapacity) {
        this.pumpCapacity = pumpCapacity;
    }

    /**
     * 获取消防炮流量（L/s）
     * 
     * @return 消防炮流量（L/s）
     */
    public Double getFireArtilleryCapacity() {
        return this.fireArtilleryCapacity;
    }

    /**
     * 设置消防炮流量（L/s）
     * 
     * @param fireArtilleryCapacity
     *          消防炮流量（L/s）
     */
    public void setFireArtilleryCapacity(Double fireArtilleryCapacity) {
        this.fireArtilleryCapacity = fireArtilleryCapacity;
    }

    /**
     * 获取水泵额定压力（Mpa）
     * 
     * @return 水泵额定压力（Mpa）
     */
    public Double getPumpRatedPressure() {
        return this.pumpRatedPressure;
    }

    /**
     * 设置水泵额定压力（Mpa）
     * 
     * @param pumpRatedPressure
     *          水泵额定压力（Mpa）
     */
    public void setPumpRatedPressure(Double pumpRatedPressure) {
        this.pumpRatedPressure = pumpRatedPressure;
    }

    public Boolean getHasExtinguishingAgent() {
        return hasExtinguishingAgent;
    }

    public void setHasExtinguishingAgent(Boolean hasExtinguishingAgent) {
        this.hasExtinguishingAgent = hasExtinguishingAgent;
    }

    public Boolean getHasEquipment() {
        return hasEquipment;
    }

    public void setHasEquipment(Boolean hasEquipment) {
        this.hasEquipment = hasEquipment;
    }

    /**
     * 获取GPS编号
     * 
     * @return GPS编号
     */
    public String getGpsCode() {
        return this.gpsCode;
    }

    /**
     * 设置GPS编号
     * 
     * @param gpsCode
     *          GPS编号
     */
    public void setGpsCode(String gpsCode) {
        this.gpsCode = gpsCode;
    }

    /**
     * 获取载水量（t）
     * 
     * @return 载水量（t）
     */
    public Double getCapacity() {
        return this.capacity;
    }

    /**
     * 设置载水量（t）
     * 
     * @param capacity
     *          载水量（t）
     */
    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    /**
     * 获取车辆状态标识
     * 
     * @return 车辆状态标识
     */
    public String getStateUuid() {
        return this.stateUuid;
    }

    /**
     * 设置车辆状态标识
     * 
     * @param stateUuid
     *          车辆状态标识
     */
    public void setStateUuid(String stateUuid) {
        this.stateUuid = stateUuid;
    }

    /**
     * 获取生产厂家
     * 
     * @return 生产厂家
     */
    public String getManufacturer() {
        return this.manufacturer;
    }

    /**
     * 设置生产厂家
     * 
     * @param manufacturer
     *          生产厂家
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * 获取举高类车辆高度（米）
     * 
     * @return 举高类车辆高度（米）
     */
    public Double getLiftHeight() {
        return this.liftHeight;
    }

    /**
     * 设置举高类车辆高度（米）
     * 
     * @param liftHeight
     *          举高类车辆高度（米）
     */
    public void setLiftHeight(Double liftHeight) {
        this.liftHeight = liftHeight;
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
     * 获取keywords
     * 
     * @return keywords
     */
    public String getKeywords() {
        return this.keywords;
    }

    /**
     * 设置keywords
     * 
     * @param keywords
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    /**
     * 获取生产厂家代码
     * 
     * @return 生产厂家代码
     */
    public String getManufacturerCode() {
        return this.manufacturerCode;
    }

    /**
     * 设置生产厂家代码
     * 
     * @param manufacturerCode
     *          生产厂家代码
     */
    public void setManufacturerCode(String manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    /**
     * 获取进口装备国内代理商
     * 
     * @return 进口装备国内代理商
     */
    public String getDomesticAgent() {
        return this.domesticAgent;
    }

    /**
     * 设置进口装备国内代理商
     * 
     * @param domesticAgent
     *          进口装备国内代理商
     */
    public void setDomesticAgent(String domesticAgent) {
        this.domesticAgent = domesticAgent;
    }

    /**
     * 获取售后服务
     * 
     * @return 售后服务
     */
    public String getAfterSaleService() {
        return this.afterSaleService;
    }

    /**
     * 设置售后服务
     * 
     * @param afterSaleService
     *          售后服务
     */
    public void setAfterSaleService(String afterSaleService) {
        this.afterSaleService = afterSaleService;
    }

    /**
     * 获取责任人ID
     * 
     * @return 责任人ID
     */
    public String getResponsiblePersonId() {
        return this.responsiblePersonId;
    }

    /**
     * 设置责任人ID
     * 
     * @param responsiblePersonId
     *          责任人ID
     */
    public void setResponsiblePersonId(String responsiblePersonId) {
        this.responsiblePersonId = responsiblePersonId;
    }

    /**
     * 获取责任人姓名
     * 
     * @return 责任人姓名
     */
    public String getResponsiblePersonName() {
        return this.responsiblePersonName;
    }

    /**
     * 设置责任人姓名
     * 
     * @param responsiblePersonName
     *          责任人姓名
     */
    public void setResponsiblePersonName(String responsiblePersonName) {
        this.responsiblePersonName = responsiblePersonName;
    }

    /**
     * 获取出厂日期
     * 
     * @return 出厂日期
     */
    public Date getProductionDate() {
        return this.productionDate;
    }

    /**
     * 设置出厂日期
     * 
     * @param productionDate
     *          出厂日期
     */
    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    /**
     * 获取装备日期
     * 
     * @return 装备日期
     */
    public Date getEquipmentDate() {
        return this.equipmentDate;
    }

    /**
     * 设置装备日期
     * 
     * @param equipmentDate
     *          装备日期
     */
    public void setEquipmentDate(Date equipmentDate) {
        this.equipmentDate = equipmentDate;
    }

    /**
     * 获取报废日期
     * 
     * @return 报废日期
     */
    public Date getDiscardedDate() {
        return this.discardedDate;
    }

    /**
     * 设置报废日期
     * 
     * @param discardedDate
     *          报废日期
     */
    public void setDiscardedDate(Date discardedDate) {
        this.discardedDate = discardedDate;
    }

    /**
     * 获取有效期至
     * 
     * @return 有效期至
     */
    public Date getPeriod() {
        return this.period;
    }

    /**
     * 设置有效期至
     * 
     * @param period
     *          有效期至
     */
    public void setPeriod(Date period) {
        this.period = period;
    }

    /**
     * 获取累计运输时间
     * 
     * @return 累计运输时间
     */
    public Double getTransportTime() {
        return this.transportTime;
    }

    /**
     * 设置累计运输时间
     * 
     * @param transportTime
     *          累计运输时间
     */
    public void setTransportTime(Double transportTime) {
        this.transportTime = transportTime;
    }

    /**
     * 获取累计使用次数
     * 
     * @return 累计使用次数
     */
    public Integer getCumulativeUse() {
        return this.cumulativeUse;
    }

    /**
     * 设置累计使用次数
     * 
     * @param cumulativeUse
     *          累计使用次数
     */
    public void setCumulativeUse(Integer cumulativeUse) {
        this.cumulativeUse = cumulativeUse;
    }

    /**
     * 获取本次保养日期
     * 
     * @return 本次保养日期
     */
    public Date getMaintenanceDate() {
        return this.maintenanceDate;
    }

    /**
     * 设置本次保养日期
     * 
     * @param maintenanceDate
     *          本次保养日期
     */
    public void setMaintenanceDate(Date maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    /**
     * 获取下次保养日期
     * 
     * @return 下次保养日期
     */
    public Date getNextMaintenanceDate() {
        return this.nextMaintenanceDate;
    }

    /**
     * 设置下次保养日期
     * 
     * @param nextMaintenanceDate
     *          下次保养日期
     */
    public void setNextMaintenanceDate(Date nextMaintenanceDate) {
        this.nextMaintenanceDate = nextMaintenanceDate;
    }

    /**
     * 获取车架号
     * 
     * @return 车架号
     */
    public String getFrameNumber() {
        return this.frameNumber;
    }

    /**
     * 设置车架号
     * 
     * @param frameNumber
     *          车架号
     */
    public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
    }

    /**
     * 获取发动机编号
     * 
     * @return 发动机编号
     */
    public String getEngineNumber() {
        return this.engineNumber;
    }

    /**
     * 设置发动机编号
     * 
     * @param engineNumber
     *          发动机编号
     */
    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    /**
     * 获取批次号
     * 
     * @return 批次号
     */
    public String getBatchNumber() {
        return this.batchNumber;
    }

    /**
     * 设置批次号
     * 
     * @param batchNumber
     *          批次号
     */
    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    /**
     * 获取计量单位代码
     * 
     * @return 计量单位代码
     */
    public String getMeasurementUnitCode() {
        return this.measurementUnitCode;
    }

    /**
     * 设置计量单位代码
     * 
     * @param measurementUnitCode
     *          计量单位代码
     */
    public void setMeasurementUnitCode(String measurementUnitCode) {
        this.measurementUnitCode = measurementUnitCode;
    }

    /**
     * 获取是否用于训练
     * 
     * @return 是否用于训练
     */
    public Boolean getTraining() {
        return this.training;
    }

    /**
     * 设置是否用于训练
     * 
     * @param training
     *          是否用于训练
     */
    public void setTraining(Boolean training) {
        this.training = training;
    }

    /**
     * 获取锁定状态
     * 
     * @return 锁定状态
     */
    public String getLockState() {
        return this.lockState;
    }

    /**
     * 设置锁定状态
     * 
     * @param lockState
     *          锁定状态
     */
    public void setLockState(String lockState) {
        this.lockState = lockState;
    }

    /**
     * 获取电台呼号
     * 
     * @return 电台呼号
     */
    public String getRadioCallSign() {
        return this.radioCallSign;
    }

    /**
     * 设置电台呼号
     * 
     * @param radioCallSign
     *          电台呼号
     */
    public void setRadioCallSign(String radioCallSign) {
        this.radioCallSign = radioCallSign;
    }

    /**
     * 获取案件编号
     * 
     * @return 案件编号
     */
    public String getCaseNumber() {
        return this.caseNumber;
    }

    /**
     * 设置案件编号
     * 
     * @param caseNumber
     *          案件编号
     */
    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    /**
     * 获取是否装配
     * 
     * @return 是否装配
     */
    public Boolean getAssemble() {
        return this.assemble;
    }

    /**
     * 设置是否装配
     * 
     * @param assemble
     *          是否装配
     */
    public void setAssemble(Boolean assemble) {
        this.assemble = assemble;
    }

    /**
     * 获取是否跨支队
     *
     * @return 是否跨支队
     */
    public Boolean getCrossTheBranch() {
        return this.crossTheBranch;
    }

    /**
     * 设置是否跨支队
     * 
     * @param crossTheBranch
     *          是否跨支队
     */
    public void setCrossTheBranch(Boolean crossTheBranch) {
        this.crossTheBranch = crossTheBranch;
    }

    /**
     * 获取是否跨总队
     * 
     * @return 是否跨总队
     */
    public Boolean getCrossTheBrigade() {
        return this.crossTheBrigade;
    }

    /**
     * 设置是否跨总队
     * 
     * @param crossTheBrigade
     *          是否跨总队
     */
    public void setCrossTheBrigade(Boolean crossTheBrigade) {
        this.crossTheBrigade = crossTheBrigade;
    }

    /**
     * 获取车辆简称
     * 
     * @return 车辆简称
     */
    public String getVehicleAbbreviation() {
        return this.vehicleAbbreviation;
    }

    /**
     * 设置车辆简称
     * 
     * @param vehicleAbbreviation
     *          车辆简称
     */
    public void setVehicleAbbreviation(String vehicleAbbreviation) {
        this.vehicleAbbreviation = vehicleAbbreviation;
    }

    /**
     * 获取规格号码
     * 
     * @return 规格号码
     */
    public String getSpecificationNumber() {
        return this.specificationNumber;
    }

    /**
     * 设置规格号码
     * 
     * @param specificationNumber
     *          规格号码
     */
    public void setSpecificationNumber(String specificationNumber) {
        this.specificationNumber = specificationNumber;
    }

    /**
     * 获取资产编号
     * 
     * @return 资产编号
     */
    public String getAssetNumber() {
        return this.assetNumber;
    }

    /**
     * 设置资产编号
     * 
     * @param assetNumber
     *          资产编号
     */
    public void setAssetNumber(String assetNumber) {
        this.assetNumber = assetNumber;
    }

    /**
     * 获取参考价
     * 
     * @return 参考价
     */
    public String getReferencePrice() {
        return this.referencePrice;
    }

    /**
     * 设置参考价
     * 
     * @param referencePrice
     *          参考价
     */
    public void setReferencePrice(String referencePrice) {
        this.referencePrice = referencePrice;
    }

    /**
     * 获取商标
     * 
     * @return 商标
     */
    public String getTrademark() {
        return this.trademark;
    }

    /**
     * 设置商标
     * 
     * @param trademark
     *          商标
     */
    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    /**
     * 获取颜色
     * 
     * @return 颜色
     */
    public String getColour() {
        return this.colour;
    }

    /**
     * 设置颜色
     * 
     * @param colour
     *          颜色
     */
    public void setColour(String colour) {
        this.colour = colour;
    }

    /**
     * 获取国别代码
     * 
     * @return 国别代码
     */
    public String getCountryCode() {
        return this.countryCode;
    }

    /**
     * 设置国别代码
     * 
     * @param countryCode
     *          国别代码
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }


    @Override
    public String toString() {
        String str =  StringUtil.get(code) + String.format("|%s", StringUtil.get(name)) + "|消防车" +
                String.format("|%s",  DictionaryUtil.getValueByCode(typeUuid, LvApplication.getInstance().getCompDicMap().get("JTGJ"))) +
                String.format("|%s",  DictionaryUtil.getValueByCode(ownerType, LvApplication.getInstance().getCompDicMap().get("CL_SSJG"))) +
                String.format("|%s",  StringUtil.get(plateNumber)) +
                String.format("|%s",  StringUtil.get(gpsCode)) +
                String.format("|%s",  StringUtil.get(manufacturer)) +
                String.format("|%s",  StringUtil.get(createPerson)) +
                String.format("|%s",  StringUtil.get(updatePerson)) +
                String.format("|%s",  StringUtil.get(manufacturerCode)) +
                String.format("|%s",  StringUtil.get(domesticAgent)) +
                String.format("|%s",  StringUtil.get(afterSaleService)) +
                String.format("|%s",  StringUtil.get(responsiblePersonId)) +
                String.format("|%s",  StringUtil.get(responsiblePersonName)) +
                String.format("|%s",  StringUtil.get(cumulativeUse)) +
                String.format("|%s",  StringUtil.get(frameNumber)) +
                String.format("|%s",  StringUtil.get(engineNumber)) +
                String.format("|%s",  StringUtil.get(batchNumber)) +
                String.format("|%s",  StringUtil.get(measurementUnitCode)) +
                String.format("|%s",  StringUtil.get(lockState)) +
                String.format("|%s",  StringUtil.get(radioCallSign)) +
                String.format("|%s",  StringUtil.get(caseNumber)) +
                String.format("|%s",  StringUtil.get(vehicleAbbreviation)) +
                String.format("|%s",  StringUtil.get(specificationNumber)) +
                String.format("|%s",  StringUtil.get(assetNumber)) +
                String.format("|%s",  StringUtil.get(referencePrice)) +
                String.format("|%s",  StringUtil.get(trademark)) +
                String.format("|%s",  StringUtil.get(colour)) +
                String.format("|%s",  StringUtil.get(countryCode)) +
                String.format("|%s",  StringUtil.get(remark));
        return str;
    }

    private String getTypeCode() {
        String typeCode = "";
        if(!StringUtil.isEmpty(typeUuid)) {
            Dictionary dic = DictionaryUtil.getDictionaryByCode(typeUuid, LvApplication.getInstance().getCompDicMap().get("JTGJ"));
            if(dic != null) {
                typeCode = StringUtil.get(dic.getId());
            }
        }
        return typeCode;
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