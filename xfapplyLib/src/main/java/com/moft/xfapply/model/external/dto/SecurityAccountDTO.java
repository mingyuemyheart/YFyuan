package com.moft.xfapply.model.external.dto;

/**
 * 账号(SECURITY_ACCOUNT)
 * 
 * @author wangxy
 * @version 1.0.0 2017-04-14
 */
public class SecurityAccountDTO extends RestDTO implements java.io.Serializable {
     
    private static final long serialVersionUID = -28592938846686977L;
    
    private Integer id; // 主键
    private String uuid;
    private String userName; // 名称
    private String password; // 密码
    private String  accountType; // 类型
	private String belongtoGroup; //所属团体
	
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getBelongtoGroup() {
		return belongtoGroup;
	}

	public void setBelongtoGroup(String belongtoGroup) {
		this.belongtoGroup = belongtoGroup;
	}
}