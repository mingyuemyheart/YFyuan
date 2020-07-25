/**
 * Copyright (C), 2012-2018, 大连云帆科技有限公司
 * FileName: CompUnionpowerDTO
 * Author:   ZhangShouyan
 * Date:     2018/3/22 0022 19:28
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.moft.xfapply.model.external.dto;

/**
 * 联动力量(comp_unionpower)
 * 
 * @author wangxy
 * @version 1.0.0 2017-05-09
 */
public class CompUnionpowerDTO implements java.io.Serializable {

	private static final long serialVersionUID = 2103611272431341560L;

	private String dutyPhone; // 值班电话
	private String fax; // 传真
	private String contactName; // 姓名
	private String duties; // 职务
	private String officePhone; // 办公电话
	private String homePhone; // 住宅电话
	private String mobilePhone; // 移动电话

	public String getDutyPhone() {
		return dutyPhone;
	}

	public void setDutyPhone(String dutyPhone) {
		this.dutyPhone = dutyPhone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getDuties() {
		return duties;
	}

	public void setDuties(String duties) {
		this.duties = duties;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

}