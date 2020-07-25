package com.moft.xfapply.model.external.dto;

/**
 * fire_facility_compartment
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class FireFacilityCompartmentDTO extends FireFacilityBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 4244237635934145430L;

    /** 区域面积
 */
    private Double area;

    /** 位置
 */
    private String location;

    /** 分隔设施
 */
    private String separateFacility;

    /** 分隔位置
 */
    private String separateLocation;

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSeparateFacility() {
        return separateFacility;
    }

    public void setSeparateFacility(String separateFacility) {
        this.separateFacility = separateFacility;
    }

    public String getSeparateLocation() {
        return separateLocation;
    }

    public void setSeparateLocation(String separateLocation) {
        this.separateLocation = separateLocation;
    }
}