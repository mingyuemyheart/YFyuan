package com.moft.xfapply.model.common;

/**
 * Created by sxf on 2017/5/6.
 */

public class SearchSummaryInfo {
    private String typeCode;
    private String type;
    private int count;

    public String getType() {
        return type;
    }

    public int getCount() {
        return count;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}
