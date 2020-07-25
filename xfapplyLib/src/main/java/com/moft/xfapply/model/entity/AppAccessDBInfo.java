package com.moft.xfapply.model.entity;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Property;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;

import java.util.Date;

@Table(name = "app_access")
public class AppAccessDBInfo implements java.io.Serializable {
    /**
     * @Fields serialVersionUID :
     */
    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    private int id;// 主键
    @Property(column = "device_app_id")
    private String deviceAppId;
    @Property(column = "login_date")
    private Long loginDate;
    @Property(column = "last_operate_date")
    private Long lastOperateDate;
    @Property(column = "is_commit")
    private Boolean isCommit;
    private String remark;

    public AppAccessDBInfo() {
        isCommit = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceAppId() {
        return deviceAppId;
    }

    public void setDeviceAppId(String deviceAppId) {
        this.deviceAppId = deviceAppId;
    }

    public Long getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Long loginDate) {
        this.loginDate = loginDate;
    }

    public Long getLastOperateDate() {
        return lastOperateDate;
    }

    public void setLastOperateDate(Long lastOperateDate) {
        this.lastOperateDate = lastOperateDate;
    }

    public Boolean getIsCommit() {
        return isCommit;
    }

    public void setIsCommit(Boolean commit) {
        isCommit = commit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
