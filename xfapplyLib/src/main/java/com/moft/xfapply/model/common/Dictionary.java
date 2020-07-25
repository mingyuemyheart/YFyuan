package com.moft.xfapply.model.common;

import java.io.Serializable;
import java.util.List;

public class Dictionary implements Serializable {
    private String id;
    private String code;
    private String value;
    private String parentCode;
    private int resListId = -1;
    private int resMapId = -1;
    private int resCurMapId = -1;
    private List<Dictionary> subDictionary;

    public Dictionary(String value, String code) {
        this.value = value;
        this.code = code;
    }

    public Dictionary(String value, String code, int resListId) {
        this.value = value;
        this.code = code;
        this.resListId = resListId;
    }

    public Dictionary(String value, String code, int resListId, int resMapId, int resCurMapId) {
        this.value = value;
        this.code = code;
        this.resListId = resListId;
        this.resMapId = resMapId;
        this.resCurMapId = resCurMapId;
    }

    public Dictionary(String value, String code, String parentCode) {
        this.value = value;
        this.code = code;
        this.parentCode = parentCode;
    }
    public Dictionary(String id, String value, String code, String parentCode) {
        this.id = id;
        this.value = value;
        this.code = code;
        this.parentCode = parentCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public int getResListId() {
        return resListId;
    }

    public void setResListId(int resListId) {
        this.resListId = resListId;
    }

    public int getResMapId() {
        return resMapId;
    }

    public void setResMapId(int resMapId) {
        this.resMapId = resMapId;
    }

    public int getResCurMapId() {
        return resCurMapId;
    }

    public List<Dictionary> getSubDictionary() {
        return subDictionary;
    }

    public void setSubDictionary(List<Dictionary> subDictionary) {
        this.subDictionary = subDictionary;
    }
}
