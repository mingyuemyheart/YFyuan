package com.moft.xfapply.model.common;

import java.io.Serializable;

/**
 * Created by sxf on 2019-05-03.
 */

public class WhpViewInfo implements Serializable {
    private String uuid;
    private String name;
    private String property1;
    private String property2;
    private int type = 0;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public String getProperty2() {
        return property2;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
