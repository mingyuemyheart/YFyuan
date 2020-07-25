package com.moft.xfapply.model.external.dto;

import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.base.IMediaDTO;
import com.moft.xfapply.model.external.base.ISourceDataDTO;
import com.moft.xfapply.utils.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * key_unit
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class KeyUnitDTO extends RestDTO implements ISourceDataDTO {
    /** 版本号 */
    private static final long serialVersionUID = -8184561302467145632L;

    /** 唯一标识
 */
    private String uuid;
    private String eleType;

    /** 编码
 */
    private String code;

    /** 单位名称
 */
    private String name;

    /** 数据所属区域
 */
    private String departmentUuid;

    /** 防火级别
 */
    private String preventionGrade;

    /** 防火队站uuid
 */
    private String preventionStationUuid;

    /** 防火队站名称
 */
    private String preventionStationName;

    /** 灭火队站uuid
 */
    private String extinguishStationUuid;

    /** 灭火队站名称
 */
    private String extinguishStationName;

    /** 单位性质标识
 */
    private String characterUuid;

    /** 行政区划编码
 */
    private String districtCode;

    /** 单位地址
 */
    private String address;

    /** 路/街
 */
    private String addrRoad;

    /** 号
 */
    private String addrNumber;

    private String geometryText;//位置类型

    /** 位置的类型
     */
    private String geometryType;

    /** 经度
     */
    private Double longitude;

    /** 纬度
     */
    private Double latitude;

    /** 消防负责人
 */
    private String responsiblePerson;

    /** 消防负责人联系方式
 */
    private String responsiblePersonContact;
    /** 消防负责人身份证
     */
    private String responsiblePersonIdCard;
    /** 消防管理人
 */
    private String managePerson;

    /** 消防管理人联系方式
 */
    private String managePersonContact;
    /** 消防管理人身份证
     */
    private String managePersonIdCard;
    /** 单位值班电话
 */
    private String dutyTelephone;
    /** 防火监督员
     */
    private String supervisorPerson;

    /** 防火监督员联系方式
     */
    private String supervisorPersonContact;
    //有无消防力量
    private Boolean hasFireForce;

    // 消防力量-类型
    private String fireForceType;

    // 消防力量-联系方式
    private String fireForceUnitTel;

    // 消防力量-执勤车辆
    private Integer fireForceDutyVehicle;

    // 消防力量-消防队员人数
    private Integer fireForceDutyNum;

    // 消防力量-备注
    private String fireForceRemark;

    /** 单位概况
 */
    private String unitOverview;
    /** 功能分区描述
     */
    private String functionalDescription;
    /** 建筑情况
 */
    private String structureFlag;

    /** 有无全景
 */
    private Boolean hasPano;

    /** 有无预案
 */
    private Boolean hasRescue;

    /** 有无三维
 */
    private Boolean hasMx;

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

    /** 删除标识
     */
    private Boolean deleted;

    /** 数据版本
     */
    private Integer version;

    //重点单位区域表字段

    /** 毗邻情况-东
     */
    private String adjacentEast;

    /** 毗邻情况-西
     */
    private String adjacentWest;

    /** 毗邻情况-南
     */
    private String adjacentSouth;

    /** 毗邻情况-北
     */
    private String adjacentNorth;

    /** 辖区行车路线
     */
    private String routeFromStation;

    /** 到达时长
     */
    private Integer timeStationArrive;

    /** 总占地面积
     */
    private Double floorArea;

    /** 总建筑面积
     */
    private Double structureArea;

    /** 是否存在有效副本 */
    public Boolean hasSnapshot;

    private String belongtoGroup;

    private Double dataQuality;

    private String keywords; //关键字查询

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentUuid() {
        return departmentUuid;
    }

    public void setDepartmentUuid(String departmentUuid) {
        this.departmentUuid = departmentUuid;
    }

    public String getPreventionGrade() {
        return preventionGrade;
    }

    public void setPreventionGrade(String preventionGrade) {
        this.preventionGrade = preventionGrade;
    }

    public String getPreventionStationUuid() {
        return preventionStationUuid;
    }

    public void setPreventionStationUuid(String preventionStationUuid) {
        this.preventionStationUuid = preventionStationUuid;
    }

    public String getPreventionStationName() {
        return preventionStationName;
    }

    public void setPreventionStationName(String preventionStationName) {
        this.preventionStationName = preventionStationName;
    }

    public String getExtinguishStationUuid() {
        return extinguishStationUuid;
    }

    public void setExtinguishStationUuid(String extinguishStationUuid) {
        this.extinguishStationUuid = extinguishStationUuid;
    }

    public String getExtinguishStationName() {
        return extinguishStationName;
    }

    public void setExtinguishStationName(String extinguishStationName) {
        this.extinguishStationName = extinguishStationName;
    }

    public String getCharacterUuid() {
        return characterUuid;
    }

    public void setCharacterUuid(String characterUuid) {
        this.characterUuid = characterUuid;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddrRoad() {
        return addrRoad;
    }

    public void setAddrRoad(String addrRoad) {
        this.addrRoad = addrRoad;
    }

    public String getAddrNumber() {
        return addrNumber;
    }

    public void setAddrNumber(String addrNumber) {
        this.addrNumber = addrNumber;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getResponsiblePersonContact() {
        return responsiblePersonContact;
    }

    public void setResponsiblePersonContact(String responsiblePersonContact) {
        this.responsiblePersonContact = responsiblePersonContact;
    }

    public String getManagePerson() {
        return managePerson;
    }

    public void setManagePerson(String managePerson) {
        this.managePerson = managePerson;
    }

    public String getManagePersonContact() {
        return managePersonContact;
    }

    public void setManagePersonContact(String managePersonContact) {
        this.managePersonContact = managePersonContact;
    }

    public String getDutyTelephone() {
        return dutyTelephone;
    }

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

    public String getFireForceRemark() {
        return fireForceRemark;
    }

    public void setFireForceRemark(String fireForceRemark) {
        this.fireForceRemark = fireForceRemark;
    }

    public String getUnitOverview() {
        return unitOverview;
    }

    public void setUnitOverview(String unitOverview) {
        this.unitOverview = unitOverview;
    }

    public String getStructureFlag() {
        return structureFlag;
    }

    public void setStructureFlag(String structureFlag) {
        this.structureFlag = structureFlag;
    }

    public Boolean getHasPano() {
        return hasPano;
    }

    public void setHasPano(Boolean hasPano) {
        this.hasPano = hasPano;
    }

    public Boolean getHasRescue() {
        return hasRescue;
    }

    public void setHasRescue(Boolean hasRescue) {
        this.hasRescue = hasRescue;
    }

    public Boolean getHasMx() {
        return hasMx;
    }

    public void setHasMx(Boolean hasMx) {
        this.hasMx = hasMx;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public Boolean getHasSnapshot() {
        return hasSnapshot;
    }

    public void setHasSnapshot(Boolean hasSnapshot) {
        this.hasSnapshot = hasSnapshot;
    }

    public String getGeometryText() {
        return geometryText;
    }

    public void setGeometryText(String geometryText) {
        this.geometryText = geometryText;
    }

    public String getGeometryType() {
        return geometryType;
    }

    public void setGeometryType(String geometryType) {
        this.geometryType = geometryType;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
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
    public String getEleType() {
        if(StringUtil.isEmpty(eleType)) {
            eleType = AppDefs.CompEleType.KEY_UNIT.toString();
        }
        return eleType;
    }

    public <T> void setMediaInfos(List<T> mediaList) {
    }

    @Override
    public List<IMediaDTO> getMediaInfos() {
        List<IMediaDTO> mediaList = new ArrayList<>();
        return mediaList;
    }
}