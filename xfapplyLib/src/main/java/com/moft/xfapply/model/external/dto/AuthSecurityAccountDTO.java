package com.moft.xfapply.model.external.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 账号(SECURITY_ACCOUNT)
 * 
 * @author wangxy
 * @version 1.0.0 2017-04-14
 */
public class AuthSecurityAccountDTO extends RestDTO implements java.io.Serializable {
     
    private static final long serialVersionUID = -28592938846686977L;
    
    private String uuid;
	private String  userName; // 名称
	private String  password; // 密码
	private Boolean  loginFlag; // 是否可以登陆
	private Boolean  deleted; // 删除标志
	private  String accountType; //
	private  String loginIp; // 最后登陆IP
	private Date loginDate; // 最后登陆时间
	private  String belongtoGroup; // 所属地区 大连2102
	private String  remark; // 备注

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(Boolean loginFlag) {
		this.loginFlag = loginFlag;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getBelongtoGroup() {
		return belongtoGroup;
	}

	public void setBelongtoGroup(String belongtoGroup) {
		this.belongtoGroup = belongtoGroup;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}