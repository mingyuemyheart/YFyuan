/**
 * Copyright (C), 2012-2018, 大连云帆科技有限公司
 * FileName: StationBrigadeDTO
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
 * (总队)
 * 
 * @author wangxy
 * @version 1.0.0 2017-04-14
 */
public class StationBrigadeDTO extends StationBaseDTO implements ISourceDataDTO {

	private static final long serialVersionUID = 5171314158182546052L;

	/** 文职雇员数量 */
	private Integer employeeNum;

	/** 传真 */
	private String fax;

	/** 总队长姓名 */
	private String captainName;

	/** 总队长联系方式 */
	private String captainTel;

	/** 总队政委姓名 */
	private String politicalCommissarName;

	/** 总队政委联系方式 */
	private String politicalCommissarTel;

	/** 下辖支队数 */
	private Integer jurisdictionDetachmentNum;

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
	 * 获取文职雇员数量
	 *
	 * @return 文职雇员数量
	 */
	public Integer getEmployeeNum() {
		return this.employeeNum;
	}

	/**
	 * 设置文职雇员数量
	 *
	 * @param employeeNum
	 *          文职雇员数量
	 */
	public void setEmployeeNum(Integer employeeNum) {
		this.employeeNum = employeeNum;
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
	 * 获取总队长姓名
	 *
	 * @return 总队长姓名
	 */
	public String getCaptainName() {
		return this.captainName;
	}

	/**
	 * 设置总队长姓名
	 *
	 * @param captainName
	 *          总队长姓名
	 */
	public void setCaptainName(String captainName) {
		this.captainName = captainName;
	}

	/**
	 * 获取总队长联系方式
	 *
	 * @return 总队长联系方式
	 */
	public String getCaptainTel() {
		return this.captainTel;
	}

	/**
	 * 设置总队长联系方式
	 *
	 * @param captainTel
	 *          总队长联系方式
	 */
	public void setCaptainTel(String captainTel) {
		this.captainTel = captainTel;
	}

	/**
	 * 获取总队政委姓名
	 *
	 * @return 总队政委姓名
	 */
	public String getPoliticalCommissarName() {
		return this.politicalCommissarName;
	}

	/**
	 * 设置总队政委姓名
	 *
	 * @param politicalCommissarName
	 *          总队政委姓名
	 */
	public void setPoliticalCommissarName(String politicalCommissarName) {
		this.politicalCommissarName = politicalCommissarName;
	}

	/**
	 * 获取总队政委联系方式
	 *
	 * @return 总队政委联系方式
	 */
	public String getPoliticalCommissarTel() {
		return this.politicalCommissarTel;
	}

	/**
	 * 设置总队政委联系方式
	 *
	 * @param politicalCommissarTel
	 *          总队政委联系方式
	 */
	public void setPoliticalCommissarTel(String politicalCommissarTel) {
		this.politicalCommissarTel = politicalCommissarTel;
	}

	public Integer getJurisdictionDetachmentNum() {
		return jurisdictionDetachmentNum;
	}

	public void setJurisdictionDetachmentNum(Integer jurisdictionDetachmentNum) {
		this.jurisdictionDetachmentNum = jurisdictionDetachmentNum;
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
			eleType = AppDefs.CompEleType.BRIGADE.toString();
		}
		return eleType;
	}
}