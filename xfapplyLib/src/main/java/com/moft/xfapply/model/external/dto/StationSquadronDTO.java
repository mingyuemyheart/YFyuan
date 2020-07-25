/**
 * Copyright (C), 2012-2018, 大连云帆科技有限公司
 * FileName: StationSquadronDTO
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
 * (中队)
 * 
 * @author wangxy
 * @version 1.0.0 2017-04-14
 */
public class StationSquadronDTO extends StationBaseDTO implements ISourceDataDTO {

	private static final long serialVersionUID = 3694403908872855515L;

	/** 队伍类型 */
	private String squadronType;

	/** 辖区范围 */
	private String jurisdictionRange;

	/** 辖区面积（平方公里） */
	private Double jurisdictionAcreage;

	/** 每日执勤人数 */
	private Integer dutyNum;

	/** 中队长姓名 */
	private String squadronLeaderName;

	/** 中队长联系方式 */
	private String squadronLeaderTel;

	/** 指导员姓名 */
	private String instructorName;

	/** 指导员联系方式 */
	private String instructorTel;

	/** 副指导员姓名 */
	private String viceInstructorName;

	/** 副指导员联系方式 */
	private String viceInstructorTel;

	/** 副中队长一姓名 */
	private String viceSquadronLeaderOneName;

	/** 副中队长一联系方式 */
	private String viceSquadronLeaderFirstTel;

	/** 副中队长二姓名 */
	private String viceSquadronLeaderSecondName;

	/** 副中队长二联系方式 */
	private String viceSquadronLeaderSecondTel;

	/** 副中队长三姓名 */
	private String viceSquadronLeaderThirdName;

	/** 副中队长三联系方式 */
	private String viceSquadronLeaderThirdTel;

	public String getSquadronType() {
		return squadronType;
	}

	public void setSquadronType(String squadronType) {
		this.squadronType = squadronType;
	}

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
	 * 获取中队长姓名
	 *
	 * @return 中队长姓名
	 */
	public String getSquadronLeaderName() {
		return this.squadronLeaderName;
	}

	/**
	 * 设置中队长姓名
	 *
	 * @param squadronLeaderName
	 *          中队长姓名
	 */
	public void setSquadronLeaderName(String squadronLeaderName) {
		this.squadronLeaderName = squadronLeaderName;
	}

	/**
	 * 获取中队长联系方式
	 *
	 * @return 中队长联系方式
	 */
	public String getSquadronLeaderTel() {
		return this.squadronLeaderTel;
	}

	/**
	 * 设置中队长联系方式
	 *
	 * @param squadronLeaderTel
	 *          中队长联系方式
	 */
	public void setSquadronLeaderTel(String squadronLeaderTel) {
		this.squadronLeaderTel = squadronLeaderTel;
	}

	/**
	 * 获取指导员姓名
	 *
	 * @return 指导员姓名
	 */
	public String getInstructorName() {
		return this.instructorName;
	}

	/**
	 * 设置指导员姓名
	 *
	 * @param instructorName
	 *          指导员姓名
	 */
	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

	/**
	 * 获取指导员联系方式
	 *
	 * @return 指导员联系方式
	 */
	public String getInstructorTel() {
		return this.instructorTel;
	}

	/**
	 * 设置指导员联系方式
	 *
	 * @param instructorTel
	 *          指导员联系方式
	 */
	public void setInstructorTel(String instructorTel) {
		this.instructorTel = instructorTel;
	}

	/**
	 * 获取副指导员姓名
	 *
	 * @return 副指导员姓名
	 */
	public String getViceInstructorName() {
		return this.viceInstructorName;
	}

	/**
	 * 设置副指导员姓名
	 *
	 * @param viceInstructorName
	 *          副指导员姓名
	 */
	public void setViceInstructorName(String viceInstructorName) {
		this.viceInstructorName = viceInstructorName;
	}

