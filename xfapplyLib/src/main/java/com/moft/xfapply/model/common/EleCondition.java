package com.moft.xfapply.model.common;

import com.moft.xfapply.app.LvApplication;

import java.io.Serializable;

public class EleCondition implements Serializable {
    private String uuid;
    private String name;
    private String type;
    private String cityCode;
    private Boolean isHistory;

    public EleCondition() {
        uuid = "";
        type = "";
        cityCode = LvApplication.getInstance().getCityCode();
        isHistory = false;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Boolean getHistory() {
        return isHistory;
    }

    public void setHistory(Boolean history) {
        isHistory = history;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
