package com.moft.xfapply.model.entity;

import com.moft.xfapply.activity.bussiness.GeomEleBussiness;
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
 */
@Table(name = "material_equipment")
public class MaterialEquipmentDBInfo implements IGeomElementInfo {
    /**
     * 版本号
     */
    @Transient
    private static final long serialVersionUID = -6810864417092235975L;

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


    /**
     * 所属队站
     */
    @Property(column = "station_uuid")
    private String  stationUuid;
    /**
     * 储地类型
     */
    @Property(column = "storage_position_type")
    private String storagePositionType;
    /**
     * 储地uuid
     */
    @Property(column = "storage_position_uuid")
    private String storagePositionUuid;
    /**
     * 器材类型标识
     */
    @Property(column = "type_uuid")
    private String  typeUuid;
    /**
     * 库存量（可用）
     */
    @Property(column = "userful_inventory")
    private Integer  userfulInventory;
    /**
     * 库存量（损坏）
     */
    @Property(column = "unuserful_inventory")
    private Integer  unuserfulInventory;

    /**
     * 仓库储备量
     */
    private Integer reserve;
    /**
     * 生产厂家
     */
    private String  manufacturer;
    /**
     * 数据版本
     */
    private Integer version;
    /**
     * 是否消耗性装备
     */
    @Property(column = "is_expendable")
    private Boolean isExpendable;
    /**
     * 规格型号
     */
    @Property(column = "specification_model")
    private String  specificationModel;
    /**
     * 计量单位代码
     */
    @Property(column = "measurement_unit_code")
    private String  measurementUnitCode;
    /**
     * 参考价
     */
    @Property(column = "reference_price")
    private Double  referencePrice;
    /**
     * 重量
     */
    private Double  weight;
    /**
     * 体积
     */
    private Double  volume;
    /**
     * 检测批号
     */
    @Property(column = "test_batch_number")
    private String  testBatchNumber;
    /**
     * 适用范围
     */
    @Property(column = "trial_scope")
    private String  trialScope;
    /**
     * 准入检验类别代码
     */
    @Property(column = "admittance_test_category_code")
    private String  admittanceTestCategoryCode;
    /**
     * 进口装备国内代理商
     */
    @Property(column = "domestic_agent")
    private String  domesticAgent;
    /**
     * 售后服务单位
     */
    @Property(column = "service_unit")
    private String  serviceUnit;
    /**
     * 保养周期
     */
    @Property(column = "maintenance_cycle")
    private Double  maintenanceCycle;
    /**
     * 检查周期
     */
    @Property(column = "inspection_cycle")
    private Double  inspectionCycle;
    /**
     * 生产日期
     */
    @Property(column = "manufacture_date")
    private Date    manufactureDate;

    @Transient
    private List<PropertyDes> pdListDetail = new ArrayList<PropertyDes>();

    public MaterialEquipmentDBInfo() {
        reserve = 0;
        userfulInventory = 0;
        unuserfulInventory = 0;
    }

