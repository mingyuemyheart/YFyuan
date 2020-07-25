package com.moft.xfapply.model.external.dto;

import com.moft.xfapply.model.external.base.IMediaDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * station_base
 * 
 * @author wangx
 * @version 1.0.0 2018-05-22
 */
public class StationBaseDTO extends RestDTO {
    /** 版本号 */
    private static final long serialVersionUID = 4017703880313128007L;

    private String uuid;
    protected String eleType;

    /** 编码 */
    private String code;

    /** 行政区域 */
    private String districtCode;

    /** 数据所属区域 */
    private String departmentUuid;

    /** 名称 */
    private String name;

    /** 类型 */
    private String type;

    /** 地址 */
    private String address;

    /** 路/街 */
    private String road;

    /** 号 */
    private String no;

    /** 经度
     */
    private Double longitude;

    /** 纬度
     */
    private Double latitude;

    /** 位置的类型
     */
    private String geometryType;

    /** 空间坐标JSON */
    private String geometryText;

    /** 现役人数 */
    private Integer serviceNum;

    /** 政府专职消防员数 */
    private Integer firefighterNum;

    /** 联系电话/值班电话 */
    private String contactNum;

    /** 删除标志 */
    private Boolean deleted;

    /** 版本 */
    private Integer version;

    /** 创建时间 */
    private Date createDate;

    /** 更新时间 */
    private Date updateDate;

    private String createPerson; // 提交人

    private String updatePerson; // 最新更新者

    /** 备注 */
    private String remark;
    private String belongtoGroup;
    private Double dataQuality;
    private String keywords; //关键字查询

    private List<StationMediaDTO> stationMediaDTOs;

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
     * 获取行政区域
     * 
     * @return 行政区域
     */
    public String getDistrictCode() {
        return this.districtCode;
    }

    /**
     * 设置行政区域
     * 
     * @param districtCode
     *          行政区域
     */
    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
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
     * 获取类型
     * 
     * @return 类型
     */
    public String getType() {
        return this.type;
    }

    /**
     * 设置类型
     * 
     * @param type
     *          类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取地址
     * 
     * @return 地址
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * 设置地址
     * 
     * @param address
     *          地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取路/街
     * 
     * @return 路/街
     */
    public String getRoad() {
        return this.road;
    }

    /**
     * 设置路/街
     * 
     * @param road
     *          路/街
     */
    public void setRoad(String road) {
        this.road = road;
    }

    /**
     * 获取号
     * 
     * @return 号
     */
    public String getNo() {
        return this.no;
    }

    /**
     * 设置号
     * 
     * @param no
     *          号
     */
    public void setNo(String no) {
        this.no = no;
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

    /**
     * 获取空间坐标JSON
     * 
     * @return 空间坐标JSON
     */
    public String getGeometryText() {
        return this.geometryText;
    }

    /**
     * 设置空间坐标JSON
     * 
     * @param geometryText
     *          空间坐标JSON
     */
    public void setGeometryText(String geometryText) {
        this.geometryText = geometryText;
    }

    /**
     * 获取现役人数
     * 
     * @return 现役人数
     */
    public Integer getServiceNum() {
        return this.serviceNum;
    }

    /**
     * 设置现役人数
     * 
     * @param serviceNum
     *          现役人数
     */
    public void setServiceNum(Integer serviceNum) {
        this.serviceNum = serviceNum;
    }

    /**
     * 获取政府专职消防员数
     * 
     * @return 政府专职消防员数
     */
    public Integer getFirefighterNum() {
        return this.firefighterNum;
    }

    /**
     * 设置政府专职消防员数
     * 
     * @param firefighterNum
     *          政府专职消防员数
     */
    public void setFirefighterNum(Integer firefighterNum) {
        this.firefighterNum = firefighterNum;
    }

    /**
     * 获取联系电话/值班电话
     * 
     * @return 联系电话/值班电话
     */
    public String getContactNum() {
        return this.contactNum;
    }

    /**
     * 设置联系电话/值班电话
     * 
     * @param contactNum
     *          联系电话/值班电话
     */
    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    /**
     * 获取删除标志
     * 
     * @return 删除标志
     */
    public Boolean getDeleted() {
        return this.deleted;
    }

    /**
     * 设置删除标志
     * 
     * @param deleted
     *          删除标志
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取版本
     * 
     * @return 版本
     */
    public Integer getVersion() {
        return this.version;
    }

    /**
     * 设置版本
     * 
     * @param version
     *          版本
     */
    public void setVersion(Integer version) {
        this.version = version;
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

    public List<StationMediaDTO> getStationMediaDTOs() {
        return stationMediaDTOs;
    }

    public void setStationMediaDTOs(List<StationMediaDTO> stationMediaDTOs) {
        this.stationMediaDTOs = stationMediaDTOs;
    }

    public <T> void setMediaInfos(List<T> mediaList) {
        if(mediaList != null && mediaList.size() > 0) {
            stationMediaDTOs = new ArrayList<>();
            for(T item : mediaList) {
                stationMediaDTOs.add((StationMediaDTO) item);
            }
        }
    }

    public List<IMediaDTO> getMediaInfos() {
        List<IMediaDTO> mediaList = new ArrayList<>();
        if(stationMediaDTOs != null && stationMediaDTOs.size() > 0) {
            for(StationMediaDTO item : stationMediaDTOs) {
                mediaList.add(item);
            }
        }
        return mediaList;
    }
}