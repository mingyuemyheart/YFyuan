package com.moft.xfapply.model.common;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class DeviceAccessParam implements Serializable {
    private static final long serialVersionUID = 2025397875277319490L;
    private String identity;
    private String appIdentity;
    private String loginDate;
    private String lastOperateDate;

    public DeviceAccessParam() {
    }

    public String getIdentity() {
        return this.identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getAppIdentity() {
        return this.appIdentity;
    }

    public void setAppIdentity(String appIdentity) {
        this.appIdentity = appIdentity;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public String getLastOperateDate() {
        return lastOperateDate;
    }

    public void setLastOperateDate(String lastOperateDate) {
        this.lastOperateDate = lastOperateDate;
    }
}
