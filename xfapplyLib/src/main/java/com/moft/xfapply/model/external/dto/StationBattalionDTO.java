/**
 * Copyright (C), 2012-2018, 大连云帆科技有限公司
 * FileName: StationBattalionDTO
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
 * (大队)
 * 
 * @author wangxy
 * @version 1.0.0 2017-04-14
 */
public class StationBattalionDTO extends StationBaseDTO implements ISourceDataDTO {

	private static final long serialVersionUID = -2334258848225027584L;

	/** 辖区范围 */
	private String jurisdictionRange;

	/** 辖区面积（平方公里） */
	private Double jurisdictionAcreage;

	/** 是否独立接警 */
	private Boolean independent;

	/** 大队长姓名 */
	private String captainName;

	/** 大队长联系方式 */
	private String captainTel;

	/** 教导员姓名 */
	private String instructorName;

	/** 教导员联系方式 */
	private String instructorTel;

	/** 副大队长姓名 */
	private String viceCaptainName;

	/** 副大队长联系方式 */
	private String viceCaptainTel;

	/** 副教导员姓名 */
	private String viceInstructorName;

	/** 副教导员联系方式 */
	private String viceInstructorTel;

	/** 消防文员数 */
	private Integer firefightingClerkNum;
	/** 下辖中队现役官兵总数 */
	private Integer overallSquadronDutyNum;

	/** 大队总人数 */
	private Integer overallPersonNumber;

	/**
	 * 获取辖区范围
	 *
	 * @return 辖区范围
	 */
	public String getJurisdictionRange() {
		return this.jurisdictionRange;
	}

	/**
	 * 设置辖区范围
	 *
	 * @param jurisdictionRange
	 *          辖区范围
	 */
	public void setJurisdictionRange(String jurisdictionRange) {
		this.jurisdictionRange = jurisdictionRange;
	}

	/**
	 * 获取辖区面积（平方公里）
	 *
	 * @return 辖区面积（平方公里）
	 */
	public Double getJurisdictionAcreage() {
		return this.jurisdictionAcreage;
	}

	/**
	 * 设置辖区面积（平方公里）
	 *
	 * @param jurisdictionAcreage
	 *          辖区面积（平方公里）
	 */
	public void setJurisdictionAcreage(Double jurisdictionAcreage) {
		this.jurisdictionAcreage = jurisdictionAcreage;
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
	 * 获取大队长姓名
	 *
	 * @return 大队长姓名
	 */
	public String getCaptainName() {
		return this.captainName;
	}

	/**
	 * 设置大队长姓名
	 *
	 * @param captainName
	 *          大队长姓名
	 */
	public void setCaptainName(String captainName) {
		this.captainName = captainName;
	}

	/**
	 * 获取大队长联系方式
	 *
	 * @return 大队长联系方式
	 */
	public String getCaptainTel() {
		return this.captainTel;
	}

	/**
	 * 设置大队长联系方式
	 *
	 * @param captainTel
	 *          大队长联系方式
	 */
	public void setCaptainTel(String captainTel) {
		this.captainTel = captainTel;
	}

	/**
	 * 获取教导员姓名
	 *
	 * @return 教导员姓名
	 */
	public String getInstructorName() {
		return this.instructorName;
	}

	/**
	 * 设置教导员姓名
	 *
	 * @param instructorName
	 *          教导员姓名
	 */
	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

	/**
	 * 获取教导员联系方式
	 *
	 * @return 教导员联系方式
	 */
	public String getInstructorTel() {
		return this.instructorTel;
	}

	/**
	 * 设置教导员联系方式
	 *
	 * @param instructorTel
	 *          教导员联系方式
	 */
	public void setInstructorTel(String instructorTel) {
		this.instructorTel = instructorTel;
	}

	/**
	 * 获取副大队长姓名
	 *
	 * @return 副大队长姓名
	 */
	public String getViceCaptainName() {
		return this.viceCaptainName;
	}

	/**
	 * 设置副大队长姓名
	 *
	 * @param viceCaptainName
	 *          副大队长姓名
	 */
	public void setViceCaptainName(String viceCaptainName) {
		this.viceCaptainName = viceCaptainName;
	}

	/**
	 * 获取副大队长联系方式
	 *
	 * @return 副大队长联系方式
	 */
	public String getViceCaptainTel() {
		return this.viceCaptainTel;
	}

	/**
	 * 设置副大队长联系方式
	 *
	 * @param viceCaptainTel
	 *          副大队长联系方式
	 */
	public void setViceCaptainTel(String viceCaptainTel) {
		this.viceCaptainTel = viceCaptainTel;
	}

	/**
	 * 获取副教导员姓名
	 *
	 * @return 副教导员姓名
	 */
	public String getViceInstructorName() {
		return this.viceInstructorName;
	}

	/**
	 * 设置副教导员姓名
	 *
	 * @param viceInstructorName
	 *          副教导员姓名
	 */
	public void setViceInstructorName(String viceInstructorName) {
		this.viceInstructorName = viceInstructorName;
	}

	/**
	 * 获取副教导员联系方式
	 *
	 * @return 副教导员联系方式
	 */
	public String getViceInstructorTel() {
		return this.viceInstructorTel;
	}

	/**
	 * 设置副教导员联系方式
	 *
	 * @param viceInstructorTel
	 *          副教导员联系方式
	 */
	public void setViceInstructorTel(String viceInstructorTel) {
		this.viceInstructorTel = viceInstructorTel;
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

	@Override
	public String getEleType() {
		if(StringUtil.isEmpty(eleType)) {
			eleType = AppDefs.CompEleType.BATTALION.toString();
		}
		return eleType;
	}

	public Integer getOverallSquadronDutyNum() {
		return overallSquadronDutyNum;
	}

	public void setOverallSquadronDutyNum(Integer overallSquadronDutyNum) {
		this.overallSquadronDutyNum = overallSquadronDutyNum;
	}

	public Integer getOverallPersonNumber() {
		return overallPersonNumber;
	}

	public void setOverallPersonNumber(Integer overallPersonNumber) {
		this.overallPersonNumber = overallPersonNumber;
	}
}