package com.moft.xfapply.model.external.dto;

import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.base.IMediaDTO;
import com.moft.xfapply.model.external.base.ISourceDataDTO;
import com.moft.xfapply.utils.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * material_fire_vehicle
 * 
 * @author wangx
 * @version 1.0.0 2018-05-24
 */
public class FireVehicleDTO extends RestDTO implements ISourceDataDTO {
    /** 版本号 */
    private static final long serialVersionUID = -5840214426954251730L;

    /** 唯一标识 */
    private String uuid;
    private String eleType;

    /** 编码 */
    private String code;

    /** 名称 */
    private String name;

    /** 所属队站 */
    private String stationUuid;

    /** 区县 */
    private String ownerType;

    /** 车牌号 */
    private String plateNumber;

    /** 车辆类型的标识 */
    private String typeUuid;

    /** 水泵流量（L/s） */
    private Double pumpCapacity;

    /** 消防炮流量（L/s） */
    private Double fireArtilleryCapacity;

    /** 水泵额定压力（Mpa） */
    private Double pumpRatedPressure;

    /** 是否有车载灭火剂 */
    private Boolean hasExtinguishingAgent;

    /** 是否有车载器材 */
    private Boolean hasEquipment;

    /** GPS编号 */
    private String gpsCode;

    /** 载水量（t） */
    private Double capacity;

    /** 车辆状态标识 */
    private String stateUuid;

    /** 生产厂家 */
    private String manufacturer;

    /** 举高类车辆高度（米） */
    private Double liftHeight;

    /** 删除标识 */
    private Boolean deleted;

    /** 数据版本 */
    private Integer version;

    /** 提交人 */
    private String createPerson;

    /** 创建时间 */
    private Date createDate;

    /** 最新更新者 */
    private String updatePerson;

    /** 更新时间 */
    private Date updateDate;

    /** 备注 */
    private String remark;

    /** keywords */
    private String keywords;

    /** 生产厂家代码 */
    private String manufacturerCode;

    /** 进口装备国内代理商 */
    private String domesticAgent;

    /** 售后服务 */
    private String afterSaleService;

    /** 责任人ID */
    private String responsiblePersonId;

    /** 责任人姓名 */
    private String responsiblePersonName;

    /** 车架号 */
    private String frameNumber;

    /** 发动机编号 */
    private String engineNumber;

    /** 批次号 */
    private String batchNumber;

    /** 计量单位代码 */
    private String measurementUnitCode;

    /** 锁定状态 */
    private String lockState;

    /** 电台呼号 */
    private String radioCallSign;

    /** 案件编号 */
    private String caseNumber;

    /** 是否装配 */
    private Boolean assemble;

    /** 是否跨支队 */
    private Boolean crossTheBranch;

    /** 是否跨总队 */
    private Boolean crossTheBrigade;

    /** 车辆简称 */
    private String vehicleAbbreviation;

    /** 规格号码 */
    private String specificationNumber;

    /** 资产编号 */
    private String assetNumber;

    /** 参考价 */
    private String referencePrice;

    /** 商标 */
    private String trademark;

    /** 颜色 */
    private String colour;

    /** 国别代码 */
    private String countryCode;

    /** 出厂日期 */
    private Date productionDate;

    /** 装备日期 */
    private Date equipmentDate;

    /** 报废日期 */
    private Date discardedDate;

    /** 本次保养日期 */
    private Date maintenanceDate;

    /** 下次保养日期 */
    private Date nextMaintenanceDate;

    /** 是否用于训练 */
    private Boolean training;

    /** 有效期至 */
    private Date period;

    /** 累计运输时间 */
    private Double transportTime;

    /** 累计使用次数 */
    private Integer cumulativeUse;

    /** 数据所属区域 */
    private String departmentUuid;

    private String belongtoGroup;

    private Double dataQuality;

    /** 灭火剂 */
    private List<ExtinguishingAgentDTO> extinguishingAgentDTOs;

