package com.moft.xfapply.model.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/6 0006.
 */

public class Table {
    private String name;
    private String sql;
    List<Attribute> attributes;

    public Table(String name, String sql) {
        this.name = name;
        this.sql = sql;
        attributes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }
}
