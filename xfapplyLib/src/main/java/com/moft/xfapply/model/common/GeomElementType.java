package com.moft.xfapply.model.common;

import java.io.Serializable;

import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.utils.DictionaryUtil;
import com.moft.xfapply.utils.StringUtil;

public class GeomElementType implements Serializable {
    private static final long serialVersionUID = -4793530619957947320L;
    
    private String type;
    private String typeCode;
    private int count;

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public String getTypeCode() {
        return typeCode;
    }
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
        this.type = DictionaryUtil.getValueByCode(typeCode, LvApplication.getInstance().getEleTypeDicList());
        
        if (StringUtil.isEmpty(this.type)) {
            this.type = "消防数据";
        }
    }
}