    /** 装备器材 */
    private List<EquipmentDTO> equipmentDTOs;

    /** 执勤中队 */
    private StationSquadronDTO stationSquadronDTO;


    private List<MaterialMediaDTO> materialMediaDTOs;

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
     * @param uuid
     *          唯一标识
     */
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

    /**
     * 获取区县
     *
     * @return 区县
     */
    public String getOwnerType() {
        return this.ownerType;
    }

    /**
     * 设置区县
     *
     * @param ownerType
     *          区县
     */
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

    public List<ExtinguishingAgentDTO> getExtinguishingAgentDTOs() {
        return extinguishingAgentDTOs;
    }

    public void setExtinguishingAgentDTOs(List<ExtinguishingAgentDTO> extinguishingAgentDTOs) {
        this.extinguishingAgentDTOs = extinguishingAgentDTOs;
    }

    public List<EquipmentDTO> getEquipmentDTOs() {
        return equipmentDTOs;
    }

    public void setEquipmentDTOs(List<EquipmentDTO> equipmentDTOs) {
        this.equipmentDTOs = equipmentDTOs;
    }

    public StationSquadronDTO getStationSquadronDTO() {
        return stationSquadronDTO;
    }

    public void setStationSquadronDTO(StationSquadronDTO stationSquadronDTO) {
        this.stationSquadronDTO = stationSquadronDTO;
    }

    @Override
    public String getEleType() {
        if(StringUtil.isEmpty(eleType)) {
            eleType = AppDefs.CompEleType.FIRE_VEHICLE.toString();
        }
        return eleType;
    }

    @Override
    public String getGeometryType() {
        return null;
    }

    @Override
    public void setGeometryType(String geometryType) {

    }

    @Override
    public Double getLatitude() {
        return null;
    }

    @Override
    public void setLatitude(Double latitude) {

    }

    @Override
    public Double getLongitude() {
        return null;
    }

    @Override
    public void setLongitude(Double longitude) {

    }

    public List<MaterialMediaDTO> getMaterialMediaDTOs() {
        return materialMediaDTOs;
    }

    public void setMaterialMediaDTOs(List<MaterialMediaDTO> materialMediaDTOs) {
        this.materialMediaDTOs = materialMediaDTOs;
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

    public <T> void setMediaInfos(List<T> mediaList) {
        if(mediaList != null && mediaList.size() > 0) {
            materialMediaDTOs = new ArrayList<>();
            for(T item : mediaList) {
                materialMediaDTOs.add((MaterialMediaDTO) item);
            }
        }
    }

    public List<IMediaDTO> getMediaInfos() {
        List<IMediaDTO> mediaList = new ArrayList<>();
        if(materialMediaDTOs != null && materialMediaDTOs.size() > 0) {
            for(MaterialMediaDTO item : materialMediaDTOs) {
                mediaList.add(item);
            }
        }
        return mediaList;
    }

    public List<ExtinguishingAgentDTO> getExtinguishingAgentDTOs(String snapshotUuid) {
        if(extinguishingAgentDTOs != null && extinguishingAgentDTOs.size() > 0) {
            for(ExtinguishingAgentDTO item : extinguishingAgentDTOs) {
                item.setUuid(joinToSnapshotUuid(snapshotUuid, item.getUuid()));
            }
        }
        return extinguishingAgentDTOs;
    }

    public List<EquipmentDTO> getEquipmentDTOs(String snapshotUuid) {
        if(equipmentDTOs != null && equipmentDTOs.size() > 0) {
            for(EquipmentDTO item : equipmentDTOs) {
                item.setUuid(joinToSnapshotUuid(snapshotUuid, item.getUuid()));
            }
        }
        return equipmentDTOs;
    }

    private String joinToSnapshotUuid(String snapshotUuid, String uuid) {
        String retUuid = uuid;

        if(uuid.indexOf("|") == -1) {
            retUuid = snapshotUuid + "|" + uuid;
        }
        return retUuid;
    }
}