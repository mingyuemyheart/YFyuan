/**
 * Copyright (C), 2012-2018, 大连云帆科技有限公司
 * FileName: EnterpriseFourceDTO
 * Author:   wangxu
 * Date:     2018/3/22 0022 19:28
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.moft.xfapply.model.external.dto;

import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.base.IMediaDTO;
import com.moft.xfapply.model.external.base.ISourceDataDTO;
import com.moft.xfapply.utils.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * (企业队)
 * 
 * @author wangxy
 * @version 1.0.0 2017-04-14
 */
public class EnterpriseFourceDTO extends RestDTO implements ISourceDataDTO {

	private static final long serialVersionUID = -8636254417686740490L;

	private String uuid;
	private String eleType;

	/** 队伍类型 */
	private String type;

	/** 队伍编码 */
	private String code;

	/** 名称 */
	private String name;

	/** 所属单位 */
	private String keyUnitUuid;

	private String keyUnitName;

	/** 数据所属区域 */
	private String departmentUuid;

	/** 管辖单位 */
	private String unit;

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

	/** 消防队员总人数 */
	private Integer totalNum;

	/** 每日执勤人数 */
	private Integer dutyNum;

	/** 执勤车辆（台） */
	private Integer dutyVehicle;

	/** 灭火剂量（吨） */
	private Double extinguishingDose;

	/** 值班电话 */
	private String dutyTel;

	/** 传真 */
	private String fax;

	/** 队长姓名 */
	private String headerName;

	/** 队长联系方式 */
	private String headerTel;

	/** 管辖单位联系方式 */
	private String unitTel;

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
	 * 获取队伍类型
	 *
	 * @return 队伍类型
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * 设置队伍类型
	 *
	 * @param type
	 *          队伍类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取队伍编码
	 *
	 * @return 队伍编码
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * 设置队伍编码
	 *
	 * @param code
	 *          队伍编码
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
	 * 获取所属单位
	 *
	 * @return 所属单位
	 */
	public String getKeyUnitUuid() {
		return this.keyUnitUuid;
	}

	/**
	 * 设置所属单位
	 *
	 * @param keyUnitUuid
	 *          所属单位
	 */
	public void setKeyUnitUuid(String keyUnitUuid) {
		this.keyUnitUuid = keyUnitUuid;
	}

	public String getKeyUnitName() {
		return keyUnitName;
	}

	public void setKeyUnitName(String keyUnitName) {
		this.keyUnitName = keyUnitName;
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
	 * 获取管辖单位
	 *
	 * @return 管辖单位
	 */
	public String getUnit() {
		return this.unit;
	}

	/**
	 * 设置管辖单位
	 *
	 * @param unit
	 *          管辖单位
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * 获取地址
	 *
	 * @return 地址
	 */
	public String getAdress() {
		return this.address;
	}

	/**
	 * 设置地址
	 *
	 * @param address
	 *          地址
	 */
	public void setAdress(String address) {
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

	@Override
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Override
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

	/**
	 * 获取消防队员总人数
	 *
	 * @return 消防队员总人数
	 */
	public Integer getTotalNum() {
		return this.totalNum;
	}

	/**
	 * 设置消防队员总人数
	 *
	 * @param totalNum
	 *          消防队员总人数
	 */
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	/**
	 * 获取每日执勤人数
	 *
	 * @return 每日执勤人数
	 */
	public Integer getDutyNum() {
		return this.dutyNum;
	}

	/**
	 * 设置每日执勤人数
	 *
	 * @param dutyNum
	 *          每日执勤人数
	 */
	public void setDutyNum(Integer dutyNum) {
		this.dutyNum = dutyNum;
	}

	/**
	 * 获取执勤车辆（台）
	 *
	 * @return 执勤车辆（台）
	 */
	public Integer getDutyVehicle() {
		return this.dutyVehicle;
	}

	/**
	 * 设置执勤车辆（台）
	 *
	 * @param dutyVehicle
	 *          执勤车辆（台）
	 */
	public void setDutyVehicle(Integer dutyVehicle) {
		this.dutyVehicle = dutyVehicle;
	}

	/**
	 * 获取灭火剂量（吨）
	 *
	 * @return 灭火剂量（吨）
	 */
	public Double getExtinguishingDose() {
		return this.extinguishingDose;
	}

	/**
	 * 设置灭火剂量（吨）
	 *
	 * @param extinguishingDose
	 *          灭火剂量（吨）
	 */
	public void setExtinguishingDose(Double extinguishingDose) {
		this.extinguishingDose = extinguishingDose;
	}

	/**
	 * 获取值班电话
	 *
	 * @return 值班电话
	 */
	public String getDutyTel() {
		return this.dutyTel;
	}

	/**
	 * 设置值班电话
	 *
	 * @param dutyTel
	 *          值班电话
	 */
	public void setDutyTel(String dutyTel) {
		this.dutyTel = dutyTel;
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
	 * 获取队长姓名
	 *
	 * @return 队长姓名
	 */
	public String getHeaderName() {
		return this.headerName;
	}

	/**
	 * 设置队长姓名
	 *
	 * @param headerName
	 *          队长姓名
	 */
	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	/**
	 * 获取队长联系方式
	 *
	 * @return 队长联系方式
	 */
	public String getHeaderTel() {
		return this.headerTel;
	}

	/**
	 * 设置队长联系方式
	 *
	 * @param headerTel
	 *          队长联系方式
	 */
	public void setHeaderTel(String headerTel) {
		this.headerTel = headerTel;
	}

	/**
	 * 获取管辖单位联系方式
	 *
	 * @return 管辖单位联系方式
	 */
	public String getUnitTel() {
		return this.unitTel;
	}

	/**
	 * 设置管辖单位联系方式
	 *
	 * @param unitTel
	 *          管辖单位联系方式
	 */
	public void setUnitTel(String unitTel) {
		this.unitTel = unitTel;
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

	@Override
	public String getEleType() {
		if(StringUtil.isEmpty(eleType)) {
			eleType = AppDefs.CompEleType.ENTERPRISE_FOURCE.toString();
		}
		return eleType;
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