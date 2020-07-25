package com.moft.xfapply.model.external.dto;

import java.util.List;

public class CompSubmitResultDTO extends RestDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String submitUuid;
	private List<CompSubmitVerifyInfo> verifyInfos;
	
	public String getSubmitUuid() {
		return submitUuid;
	}
	public void setSubmitUuid(String submitUuid) {
		this.submitUuid = submitUuid;
	}
	public List<CompSubmitVerifyInfo> getVerifyInfos() {
		return verifyInfos;
	}
	public void setVerifyInfos(List<CompSubmitVerifyInfo> verifyInfos) {
		this.verifyInfos = verifyInfos;
	}
}
