package com.moft.xfapply.model.external.dto;

/**
 * 系统用户
 *
 * @author wangxy
 * @version 1.0.0 2018-05-21
 */
public class AuthUserDTO extends RestDTO implements java.io.Serializable {

	private static final long serialVersionUID = -28592938846686977L;

	private String  uuid;
	private String  departmentUuid; // 所属部门
	private String  firefighterUuid; // 人员标识
	private String  accountUuid; // 账号标识

	private AccessTokenDTO accessTokenDTO;
	private FirefighterDTO firefighterDTO;

	private SysDepartmentDTO sysDepartmentDTO;

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

	public String getFirefighterUuid() {
		return firefighterUuid;
	}

	public void setFirefighterUuid(String firefighterUuid) {
		this.firefighterUuid = firefighterUuid;
	}

	public String getAccountUuid() {
		return accountUuid;
	}

	public void setAccountUuid(String accountUuid) {
		this.accountUuid = accountUuid;
	}

	public AccessTokenDTO getAccessTokenDTO() {
		return accessTokenDTO;
	}

	public void setAccessTokenDTO(AccessTokenDTO accessTokenDTO) {
		this.accessTokenDTO = accessTokenDTO;
	}

	public FirefighterDTO getFirefighterDTO() {
		return firefighterDTO;
	}

	public void setFirefighterDTO(FirefighterDTO firefighterDTO) {
		this.firefighterDTO = firefighterDTO;
	}


	public SysDepartmentDTO getSysDepartmentDTO() {
		return sysDepartmentDTO;
	}

	public void setSysDepartmentDTO(SysDepartmentDTO sysDepartmentDTO) {
		this.sysDepartmentDTO = sysDepartmentDTO;
	}
}