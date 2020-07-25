package com.moft.xfapply.model.external.dto;

public class CompSubmitVerifyInfo {
	private String cId;	// client submit ID
	private String verifyUuid;
	 
	public String getcId() {
		return cId;
	}
	public void setcId(String cId) {
		this.cId = cId;
	}
	public String getVerifyUuid() {
		return verifyUuid;
	}
	public void setVerifyUuid(String verifyUuid) {
		this.verifyUuid = verifyUuid;
	}
}
