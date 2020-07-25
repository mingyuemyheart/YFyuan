package com.moft.xfapply.model.common;

import com.moft.xfapply.utils.StringUtil;

import java.io.Serializable;

/**
 * Created by sxf on 2019-04-02.
 */

public class QueryCondition implements Serializable {
    private static final long serialVersionUID = 1864522106387297952L;

    public static final int TYPE_INIT = -1;
    public static final int TYPE_KEY = 0;
    public static final int TYPE_POSITION = 1;
    public static final int TYPE_SYLX = 2;

    private int type;

    private String key;

    private Double lat;
    private Double lng;
    private String name;
    private String address;

    private String sylx;
    private String sylxCode;
    private String lxSubCode;
    private String zqzd;
    private String zqzdCode;

    private String filterSQL;
    private String filterDesc;

    public void reset() {
        type = TYPE_INIT;
        key = "";
        lat = null;
        lng = null;
        name = "";
        address = "";
        sylx = "";
        sylxCode = "";
        lxSubCode = "";
        zqzd = "";
        zqzdCode = "";
        filterSQL = "";
        filterDesc = "";
    }

    public void copy(QueryCondition qc) {
        type = qc.type;
        key = qc.key;
        lat = qc.lat;
        lng = qc.lng;
        name = qc.name;
        address = qc.address;
        sylx = qc.sylx;
        sylxCode = qc.sylxCode;
        lxSubCode = qc.lxSubCode;
        zqzd = qc.zqzd;
        zqzdCode = qc.zqzdCode;
        filterSQL = qc.filterSQL;
        filterDesc = qc.filterDesc;
    }

    public String getSearchBarText() {
        String content = "";
        if (TYPE_KEY == type) {
            content = key;
        } else if (TYPE_POSITION == type) {
            if (!StringUtil.isEmpty(name)) {
                content = name;
            } else {
                content = address;
            }
            if (!StringUtil.isEmpty(filterSQL) ||
                    !StringUtil.isEmpty(key) ||
                    !(StringUtil.isEmpty(zqzdCode) || "00".equals(zqzdCode)) ||
                    !StringUtil.isEmpty(sylxCode)) {
                content += ";条件筛选" ;
            }
        } else if (TYPE_SYLX == type) {
            content = "类别:" + sylx;
            if (!StringUtil.isEmpty(filterSQL) ||
                    !StringUtil.isEmpty(key) ||
                    !(StringUtil.isEmpty(zqzdCode) || "00".equals(zqzdCode))) {
                content += ";条件筛选" ;
            }
        }
        return content;
    }

    public String getTitle() {
        String title = "消防数据";
        if (!StringUtil.isEmpty(sylxCode) && !StringUtil.isEmpty(sylx)) {
            title = sylx;
        }
        return title;
    }

    public String getDescribe() {
        String con = "";

        switch (type) {
            case TYPE_KEY:
                if (StringUtil.isEmpty(key)) {
                    con = "全部";
                } else {
                    con = "关键字:" + key;
                }
                break;
            case TYPE_POSITION:
                con = "未知位置";
                if (!StringUtil.isEmpty(name)) {
                    con = name;
                } else if (!StringUtil.isEmpty(address)) {
                    con = address;
                }
                break;
            case TYPE_SYLX:
                if (StringUtil.isEmpty(sylx)) {
                    con = "消防数据";
                } else {
                    con = sylx;
                }
                break;
        }

        return con;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        reset();
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSylx() {
        return sylx;
    }

    public void setSylx(String sylx) {
        this.sylx = sylx;
    }

    public String getSylxCode() {
        return sylxCode;
    }

    public void setSylxCode(String sylxCode) {
        this.sylxCode = sylxCode;
    }

    public String getLxSubCode() {
        return lxSubCode;
    }

    public void setLxSubCode(String lxSubCode) {
        this.lxSubCode = lxSubCode;
    }

    public String getZqzd() {
        return zqzd;
    }

    public void setZqzd(String zqzd) {
        this.zqzd = zqzd;
    }

    public String getZqzdCode() {
        return zqzdCode;
    }

    public void setZqzdCode(String zqzdCode) {
        this.zqzdCode = zqzdCode;
    }

    public String getFilterSQL() {
        return filterSQL;
    }

    public void setFilterSQL(String filterSQL) {
        this.filterSQL = filterSQL;
    }

    public String getFilterDesc() {
        return filterDesc;
    }

    public void setFilterDesc(String filterDesc) {
        this.filterDesc = filterDesc;
    }
}
