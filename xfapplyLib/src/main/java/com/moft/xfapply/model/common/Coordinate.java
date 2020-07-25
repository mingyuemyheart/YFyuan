
package com.moft.xfapply.model.common;

public class Coordinate {
	public final static double pi = 3.14159265358979324; 
	public final static double x_pi = pi * 3000.0 / 180.0; 
	
	private Double lat;
	private Double lon;
	
	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Coordinate() {
	}
	
	public Coordinate(Double lon,Double lat) {
		super();
		this.lon = lon;
		this.lat = lat;
	}
	
	public Coordinate(String s) {
		super();
		try {
			int p = s.indexOf("_");
			this.lon = Double.valueOf(s.substring(0, p));
			this.lat = Double.valueOf(s.substring(p+1));
		} catch(Exception e) {
			this.lon = null;
			this.lat = null;
		}
	}
	
}
