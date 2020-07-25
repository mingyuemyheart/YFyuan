package com.moft.xfapply.model.external.dto;

/**
 * fire_facility_control_room
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class FireFacilityControlRoomDTO extends FireFacilityBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 2190808236934444707L;

    /** 位置 */
    private String location;

    /** 自动报警
 */
    private Boolean autoAlarmUsable;

    /** 联动控制
 */
    private Boolean linkageControlUsable;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getAutoAlarmUsable() {
        return autoAlarmUsable;
    }

    public void setAutoAlarmUsable(Boolean autoAlarmUsable) {
        this.autoAlarmUsable = autoAlarmUsable;
    }

    public Boolean getLinkageControlUsable() {
        return linkageControlUsable;
    }

    public void setLinkageControlUsable(Boolean linkageControlUsable) {
        this.linkageControlUsable = linkageControlUsable;
    }
}