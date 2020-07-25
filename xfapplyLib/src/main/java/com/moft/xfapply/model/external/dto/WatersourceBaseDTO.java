package com.moft.xfapply.model.external.dto;

import com.moft.xfapply.model.external.base.IMediaDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WatersourceBaseDTO extends RestDTO implements java.io.Serializable {

    private static final long serialVersionUID = -1228302569423704333L;

    private String uuid;//主键
    protected String eleType;
    private String code;//编码
    private String name;//水源名称
    private String departmentUuid;//组织机构uuid
    private String address;//地址
    private Double longitude;//精度
    private Double latitude; //纬度
    private String geometryType;//位置类型
    private String geometryText;//空间坐标json
    private String districtCode;//行政区划编码
    private String wsType;//水源类型
    private String wsAttch;//水源归属
    private String subordinateUnit;//所属单位（小区）
    private String availableState;//可用状态
    private String supplyUnit;//供水单位
    private String contactTel;//联系方式
    private String keywords;// 最新更新者
    private Boolean deleted;//删除标识
    private Integer version;//数据版本
    private String createPerson;//提交人
    private Date createDate;//创建时间
    private String updatePerson;//最新更新人
    private Date updateDate;//更新时间
    private String remark;//备注
    private String belongtoGroup;
    private Double dataQuality;
    private List<WatersourceMediaDTO> wsMediaDTOs;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEleType() {
        return eleType;
    }

    public void setEleType(String eleType) {
        this.eleType = eleType;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getGeometryType() {
        return geometryType;
    }

    public void setGeometryType(String geometryType) {
        this.geometryType = geometryType;
    }

    public String getGeometryText() {
        return geometryText;
    }

    public void setGeometryText(String geometryText) {
        this.geometryText = geometryText;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
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

    public String getWsType() {
        return wsType;
    }

    public void setWsType(String wsType) {
        this.wsType = wsType;
    }

    public String getWsAttch() {
        return wsAttch;
    }

    public void setWsAttch(String wsAttch) {
        this.wsAttch = wsAttch;
    }

    public String getSubordinateUnit() {
        return subordinateUnit;
    }

    public void setSubordinateUnit(String subordinateUnit) {
        this.subordinateUnit = subordinateUnit;
    }

    public String getAvailableState() {
        return availableState;
    }

    public void setAvailableState(String availableState) {
        this.availableState = availableState;
    }

    public String getSupplyUnit() {
        return supplyUnit;
    }

    public void setSupplyUnit(String supplyUnit) {
        this.supplyUnit = supplyUnit;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
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

    public List<WatersourceMediaDTO> getWsMediaDTOs() {
        return wsMediaDTOs;
    }

    public void setWsMediaDTOs(List<WatersourceMediaDTO> wsMediaDTOs) {
        this.wsMediaDTOs = wsMediaDTOs;
    }

    public <T> void setMediaInfos(List<T> mediaList) {
        if(mediaList != null && mediaList.size() > 0) {
            wsMediaDTOs = new ArrayList<>();
            for(T item : mediaList) {
                wsMediaDTOs.add((WatersourceMediaDTO) item);
            }
        }
    }

    public List<IMediaDTO> getMediaInfos() {
        List<IMediaDTO> mediaList = new ArrayList<>();
        if(wsMediaDTOs != null && wsMediaDTOs.size() > 0) {
            for(WatersourceMediaDTO item : wsMediaDTOs) {
                mediaList.add(item);
            }
        }
        return mediaList;
    }
}