    public List<PropertyDes> getPdListDetail() {
        pdListDetail.clear();

        pdListDetail.add(new PropertyDes("器材编码", "setCode", String.class, code, PropertyDes.TYPE_TEXT));
        // ID :886069  【数据】车辆、器材、灭火剂、输入类别和名称，顺序对调 2019-02-20 王旭 START
        pdListDetail.add(new PropertyDes("器材类别*", "setTypeUuid", String.class, typeUuid, LvApplication.getInstance().getCompDicMap().get("ZBQC_EX"), true, true));
        // ID :886069  【数据】车辆、器材、灭火剂、输入类别和名称，顺序对调 2019-02-20 王旭 END
        pdListDetail.add(new PropertyDes("器材名称*", "setName", String.class, name, PropertyDes.TYPE_TEXT));
        //TODO:目前获取中队队站，将来修改成获取当前组织下的队站，同步修改getPrimaryValues
        if (AppDefs.CompEleType.FIRE_VEHICLE.toString().equals(storagePositionType) && StringUtil.isEmpty(stationUuid)) {
            stationUuid = GeomEleBussiness.getInstance().getVehicleStationUuid(storagePositionUuid, belongtoGroup);
        }
        pdListDetail.add(new PropertyDes("所属队站*", "setStationUuid", String.class, stationUuid, GeomEleBussiness.getInstance().getStationSquadronDic(belongtoGroup), true, true));

        if (AppDefs.CompEleType.FIRE_VEHICLE.toString().equals(storagePositionType)) {
            pdListDetail.add(new PropertyDes("车辆装载数", "setUserfulInventory", Integer.class, userfulInventory, PropertyDes.TYPE_EDIT));
        } else {
            reserve = calcReserve();
            pdListDetail.add(new PropertyDes("总存量", "setReserve", Integer.class, reserve, PropertyDes.TYPE_TEXT));
            pdListDetail.add(new PropertyDes("库存数量(可用)", "setUserfulInventory", Integer.class, userfulInventory,
                    PropertyDes.TYPE_EDIT));
            pdListDetail.add(new PropertyDes("库存数量(损坏)", "setUnuserfulInventory", Integer.class, unuserfulInventory,
                    PropertyDes.TYPE_EDIT));
        }
        pdListDetail.add(new PropertyDes("生产厂家名称", "setManufacturer", String.class, manufacturer, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("备注", "setRemark", String.class, remark, PropertyDes.TYPE_EDIT));

        //ID:S869489 19-02-12 车辆信息以分类形式展示（分为基本类信息，作战用信息，管理用信息），同理包括器材、灭火剂（基本信息、详细信息） 宋满意 开始
        pdListDetail.add(new PropertyDes("详细信息", uuid, false, PropertyDes.TYPE_DETAIL, PropertyDes.PropertyDetail.DETAIL, MaterialEquipmentDBInfo.class));
        pdListDetail.add(new PropertyDes("规格型号", "setSpecificationModel", String.class, uuid, PropertyDes.PropertyDetail.DETAIL, specificationModel,
                PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("计量单位代码", "setMeasurementUnitCode", String.class, uuid, PropertyDes.PropertyDetail.DETAIL, measurementUnitCode,
                PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("参考价", "setReferencePrice", Double.class, uuid, PropertyDes.PropertyDetail.DETAIL, referencePrice, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("检测批号", "setTestBatchNumber", String.class, uuid, PropertyDes.PropertyDetail.DETAIL, testBatchNumber, PropertyDes
                .TYPE_EDIT));
        pdListDetail.add(new PropertyDes("重量(kg)", "setWeight", Double.class, uuid, PropertyDes.PropertyDetail.DETAIL, weight, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("体积(m³)", "setVolume", Double.class, uuid, PropertyDes.PropertyDetail.DETAIL, volume, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("适用范围", "setTrialScope", String.class, uuid, PropertyDes.PropertyDetail.DETAIL, trialScope, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("准入检验类别代码", "setAdmittanceTestCategoryCode", String.class, uuid, PropertyDes.PropertyDetail.DETAIL,
                admittanceTestCategoryCode, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("进口装备国内代理商", "setDomesticAgent", String.class, uuid, PropertyDes.PropertyDetail.DETAIL, domesticAgent, PropertyDes
                .TYPE_EDIT));
        pdListDetail.add(new PropertyDes("售后服务单位", "setServiceUnit", String.class, uuid, PropertyDes.PropertyDetail.DETAIL, serviceUnit, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("是否消耗性装备", "setIsExpendable", Boolean.class, uuid, PropertyDes.PropertyDetail.DETAIL, isExpendable, LvApplication.getInstance().getWhetherDicList()));
        pdListDetail.add(new PropertyDes("保养周期(天)", "setMaintenanceCycle", Double.class, uuid, PropertyDes.PropertyDetail.DETAIL, maintenanceCycle, PropertyDes
                .TYPE_EDIT));
        pdListDetail.add(new PropertyDes("检查周期(天)", "setInspectionCycle", Double.class, uuid, PropertyDes.PropertyDetail.DETAIL, inspectionCycle, PropertyDes
                .TYPE_EDIT));
        pdListDetail.add(new PropertyDes("生产日期", "setManufactureDate", Date.class, uuid, PropertyDes.PropertyDetail.DETAIL, manufactureDate, PropertyDes
                .TYPE_DATE));
        //ID:S869489 19-02-12 车辆信息以分类形式展示（分为基本类信息，作战用信息，管理用信息），同理包括器材、灭火剂（基本信息、详细信息） 宋满意 结束

        return pdListDetail;
    }

    public List<PropertyDes> getFireVehiclePdListDetail() {
        pdListDetail.clear();

        pdListDetail.add(new PropertyDes("消防器材", uuid, PropertyDes.TYPE_NONE, MaterialEquipmentDBInfo.class));
        pdListDetail.add(new PropertyDes("器材编码", "setCode", String.class, code, PropertyDes.TYPE_TEXT));
        // ID :886069  【数据】车辆、器材、灭火剂、输入类别和名称，顺序对调 2019-02-20 王旭 START
        pdListDetail.add(new PropertyDes("器材类别*", "setTypeUuid", String.class, typeUuid, LvApplication.getInstance().getCompDicMap().get("ZBQC_EX"), true, true));
        // ID :886069  【数据】车辆、器材、灭火剂、输入类别和名称，顺序对调 2019-02-20 王旭 END
        pdListDetail.add(new PropertyDes("器材名称*", "setName", String.class, name, PropertyDes.TYPE_TEXT));
        pdListDetail.add(new PropertyDes("车辆装载数", "setUserfulInventory", Integer.class, userfulInventory, PropertyDes.TYPE_EDIT));
//        pdListDetail.add(new PropertyDes("总存量", "setReserve", Integer.class, reserve, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("备注", "setRemark", String.class, remark, PropertyDes.TYPE_EDIT));

        //ID:S869489 19-02-12 车辆信息以分类形式展示（分为基本类信息，作战用信息，管理用信息），同理包括器材、灭火剂（基本信息、详细信息） 宋满意 开始
        pdListDetail.add(new PropertyDes("详细信息", uuid, false, PropertyDes.TYPE_DETAIL, PropertyDes.PropertyDetail.DETAIL, MaterialEquipmentDBInfo.class));
        pdListDetail.add(new PropertyDes("生产厂家名称", "setManufacturer", String.class, uuid, PropertyDes.PropertyDetail.DETAIL, manufacturer, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("是否消耗性装备", "setIsExpendable", Boolean.class, uuid, PropertyDes.PropertyDetail.DETAIL, isExpendable, LvApplication.getInstance().getWhetherDicList()));
        pdListDetail.add(new PropertyDes("规格型号", "setSpecificationModel", String.class, uuid, PropertyDes.PropertyDetail.DETAIL, specificationModel, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("计量单位代码", "setMeasurementUnitCode", String.class, uuid, PropertyDes.PropertyDetail.DETAIL, measurementUnitCode, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("参考价", "setReferencePrice", Double.class, uuid, PropertyDes.PropertyDetail.DETAIL, referencePrice, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("重量(kg)", "setWeight", Double.class, uuid, PropertyDes.PropertyDetail.DETAIL, weight, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("体积(m³)", "setVolume", Double.class,uuid, PropertyDes.PropertyDetail.DETAIL,  volume, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("检测批号", "setTestBatchNumber", String.class, uuid, PropertyDes.PropertyDetail.DETAIL, testBatchNumber, PropertyDes
                .TYPE_EDIT));
        pdListDetail.add(new PropertyDes("适用范围", "setTrialScope", String.class, uuid, PropertyDes.PropertyDetail.DETAIL, trialScope, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("准入检验类别代码", "setAdmittanceTestCategoryCode", String.class,uuid, PropertyDes.PropertyDetail.DETAIL,
                admittanceTestCategoryCode, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("进口装备国内代理商", "setDomesticAgent", String.class,uuid, PropertyDes.PropertyDetail.DETAIL,  domesticAgent, PropertyDes
                .TYPE_EDIT));
        pdListDetail.add(new PropertyDes("售后服务单位", "setServiceUnit", String.class, uuid, PropertyDes.PropertyDetail.DETAIL, serviceUnit, PropertyDes.TYPE_EDIT));
        pdListDetail.add(new PropertyDes("保养周期(天)", "setMaintenanceCycle", Double.class, uuid, PropertyDes.PropertyDetail.DETAIL, maintenanceCycle, PropertyDes
                .TYPE_EDIT));
        pdListDetail.add(new PropertyDes("检查周期(天)", "setInspectionCycle", Double.class, uuid, PropertyDes.PropertyDetail.DETAIL, inspectionCycle, PropertyDes
                .TYPE_EDIT));
        pdListDetail.add(new PropertyDes("生产日期", "setManufactureDate", Date.class, uuid, PropertyDes.PropertyDetail.DETAIL, manufactureDate, PropertyDes
                .TYPE_DATE));
        //ID:S869489 19-02-12 车辆信息以分类形式展示（分为基本类信息，作战用信息，管理用信息），同理包括器材、灭火剂（基本信息、详细信息） 宋满意 结束

        return pdListDetail;
    }

    @Transient
    private static List<PropertyConditon> pdListDetailForFilter = new ArrayList<PropertyConditon>();
    public static List<PropertyConditon> getPdListDetailForFilter() {
        pdListDetailForFilter.clear();

        pdListDetailForFilter.add(new PropertyConditon("器材类别", "material_equipment", "type_uuid", String.class, PropertyConditon.TYPE_LIST, LvApplication.getInstance().getCompDicMap().get("ZBQC_EX"), true));

        return pdListDetailForFilter;
    }
    public static String getTableNameForFilter() {
        return "material_equipment";
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
        name = DictionaryUtil.getValueByCode(typeUuid, LvApplication.getInstance().getCompDicMap().get("ZBQC_EX"));
        reserve = userfulInventory + unuserfulInventory;

        if(StringUtil.isEmpty(storagePositionUuid)) {
            storagePositionUuid = stationUuid;
        }
    }

    public PrimaryAttribute getPrimaryValues() {
        PrimaryAttribute attribute = new PrimaryAttribute();
//        attribute.setPrimaryValue1(DictionaryUtil.getValueByCode(typeUuid, LvApplication.getInstance().getCompDicMap().get("ZBQC_EX")));
        attribute.setPrimaryValue1(name);  // ID:894112 数据模块列表显示按照update降序排列，器材，灭火剂将显示的类型修改成名称。 2019-02-25 王旭

        if ("FIRE_VEHICLE".equals(storagePositionType) && StringUtil.isEmpty(departmentUuid)) {
            departmentUuid = GeomEleBussiness.getInstance().getVehicleDepartmentUuid(storagePositionUuid, belongtoGroup);
        }
        attribute.setPrimaryValue2(DictionaryUtil.getValueByCode(departmentUuid, LvApplication.getInstance().getCurrentOrgList()));

        // ID:900352【物质器材灭火剂】追加车辆信息显示。列表中，根据器材是否是车载器材，而决定显示的名字  2019-03-07 王旭 START
        String msg = "库存量";
        if ("FIRE_VEHICLE".equals(storagePositionType)) {
            msg = "车载量";
        }
        attribute.setPrimaryValue3(String.format("%s：%s", msg, StringUtil.get(reserve)));
        // ID:900352【物质器材灭火剂】追加车辆信息显示。列表中，根据器材是否是车载器材，而决定显示的名字 2019-03-07 王旭 END

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);
        nf.setMinimumFractionDigits(0);
        nf.setRoundingMode(RoundingMode.HALF_UP);
        nf.setGroupingUsed(false);
        attribute.setPrimaryValue4(nf.format(dataQuality*100)+ "%" + getVehicleName());

        return  attribute;
    }

    /*
    * @Author 王旭
    * @Date 2019-03-05
    * @Descrption  ID:900352【物质器材灭火剂】追加车辆信息显示。
    * */
    private String getVehicleName() {
        String vehicleName = "";
        if ("FIRE_VEHICLE".equals(storagePositionType) && !StringUtil.isEmpty(storagePositionUuid)) {
            vehicleName = GeomEleBussiness.getInstance().getVehicleName(storagePositionUuid, belongtoGroup);
        }
        return vehicleName;
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
     * @param code 编码
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
     * @param name 名称
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
     * @param stationUuid 所属队站
     */
    public void setStationUuid(String stationUuid) {
        this.stationUuid = stationUuid;
    }

    public String getStoragePositionType() {
        return storagePositionType;
    }

    public void setStoragePositionType(String storagePositionType) {
        this.storagePositionType = storagePositionType;
    }

    public String getStoragePositionUuid() {
        return storagePositionUuid;
    }

    public void setStoragePositionUuid(String storagePositionUuid) {
        this.storagePositionUuid = storagePositionUuid;
    }

    public Boolean getExpendable() {
        return isExpendable;
    }

    public void setExpendable(Boolean expendable) {
        isExpendable = expendable;
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
     * @param departmentUuid 数据所属区域
     */
    public void setDepartmentUuid(String departmentUuid) {
        this.departmentUuid = departmentUuid;
    }

    /**
     * 获取器材类型标识
     *
     * @return 器材类型标识
     */
    public String getTypeUuid() {
        return this.typeUuid;
    }

    /**
     * 设置器材类型标识
     *
     * @param typeUuid 器材类型标识
     */
    public void setTypeUuid(String typeUuid) {
        this.typeUuid = typeUuid;
    }

    /**
     * 获取库存量（可以）
     *
     * @return 库存量（可以）
     */
    public Integer getUserfulInventory() {
        return this.userfulInventory;
    }

    /**
     * 设置库存量（可以）
     *
     * @param userfulInventory 库存量（可以）
     */
    public void setUserfulInventory(Integer userfulInventory) {
        this.userfulInventory = userfulInventory;
    }

    /**
     * 获取库存量（损坏）
     *
     * @return 库存量（损坏）
     */
    public Integer getUnuserfulInventory() {
        return this.unuserfulInventory;
    }

    /**
     * 设置库存量（损坏）
     *
     * @param unuserfulInventory 库存量（损坏）
     */
    public void setUnuserfulInventory(Integer unuserfulInventory) {
        this.unuserfulInventory = unuserfulInventory;
    }

    /**
     * 获取总储备量
     *
     * @return 总储备量
     */
    public Integer getReserve() {
        return this.reserve;
    }

    /**
     * 设置总储备量
     *
     * @param reserve 总储备量
     */
    public void setReserve(Integer reserve) {
        this.reserve = reserve;
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
     * @param manufacturer 生产厂家
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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
     * @param deleted 删除标识
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
     * @param version 数据版本
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
     * @param createPerson 提交人
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
     * @param createDate 创建时间
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
     * @param updatePerson 最新更新者
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
     * @param updateDate 更新时间
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
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取是否消耗性装备
     *
     * @return 是否消耗性装备
     */
    public Boolean getIsExpendable() {
        return this.isExpendable;
    }

    /**
     * 设置是否消耗性装备
     *
     * @param isExpendable 是否消耗性装备
     */
    public void setIsExpendable(Boolean isExpendable) {
        this.isExpendable = isExpendable;
    }

    /**
     * 获取规格型号
     *
     * @return 规格型号
     */
    public String getSpecificationModel() {
        return this.specificationModel;
    }

    /**
     * 设置规格型号
     *
     * @param specificationModel 规格型号
     */
    public void setSpecificationModel(String specificationModel) {
        this.specificationModel = specificationModel;
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
     * @param measurementUnitCode 计量单位代码
     */
    public void setMeasurementUnitCode(String measurementUnitCode) {
        this.measurementUnitCode = measurementUnitCode;
    }

    /**
     * 获取参考价
     *
     * @return 参考价
     */
    public Double getReferencePrice() {
        return this.referencePrice;
    }

    /**
     * 设置参考价
     *
     * @param referencePrice 参考价
     */
    public void setReferencePrice(Double referencePrice) {
        this.referencePrice = referencePrice;
    }

    /**
     * 获取重量
     *
     * @return 重量
     */
    public Double getWeight() {
        return this.weight;
    }

    /**
     * 设置重量
     *
     * @param weight 重量
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    /**
     * 获取体积
     *
     * @return 体积
     */
    public Double getVolume() {
        return this.volume;
    }

    /**
     * 设置体积
     *
     * @param volume 体积
     */
    public void setVolume(Double volume) {
        this.volume = volume;
    }

    /**
     * 获取检测批号
     *
     * @return 检测批号
     */
    public String getTestBatchNumber() {
        return this.testBatchNumber;
    }

    /**
     * 设置检测批号
     *
     * @param testBatchNumber 检测批号
     */
    public void setTestBatchNumber(String testBatchNumber) {
        this.testBatchNumber = testBatchNumber;
    }

    /**
     * 获取适用范围
     *
     * @return 适用范围
     */
    public String getTrialScope() {
        return this.trialScope;
    }

    /**
     * 设置适用范围
     *
     * @param trialScope 适用范围
     */
    public void setTrialScope(String trialScope) {
        this.trialScope = trialScope;
    }

    /**
     * 获取准入检验类别代码
     *
     * @return 准入检验类别代码
     */
    public String getAdmittanceTestCategoryCode() {
        return this.admittanceTestCategoryCode;
    }

    /**
     * 设置准入检验类别代码
     *
     * @param admittanceTestCategoryCode 准入检验类别代码
     */
    public void setAdmittanceTestCategoryCode(String admittanceTestCategoryCode) {
        this.admittanceTestCategoryCode = admittanceTestCategoryCode;
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
     * @param domesticAgent 进口装备国内代理商
     */
    public void setDomesticAgent(String domesticAgent) {
        this.domesticAgent = domesticAgent;
    }

    /**
     * 获取售后服务单位
     *
     * @return 售后服务单位
     */
    public String getServiceUnit() {
        return this.serviceUnit;
    }

    /**
     * 设置售后服务单位
     *
     * @param serviceUnit 售后服务单位
     */
    public void setServiceUnit(String serviceUnit) {
        this.serviceUnit = serviceUnit;
    }

    /**
     * 获取保养周期
     *
     * @return 保养周期
     */
    public Double getMaintenanceCycle() {
        return this.maintenanceCycle;
    }

    /**
     * 设置保养周期
     *
     * @param maintenanceCycle 保养周期
     */
    public void setMaintenanceCycle(Double maintenanceCycle) {
        this.maintenanceCycle = maintenanceCycle;
    }

    /**
     * 获取检查周期
     *
     * @return 检查周期
     */
    public Double getInspectionCycle() {
        return this.inspectionCycle;
    }

    /**
     * 设置检查周期
     *
     * @param inspectionCycle 检查周期
     */
    public void setInspectionCycle(Double inspectionCycle) {
        this.inspectionCycle = inspectionCycle;
    }

    /**
     * 获取生产日期
     *
     * @return 生产日期
     */
    public Date getManufactureDate() {
        return this.manufactureDate;
    }

    /**
     * 设置生产日期
     *
     * @param manufactureDate 生产日期
     */
    public void setManufactureDate(Date manufactureDate) {
        this.manufactureDate = manufactureDate;
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
     * @param keywords 关键字查询
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


    @Override
    public String toString() {
        String str = StringUtil.get(code) +
                String.format("|%s", StringUtil.get(name)) + "|消防器材" +
                String.format("|%s",  DictionaryUtil.getValueByCode(typeUuid, LvApplication.getInstance().getCompDicMap().get("ZBQC_EX"))) +
                String.format("|%s", StringUtil.get(createPerson)) +
                String.format("|%s", StringUtil.get(updatePerson)) +
                String.format("|%s", StringUtil.get(remark)) +
                String.format("|%s", StringUtil.get(manufacturer)) +
                String.format("|%s", StringUtil.get(specificationModel)) +
                String.format("|%s", StringUtil.get(measurementUnitCode)) +
                String.format("|%s", StringUtil.get(testBatchNumber)) +
                String.format("|%s", StringUtil.get(trialScope)) +
                String.format("|%s", StringUtil.get(admittanceTestCategoryCode)) +
                String.format("|%s", StringUtil.get(domesticAgent)) +
                String.format("|%s", StringUtil.get(serviceUnit));
        return str;
    }

    private String getTypeCode() {
        String typeCode = "";
        if(!StringUtil.isEmpty(typeUuid)) {
            Dictionary dic = DictionaryUtil.getDictionaryByCode(typeUuid, LvApplication.getInstance().getCompDicMap().get("ZBQC_EX"));
            if(dic != null) {
                typeCode = StringUtil.get(dic.getId());
            }
        }
        return typeCode;
    }

    private Integer calcReserve() {
        Integer ret = 0;
        if(unuserfulInventory != null) {
            ret = unuserfulInventory;
        }
        if(userfulInventory != null) {
            ret += userfulInventory;
        }
        return ret;
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