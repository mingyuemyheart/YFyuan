package com.moft.xfapply.model.common;

import com.moft.xfapply.model.base.IMediaInfo;

import java.io.Serializable;
import java.util.List;

/*
 * @Author: 宋满意
 * @Date:   2018-06-01 17:30:53
 * @Last Modified by:   宋满意
 * @Last Modified time: 2019-02-12 16:08:16
 * No.              Date.          Modifier    Description
 * 【S869489】      2019-02-12     宋满意       车辆信息以分类形式展示
 */
public class PropertyDes implements Serializable {

    //ID:S869489 19-02-12 车辆信息以分类形式展示（分为基本类信息，作战用信息，管理用信息），同理包括器材、灭火剂（基本信息、详细信息） 王泉
    public enum PropertyDetail {
        NONE,
        DETAIL,
        MANAGEMENT
    }

    private String name;
    private Object value;
    private int type;
    private String method;
    private Class paramType;
    private boolean isConvertDic = false;
    private boolean isMust = false;
    
    private List<Dictionary> dicList;
    private Dictionary curDic;
    private int curIndex;

    private List<IMediaInfo> attachList;
    private String entityUuid;
    //ID:S869489 19-02-12 车辆信息以分类形式展示（分为基本类信息，作战用信息，管理用信息），同理包括器材、灭火剂（基本信息、详细信息） 王泉
    private PropertyDetail detailType = PropertyDetail.NONE;
    
    public static final int TYPE_NONE = 0;
    public static final int TYPE_LIST = 1;
    public static final int TYPE_EDIT = 2;
    public static final int TYPE_PHOTO = 3;
    public static final int TYPE_MAP_POS = 4;
    public static final int TYPE_PIC_POS = 5;
    public static final int TYPE_DATE = 6;
    public static final int TYPE_PHOTO_STATIC = 7;
    public static final int TYPE_OPERATION = 8;
    public static final int TYPE_ZTPMT_LIST = 9;
    public static final int TYPE_TEXT = 10;
    //追加级联机能
    public static final int TYPE_LIST_RELATE = 11;
    public static final int TYPE_EDIT_ADDRESS = 12;
    public static final int TYPE_PART_LIST = 13;
    public static final int TYPE_FIND_LIST =14;

    public static final int TYPE_DETAIL = 15;
    
    public PropertyDes(String n, Object v) {
        name = n;
        value = v;
        curIndex = -1;
        paramType = null;
        setType(TYPE_NONE);
    }

    public PropertyDes(String n, Object v, int type) {
        name = n;
        value = v;
        curIndex = -1;
        paramType = null;
        setType(type);
    }

    public PropertyDes(String n, Object v, int type, Class s) {
        name = n;
        value = v;
        curIndex = -1;
        paramType = s;
        setType(type);
    }

    //ID:S869489 19-02-12 车辆信息以分类形式展示（分为基本类信息，作战用信息，管理用信息），同理包括器材、灭火剂（基本信息、详细信息） 王泉
    public PropertyDes(String n, String uuid, Object v, int type, PropertyDetail detailType, Class s) {
        name = n;
        value = v;
        curIndex = -1;
        paramType = s;
        setType(type);
        this.entityUuid = uuid;
        this.detailType = detailType;
    }

    public PropertyDes(String n, Object v, List<Dictionary> list) {
        name = n;
        value = v;
        curIndex = -1;
        paramType = null;
        setType(TYPE_LIST);
        if(list != null && list.size() > 0) {
            setDicList(list);
            for (int index = 0; index < list.size(); ++index) {
                Dictionary dic = list.get(index);
                if (dic.getValue().equals(v)) {
                    setCurDic(dic);
                    setCurIndex(index);
                    value = dic.getValue();
                    break;
                }
            }
        }
    }

    public PropertyDes(String n, String m, String v) {
        this(n, v);
        setMethod(m);
    }

    public PropertyDes(String n, String v, Class s) {
        this(n, v);
        setParamType(s);
    }

    public PropertyDes(String n, String m, Class s, Object v) {
        this(n, v);
        setMethod(m);
        setParamType(s);
    }

