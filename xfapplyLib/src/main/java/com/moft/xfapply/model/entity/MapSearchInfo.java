package com.moft.xfapply.model.entity;

import com.moft.xfapply.app.Constant;
import com.moft.xfapply.model.common.EleCondition;
import com.moft.xfapply.utils.StringUtil;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;

import java.io.Serializable;

@Table(name = "map_search_info")
public class MapSearchInfo implements Serializable {
    /**
     * @Fields serialVersionUID :
     */
    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    private Integer id;
    private String type;
    private String name;
    private String address;
    private Integer lat;
    private Integer lng;
    private String uid;
    private String extra;
    private Object tag;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public MapSearchInfo toPosition() {
        MapSearchInfo pos = new MapSearchInfo();

        pos.id = id;
        pos.type = Constant.MAP_SEARCH_POSITION;
        pos.name = name;
        pos.address = address;
        pos.lat = lat;
        pos.lng = lng;
        pos.uid = uid;
        pos.extra = extra;

        return pos;
    }

    public Integer getLat() {
        if (lat == null) {
            return 0;
        }
        return lat;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

    public Integer getLng() {
        if (lng == null) {
            return 0;
        }
        return lng;
    }

    public boolean isGeoPosValid() {
        if (getLat() == null || getLng() == null ||
                getLat() == 0 || getLng() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void setLng(Integer lng) {
        this.lng = lng;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public EleCondition toEleCondition() {
        EleCondition ec = new EleCondition();
        if (!StringUtil.isEmpty(extra)) {
            String[] exs = extra.split(Constant.OUTLINE_DIVIDER);
            if (exs != null && exs.length >= 3) {
                ec.setUuid(exs[1]);
                ec.setName(this.name);
                ec.setType(exs[0]);
                ec.setCityCode(exs[2]);
            }
        }
        return ec;
    }
}
