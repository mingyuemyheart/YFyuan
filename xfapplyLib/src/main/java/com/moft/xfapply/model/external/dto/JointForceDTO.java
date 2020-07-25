package com.moft.xfapply.model.external.dto;

import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.base.IMediaDTO;
import com.moft.xfapply.model.external.base.ISourceDataDTO;
import com.moft.xfapply.utils.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 联动力量(kb_joint_force)
 * 
 * @author songmy
 * @version 1.0.0 2018-06-02
 */
public class JointForceDTO extends RestDTO implements ISourceDataDTO {
    /** 版本号 */
    private static final long serialVersionUID = -4067888308144227673L;

    /** 唯一标识 */
    private String uuid;

    protected String eleType;

    /** 应急力量编码 */
    private String code;

    /** 应急力量类型 */
    private String type;

    /** 应急力量名称 */
    private String name;

    /** 组织机构uuid */
    private String departmentUuid;

    /** 行政区域 */
    private String districtCode;

    /** 应急力量地址 */
    private String address;
    /** 精度 */
    private Double longitude;
    /** 纬度 */
    private Double latitude;
    /** 位置类型 */
    private String geometryType;

    /** 空间坐标JSON */
    private String geometryText;

    /** 值班电话 */
    private String dutyPhone;

    /** 传真 */
    private String fax;

    /** 姓名 */
    private String contactName;

    /** 职务 */
    private String duties;

    /** 移动电话 */
    private String mobilePhone;

    /** 应急服务内容 */
    private String emergencyContent;

    /** 应急资源说明 */
    private String emergencyResource;

    /** 关键字查询 */
    private String keywords;

    /** 提交人 */
    private String createPerson;

    /** 创建时间 */
    private Date createDate;

    /** 最新更新者 */
    private String updatePerson;

    /** 更新时间 */
    private Date updateDate;

    /** 删除标识 */
    private Boolean deleted;

    /** 数据版本 */
    private Integer version;

    private String belongtoGroup;

    private Double dataQuality;

    /** 备注 */
    private String remark;

    private List<JointForceMediaDTO> jointForceMediaDTOs;

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
     * 获取应急力量编码
     *
     * @return 应急力量编码
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 设置应急力量编码
     *
     * @param code
     *          应急力量编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取应急力量类型
     *
     * @return 应急力量类型
     */
    public String getType() {
        return this.type;
    }

    /**
     * 设置应急力量类型
     *
     * @param type
     *          应急力量类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取应急力量名称
     *
     * @return 应急力量名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置应急力量名称
     *
     * @param name
     *          应急力量名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取组织机构uuid
     *
     * @return 组织机构uuid
     */
    public String getDepartmentUuid() {
        return this.departmentUuid;
    }

    /**
     * 设置组织机构uuid
     *
     * @param departmentUuid
     *          组织机构uuid
     */
    public void setDepartmentUuid(String departmentUuid) {
        this.departmentUuid = departmentUuid;
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
     * 获取应急力量地址
     * 
     * @return 应急力量地址
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * 设置应急力量地址
     * 
     * @param address
     *          应急力量地址
     */
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
     * 获取值班电话
     * 
     * @return 值班电话
     */
    public String getDutyPhone() {
        return this.dutyPhone;
    }

    /**
     * 设置值班电话
     * 
     * @param dutyPhone
     *          值班电话
     */
    public void setDutyPhone(String dutyPhone) {
        this.dutyPhone = dutyPhone;
    }

    /**
     * 获取传真
     * 
     * @return 传真
     */
    public String getFax() {
        return this.fax;
    }

    /**
     * 设置传真
     * 
     * @param fax
     *          传真
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * 获取姓名
     * 
     * @return 姓名
     */
    public String getContactName() {
        return this.contactName;
    }

    /**
     * 设置姓名
     * 
     * @param contactName
     *          姓名
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * 获取职务
     * 
     * @return 职务
     */
    public String getDuties() {
        return this.duties;
    }

    /**
     * 设置职务
     * 
     * @param duties
     *          职务
     */
    public void setDuties(String duties) {
        this.duties = duties;
    }

    /**
     * 获取移动电话
     * 
     * @return 移动电话
     */
    public String getMobilePhone() {
        return this.mobilePhone;
    }

    /**
     * 设置移动电话
     * 
     * @param mobilePhone
     *          移动电话
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * 获取应急服务内容
     * 
     * @return 应急服务内容
     */
    public String getEmergencyContent() {
        return this.emergencyContent;
    }

    /**
     * 设置应急服务内容
     * 
     * @param emergencyContent
     *          应急服务内容
     */
    public void setEmergencyContent(String emergencyContent) {
        this.emergencyContent = emergencyContent;
    }

    /**
     * 获取应急资源说明
     * 
     * @return 应急资源说明
     */
    public String getEmergencyResource() {
        return this.emergencyResource;
    }

    /**
     * 设置应急资源说明
     * 
     * @param emergencyResource
     *          应急资源说明
     */
    public void setEmergencyResource(String emergencyResource) {
        this.emergencyResource = emergencyResource;
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
     * 获取删除标识
     * 
     * @return 删除标识
     */
    public Boolean getDeleted() {
        return deleted;
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

    public List<JointForceMediaDTO> getJointForceMediaDTOs() {
        return jointForceMediaDTOs;
    }

    public void setJointForceMediaDTOs(List<JointForceMediaDTO> jointForceMediaDTOs) {
        this.jointForceMediaDTOs = jointForceMediaDTOs;
    }

    @Override
    public String getEleType() {
        if(StringUtil.isEmpty(eleType)) {
            eleType = AppDefs.CompEleType.JOINT_FORCE.toString();
        }
        return eleType;
    }

    public <T> void setMediaInfos(List<T> mediaList) {
        if(mediaList != null && mediaList.size() > 0) {
            jointForceMediaDTOs = new ArrayList<>();
            for(T item : mediaList) {
                jointForceMediaDTOs.add((JointForceMediaDTO) item);
            }
        }
    }

    public List<IMediaDTO> getMediaInfos() {
        List<IMediaDTO> mediaList = new ArrayList<>();
        if(jointForceMediaDTOs != null && jointForceMediaDTOs.size() > 0) {
            for(JointForceMediaDTO item : jointForceMediaDTOs) {
                mediaList.add(item);
            }
        }
        return mediaList;
    }
}