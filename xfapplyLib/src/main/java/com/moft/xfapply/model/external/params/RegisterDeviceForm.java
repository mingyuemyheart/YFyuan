package com.moft.xfapply.model.external.params;

/**
 * 
 * 
 * @author cuihj
 * @version 1.0.0 2017-04-20
 */
public class RegisterDeviceForm implements java.io.Serializable {

	private static final long serialVersionUID = -3896074405886159171L;
	
	private String appId;		// DeviceAppIdentity
	private String identity;	// use DEVICE IMEI
	private String simNo;
	private String deviceType;	// DeviceType
	private String deviceSpec;
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getSimNo() {
		return simNo;
	}
	public void setSimNo(String simNo) {
		this.simNo = simNo;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceSpec() {
		return deviceSpec;
	}
	public void setDeviceSpec(String deviceSpec) {
		this.deviceSpec = deviceSpec;
	}
}