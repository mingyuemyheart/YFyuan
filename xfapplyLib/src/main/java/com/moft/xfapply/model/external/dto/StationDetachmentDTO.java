/**
 * Copyright (C), 2012-2018, 大连云帆科技有限公司
 * FileName: StationDetachmentDTO
 * Author:   wangxu
 * Date:     2018/3/22 0022 19:28
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.moft.xfapply.model.external.dto;

import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.model.external.base.ISourceDataDTO;
import com.moft.xfapply.utils.StringUtil;

/**
 * (支队)
 * 
 * @author wangxy
 * @version 1.0.0 2017-04-14
 */
public class StationDetachmentDTO extends StationBaseDTO implements ISourceDataDTO {

	private static final long serialVersionUID = 6940527265428409050L;
	/** 传真 */
	private String fax;

	/** 是否独立接警 */
	private Boolean independent;

	/** 支队长姓名 */
	private String captainName;

	/** 支队长联系方式 */
	private String captainTel;

	/** 支队政委姓名 */
	private String politicalCommissarName;

	/** 支队政委联系方式 */
	private String politicalCommissarTel;

	/** 消防文员数 */
	private Integer firefightingClerkNum;

	/** 辖区面积（平方公里） */
	private Double jurisdictionAcreage;

	/** 下辖大队数 */
	private Integer jurisdictionBattalionNum;

	/** 下辖中队数 */
	private Integer jurisdictionSquadronsNum;

	/* 车辆数 */
	private Integer vehicleNum;
	/* 水源数 */
	private Integer wsNum;
	/* 重点单位数 */
	private Integer keyUnitNum;
	/* 灭火剂储量 */
	private Double  extinguishingAgentInventory;

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
	 * 获取是否独立接警
	 *
	 * @return 是否独立接警
	 */
	public Boolean getIndependent() {
		return this.independent;
	}

	/**
	 * 设置是否独立接警
	 *
	 * @param independent
	 *          是否独立接警
	 */
	public void setIndependent(Boolean independent) {
		this.independent = independent;
	}

	/**
	 * 获取支队长姓名
	 *
	 * @return 支队长姓名
	 */
	public String getCaptainName() {
		return this.captainName;
	}

	/**
	 * 设置支队长姓名
	 *
	 * @param captainName
	 *          支队长姓名
	 */
	public void setCaptainName(String captainName) {
		this.captainName = captainName;
	}

	/**
	 * 获取支队长联系方式
	 *
	 * @return 支队长联系方式
	 */
	public String getCaptainTel() {
		return this.captainTel;
	}

	/**
	 * 设置支队长联系方式
	 *
	 * @param captainTel
	 *          支队长联系方式
	 */
	public void setCaptainTel(String captainTel) {
		this.captainTel = captainTel;
	}

	/**
	 * 获取支队政委姓名
	 *
	 * @return 支队政委姓名
	 */
	public String getPoliticalCommissarName() {
		return this.politicalCommissarName;
	}

	/**
	 * 设置支队政委姓名
	 *
	 * @param politicalCommissarName
	 *          支队政委姓名
	 */
	public void setPoliticalCommissarName(String politicalCommissarName) {
		this.politicalCommissarName = politicalCommissarName;
	}

	/**
	 * 获取支队政委联系方式
	 *
	 * @return 支队政委联系方式
	 */
	public String getPoliticalCommissarTel() {
		return this.politicalCommissarTel;
	}

	/**
	 * 设置支队政委联系方式
	 *
	 * @param politicalCommissarTel
	 *          支队政委联系方式
	 */
	public void setPoliticalCommissarTel(String politicalCommissarTel) {
		this.politicalCommissarTel = politicalCommissarTel;
	}

	/**
	 * 获取消防文员数
	 *
	 * @return 消防文员数
	 */
	public Integer getFirefightingClerkNum() {
		return this.firefightingClerkNum;
	}

	/**
	 * 设置消防文员数
	 *
	 * @param firefightingClerkNum
	 *          消防文员数
	 */
	public void setFirefightingClerkNum(Integer firefightingClerkNum) {
		this.firefightingClerkNum = firefightingClerkNum;
	}

	public Double getJurisdictionAcreage() {
		return jurisdictionAcreage;
	}

	public void setJurisdictionAcreage(Double jurisdictionAcreage) {
		this.jurisdictionAcreage = jurisdictionAcreage;
	}

	public Integer getJurisdictionBattalionNum() {
		return jurisdictionBattalionNum;
	}

	public void setJurisdictionBattalionNum(Integer jurisdictionBattalionNum) {
		this.jurisdictionBattalionNum = jurisdictionBattalionNum;
	}

	public Integer getJurisdictionSquadronsNum() {
		return jurisdictionSquadronsNum;
	}

	public void setJurisdictionSquadronsNum(Integer jurisdictionSquadronsNum) {
		this.jurisdictionSquadronsNum = jurisdictionSquadronsNum;
	}

	public Integer getVehicleNum() {
		return vehicleNum;
	}

	public void setVehicleNum(Integer vehicleNum) {
		this.vehicleNum = vehicleNum;
	}

	public Integer getWsNum() {
		return wsNum;
	}

	public void setWsNum(Integer wsNum) {
		this.wsNum = wsNum;
	}

	public Integer getKeyUnitNum() {
		return keyUnitNum;
	}

	public void setKeyUnitNum(Integer keyUnitNum) {
		this.keyUnitNum = keyUnitNum;
	}

	public Double getExtinguishingAgentInventory() {
		return extinguishingAgentInventory;
	}

	public void setExtinguishingAgentInventory(Double extinguishingAgentInventory) {
		this.extinguishingAgentInventory = extinguishingAgentInventory;
	}

	@Override
	public String getEleType() {
		if(StringUtil.isEmpty(eleType)) {
			eleType = AppDefs.CompEleType.DETACHMENT.toString();
		}
		return eleType;
	}
}