	/**
	 * 获取副指导员联系方式
	 *
	 * @return 副指导员联系方式
	 */
	public String getViceInstructorTel() {
		return this.viceInstructorTel;
	}

	/**
	 * 设置副指导员联系方式
	 *
	 * @param viceInstructorTel
	 *          副指导员联系方式
	 */
	public void setViceInstructorTel(String viceInstructorTel) {
		this.viceInstructorTel = viceInstructorTel;
	}

	/**
	 * 获取副中队长一姓名
	 *
	 * @return 副中队长一姓名
	 */
	public String getViceSquadronLeaderOneName() {
		return this.viceSquadronLeaderOneName;
	}

	/**
	 * 设置副中队长一姓名
	 *
	 * @param viceSquadronLeaderOneName
	 *          副中队长一姓名
	 */
	public void setViceSquadronLeaderOneName(String viceSquadronLeaderOneName) {
		this.viceSquadronLeaderOneName = viceSquadronLeaderOneName;
	}

	/**
	 * 获取副中队长一联系方式
	 *
	 * @return 副中队长一联系方式
	 */
	public String getViceSquadronLeaderFirstTel() {
		return this.viceSquadronLeaderFirstTel;
	}

	/**
	 * 设置副中队长一联系方式
	 *
	 * @param viceSquadronLeaderFirstTel
	 *          副中队长一联系方式
	 */
	public void setViceSquadronLeaderFirstTel(String viceSquadronLeaderFirstTel) {
		this.viceSquadronLeaderFirstTel = viceSquadronLeaderFirstTel;
	}

	/**
	 * 获取副中队长二姓名
	 *
	 * @return 副中队长二姓名
	 */
	public String getViceSquadronLeaderSecondName() {
		return this.viceSquadronLeaderSecondName;
	}

	/**
	 * 设置副中队长二姓名
	 *
	 * @param viceSquadronLeaderSecondName
	 *          副中队长二姓名
	 */
	public void setViceSquadronLeaderSecondName(String viceSquadronLeaderSecondName) {
		this.viceSquadronLeaderSecondName = viceSquadronLeaderSecondName;
	}

	/**
	 * 获取副中队长二联系方式
	 *
	 * @return 副中队长二联系方式
	 */
	public String getViceSquadronLeaderSecondTel() {
		return this.viceSquadronLeaderSecondTel;
	}

	/**
	 * 设置副中队长二联系方式
	 *
	 * @param viceSquadronLeaderSecondTel
	 *          副中队长二联系方式
	 */
	public void setViceSquadronLeaderSecondTel(String viceSquadronLeaderSecondTel) {
		this.viceSquadronLeaderSecondTel = viceSquadronLeaderSecondTel;
	}

	/**
	 * 获取副中队长三姓名
	 *
	 * @return 副中队长三姓名
	 */
	public String getViceSquadronLeaderThirdName() {
		return this.viceSquadronLeaderThirdName;
	}

	/**
	 * 设置副中队长三姓名
	 *
	 * @param viceSquadronLeaderThirdName
	 *          副中队长三姓名
	 */
	public void setViceSquadronLeaderThirdName(String viceSquadronLeaderThirdName) {
		this.viceSquadronLeaderThirdName = viceSquadronLeaderThirdName;
	}

	/**
	 * 获取副中队长三联系方式
	 *
	 * @return 副中队长三联系方式
	 */
	public String getViceSquadronLeaderThirdTel() {
		return this.viceSquadronLeaderThirdTel;
	}

	/**
	 * 设置副中队长三联系方式
	 *
	 * @param viceSquadronLeaderThirdTel
	 *          副中队长三联系方式
	 */
	public void setViceSquadronLeaderThirdTel(String viceSquadronLeaderThirdTel) {
		this.viceSquadronLeaderThirdTel = viceSquadronLeaderThirdTel;
	}

	@Override
	public String getEleType() {
		if(StringUtil.isEmpty(eleType)) {
			eleType = AppDefs.CompEleType.SQUADRON.toString();
		}
		return eleType;
	}
}