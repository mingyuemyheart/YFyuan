package com.moft.xfapply.model.external.dto;

import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.base.IMediaDTO;
import com.moft.xfapply.model.external.base.ISourceDataDTO;
import com.moft.xfapply.utils.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * material_equipment
 * 
 * @author wangx
 * @version 1.0.0 2018-05-24
 */
public class EquipmentDTO extends RestDTO implements ISourceDataDTO {
    /** 版本号 */
    private static final long serialVersionUID = -9076238677488218843L;

    /** 唯一标识 */
    private String uuid;
    private String eleType;

    /** 编码
 */
    private String code;

    /** 名称
 */
    private String name;

    /** 所属队站
 */
    private String stationUuid;

    /** 数据所属区域
 */
    private String departmentUuid;

    /** 器材类型标识
 */
    private String typeUuid;

    /** 库存量（可以）
 */
    private Integer userfulInventory;

    /** 库存量（损坏）
 */
    private Integer unuserfulInventory;

    /** 生产厂家
 */
    private String manufacturer;

    /** 删除标识
 */
    private Boolean deleted;

    /** 数据版本
 */
    private Integer version;

    /** 提交人
 */
    private String createPerson;

    /** 创建时间
 */
    private Date createDate;

    /** 最新更新者
 */
    private String updatePerson;

    /** 更新时间
 */
    private Date updateDate;

    /** 备注
 */
    private String remark;

    /** 是否消耗性装备
 */
    private Boolean isExpendable;

    /** 规格型号
 */
    private String specificationModel;

    /** 计量单位代码
 */
    private String measurementUnitCode;

    /** 参考价
 */
    private Double referencePrice;

    /** 重量
 */
    private Double weight;

    /** 体积 */
    private Double volume;

    /** 检测批号
 */
    private String testBatchNumber;

    /** 适用范围
 */
    private String trialScope;

    /** 准入检验类别代码
 */
    private String admittanceTestCategoryCode;

    /** 进口装备国内代理商
 */
    private String domesticAgent;

    /** 售后服务单位
 */
    private String serviceUnit;

    /** 保养周期
 */
    private Double maintenanceCycle;

    /** 检查周期
 */
    private Double inspectionCycle;

    /** 生产日期
 */
    private Date manufactureDate;

    /** 关键字查询
 */
    private String keywords;

    /** 总储备量 */
    private Integer reserve;

    /** 储地类型;分为VEHICLE和STATION */
    private String storagePositionType;

    /** 储地uuid;储地类型为VEHICLE时，此处关联车辆uuid，仓储类型为STATION时，关联队站uuid */
    private String storagePositionUuid;

    private String belongtoGroup;
    private Double dataQuality;
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
     * @param typeUuid
     *          器材类型标识

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
     * @param userfulInventory
     *          库存量（可以）

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
     * @param unuserfulInventory
     *          库存量（损坏）

     */
    public void setUnuserfulInventory(Integer unuserfulInventory) {
        this.unuserfulInventory = unuserfulInventory;
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
     * @param isExpendable
     *          是否消耗性装备

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
     * @param specificationModel
     *          规格型号

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
     * @param measurementUnitCode
     *          计量单位代码

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
     * @param referencePrice
     *          参考价

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
     * @param weight
     *          重量

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
     * @param volume
     *          体积
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
     * @param testBatchNumber
     *          检测批号

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
     * @param trialScope
     *          适用范围

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
     * @param admittanceTestCategoryCode
     *          准入检验类别代码

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
     * @param domesticAgent
     *          进口装备国内代理商

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
     * @param serviceUnit
     *          售后服务单位

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
     * @param maintenanceCycle
     *          保养周期

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
     * @param inspectionCycle
     *          检查周期

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
     * @param manufactureDate
     *          生产日期

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
     * @param keywords
     *          关键字查询

     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
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
     * @param reserve
     *          总储备量
     */
    public void setReserve(Integer reserve) {
        this.reserve = reserve;
    }

    /**
     * 获取储地类型;分为VEHICLE和STATION
     * 
     * @return 储地类型;分为VEHICLE和STATION
     */
    public String getStoragePositionType() {
        return this.storagePositionType;
    }

    /**
     * 设置储地类型;分为VEHICLE和STATION
     * 
     * @param storagePositionType
     *          储地类型;分为VEHICLE和STATION
     */
    public void setStoragePositionType(String storagePositionType) {
        this.storagePositionType = storagePositionType;
    }

    /**
     * 获取储地uuid;储地类型为VEHICLE时，此处关联车辆uuid，仓储类型为STATION时，关联队站uuid
     * 
     * @return 储地uuid;储地类型为VEHICLE时
     */
    public String getStoragePositionUuid() {
        return this.storagePositionUuid;
    }

    /**
     * 设置储地uuid;储地类型为VEHICLE时，此处关联车辆uuid，仓储类型为STATION时，关联队站uuid
     * 
     * @param storagePositionUuid
     *          储地uuid;储地类型为VEHICLE时
     */
    public void setStoragePositionUuid(String storagePositionUuid) {
        this.storagePositionUuid = storagePositionUuid;
    }

    @Override
    public String getEleType() {
        if(StringUtil.isEmpty(eleType)) {
            eleType = AppDefs.CompEleType.EQUIPMENT.toString();
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
}