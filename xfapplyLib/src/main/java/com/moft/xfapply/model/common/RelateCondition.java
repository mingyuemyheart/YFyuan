package com.moft.xfapply.model.common;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/29 0029.
 */

public class RelateCondition implements Serializable {
    private String relateCode;
    private String parentCode;
    private boolean isRoot;

    public RelateCondition(String relateCode, boolean isRoot) {
        this.relateCode = relateCode;
        this.parentCode = "";
        this.isRoot = isRoot;
    }

    public String getRelateCode() {
        return relateCode;
    }

    public void setRelateCode(String relateCode) {
        this.relateCode = relateCode;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }
}
