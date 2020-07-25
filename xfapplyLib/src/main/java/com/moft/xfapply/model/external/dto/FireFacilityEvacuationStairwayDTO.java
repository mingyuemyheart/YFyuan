package com.moft.xfapply.model.external.dto;/*
 * Welcome to use the TableGo Tools.
 * 
 * http://vipbooks.iteye.com
 * http://blog.csdn.net/vipbooks
 * http://www.cnblogs.com/vipbooks
 * 
 * Author:bianj
 * Email:edinsker@163.com
 * Version:5.8.8
 */

/**
 * fire_facility_evacuation_stairway
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class FireFacilityEvacuationStairwayDTO extends FireFacilityBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 6847713807114181466L;


    /** 所属建（构）筑
 */
    private String structureName;

    /** 位置
 */
    private String location;
    private Boolean firstFloor; //用于标记是否是首层

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getFirstFloor() {
        return firstFloor;
    }

    public void setFirstFloor(Boolean firstFloor) {
        this.firstFloor = firstFloor;
    }
}