    public PropertyDes(String n, String m, Class s, Object v, int type) {
        this(n, v, type);
        setMethod(m);
        setParamType(s);
    }

    //ID:S869489 19-02-12 车辆信息以分类形式展示（分为基本类信息，作战用信息，管理用信息），同理包括器材、灭火剂（基本信息、详细信息） 王泉
    public PropertyDes(String n, String m, Class s, String uuid, PropertyDetail detailType, Object v, int type) {
        this(n, v, type);
        setMethod(m);
        setParamType(s);
        this.entityUuid = uuid;
        this.detailType = detailType;
    }

    public PropertyDes(String n, String m, Class s, Object v, int type, boolean isMust) {
        this(n, v, type);
        setMethod(m);
        setParamType(s);
        this.isMust = isMust;
    }

    public PropertyDes(String n, String m, Class s,  Object v, List<Dictionary> list) {
        this(n, v, list);
        setMethod(m);
        setParamType(s);
        this.isConvertDic = true;
    }

    //ID:S869489 19-02-12 车辆信息以分类形式展示（分为基本类信息，作战用信息，管理用信息），同理包括器材、灭火剂（基本信息、详细信息） 王泉
    public PropertyDes(String n, String m, Class s, String uuid, PropertyDetail detailType, Object v, List<Dictionary> list) {
        this(n, v, list);
        setMethod(m);
        setParamType(s);
        this.isConvertDic = true;
        this.entityUuid = uuid;
        this.detailType = detailType;
    }

    public PropertyDes(String n, String m, Class s,  Object v, List<Dictionary> list, boolean isConvertDic) {
        this(n, v, list);
        setMethod(m);
        setParamType(s);
        this.isConvertDic = isConvertDic;
    }

    public PropertyDes(String n, String m, Class s,  Object v, List<Dictionary> list, boolean isConvertDic, boolean isMust) {
        this(n, v, list);
        setMethod(m);
        setParamType(s);
        this.isConvertDic = isConvertDic;
        this.isMust = isMust;
    }

    public PropertyDes(String n, String m, Class s,  Object v, List<IMediaInfo> list, int type) {
        this(n, m, s, v);
        attachList = list;
        this.type = type;
        this.isConvertDic = true;
    }

    public PropertyDes(int type, List<IMediaInfo> list, String entityUuid) {
        attachList = list;
        setType(type);
        this.entityUuid = entityUuid;
    }

    public PropertyDes() {
        name = "";
        value = null;
        curIndex = -1;
        setType(TYPE_NONE);
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Dictionary> getDicList() {
        return dicList;
    }

    public void setDicList(List<Dictionary> dicList) {
        this.dicList = dicList;
    }

    public Dictionary getCurDic() {
        return curDic;
    }

    public void setCurDic(Dictionary curDic) {
        this.curDic = curDic;
    }

    public int getCurIndex() {
        return curIndex;
    }

    public void setCurIndex(int curIndex) {
        this.curIndex = curIndex;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Class getParamType() {
        return paramType;
    }

    public void setParamType(Class paramType) {
        this.paramType = paramType;
    }

    public List<IMediaInfo> getAttachList() {
        return attachList;
    }

    public String getEntityUuid() {
        return entityUuid;
    }

    public void setEntityUuid(String entityUuid) {
        this.entityUuid = entityUuid;
    }

    public boolean isConvertDic() {
        return isConvertDic;
    }

    public void setConvertDic(boolean convertDic) {
        isConvertDic = convertDic;
    }

    public boolean getIsMust() {
        return isMust;
    }

    public void setIsMust(boolean isMust) {
        this.isMust = isMust;
    }

    //ID:S869489 19-02-12 车辆信息以分类形式展示（分为基本类信息，作战用信息，管理用信息），同理包括器材、灭火剂（基本信息、详细信息） 王泉 开始
    public PropertyDetail getDetailType() {
        return detailType;
    }

    public void setDetailType(PropertyDetail detailType) {
        this.detailType = detailType;
    }
    //ID:S869489 19-02-12 车辆信息以分类形式展示（分为基本类信息，作战用信息，管理用信息），同理包括器材、灭火剂（基本信息、详细信息） 王泉 结束
}
