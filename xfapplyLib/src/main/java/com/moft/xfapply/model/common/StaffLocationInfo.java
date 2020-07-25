package com.moft.xfapply.model.common;

import java.util.Date;

public class StaffLocationInfo {
	private String staffUuid;
	private Double wgs84Lon;
	private Double wgs84Lat;
	private String description;
	private Date positionTime;
	
	public String getStaffUuid() {
		return staffUuid;
	}
	public void setStaffUuid(String staffUuid) {
		this.staffUuid = staffUuid;
	}
	public Double getWgs84Lon() {
		return wgs84Lon;
	}
	public void setWgs84Lon(Double wgs84Lon) {
		this.wgs84Lon = wgs84Lon;
	}
	public Double getWgs84Lat() {
		return wgs84Lat;
	}
	public void setWgs84Lat(Double wgs84Lat) {
		this.wgs84Lat = wgs84Lat;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getPositionTime() {
		return positionTime;
	}
	public void setPositionTime(Date positionTime) {
		this.positionTime = positionTime;
	}
}
