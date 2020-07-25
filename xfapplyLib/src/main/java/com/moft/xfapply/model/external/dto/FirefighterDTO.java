package com.moft.xfapply.model.external.dto;

import java.util.Date;

/**
 * 用户(Firefighter)
 * 
 * @author wangxy
 * @version 1.0.0 2018-05-17
 */
public class FirefighterDTO extends RestDTO implements java.io.Serializable {
    
    private static final long serialVersionUID = 473170854069306248L;
    
	private String uuid; // 唯一标识
	private String departmentUuid; // 关联组织
	private String personalCode; // 人员编码
	private String name; // 姓名
	private String contactMobile; // 联系电话码
	private String sex; // 性别
	private String enlistYear; // 入伍时间-年
	private String enlistMonth; // 入伍时间-月
	private Date birthDate; // 出生年月
	private String politicalOutlook; // 政治面貌
	private String cultureDegree; // 文化程度
	private String duty; // 职务
	private String policeRankUuid; // 警衔
	private String yearLimit; // 年限
	private String address; // 家庭住址
	private String type; // 用户类型
	private String createPerson; // 提交人
	private Date createDate; // 创建时间
	private String updatePerson; // 最新更新者
	private Date updateDate; // 创建时间
	private Boolean deleted;   // 删除标识
	private String remark; // 备注

	private SysDepartmentDTO sysDepartmentDTO;
	private AuthSecurityAccountDTO authSecurityAccountDTO;


	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDepartmentUuid() {
		return departmentUuid;
	}

	public void setDepartmentUuid(String departmentUuid) {
		this.departmentUuid = departmentUuid;
	}

	public String getPersonalCode() {
		return personalCode;
	}

	public void setPersonalCode(String personalCode) {
		this.personalCode = personalCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEnlistYear() {
		return enlistYear;
	}

	public void setEnlistYear(String enlistYear) {
		this.enlistYear = enlistYear;
	}

	public String getEnlistMonth() {
		return enlistMonth;
	}

	public void setEnlistMonth(String enlistMonth) {
		this.enlistMonth = enlistMonth;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getPoliticalOutlook() {
		return politicalOutlook;
	}

	public void setPoliticalOutlook(String politicalOutlook) {
		this.politicalOutlook = politicalOutlook;
	}

	public String getCultureDegree() {
		return cultureDegree;
	}

	public void setCultureDegree(String cultureDegree) {
		this.cultureDegree = cultureDegree;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getPoliceRankUuid() {
		return policeRankUuid;
	}

	public void setPoliceRankUuid(String policeRankUuid) {
		this.policeRankUuid = policeRankUuid;
	}

	public String getYearLimit() {
		return yearLimit;
	}

	public void setYearLimit(String yearLimit) {
		this.yearLimit = yearLimit;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public SysDepartmentDTO getSysDepartmentDTO() {
		return sysDepartmentDTO;
	}

	public void setSysDepartmentDTO(SysDepartmentDTO sysDepartmentDTO) {
		this.sysDepartmentDTO = sysDepartmentDTO;
	}

	public AuthSecurityAccountDTO getAuthSecurityAccountDTO() {
		return authSecurityAccountDTO;
	}

	public void setAuthSecurityAccountDTO(AuthSecurityAccountDTO authSecurityAccountDTO) {
		authSecurityAccountDTO = authSecurityAccountDTO;
	}
}