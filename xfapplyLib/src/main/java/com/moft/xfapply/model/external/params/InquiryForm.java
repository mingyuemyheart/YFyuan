package com.moft.xfapply.model.external.params;

import java.util.List;

public class InquiryForm {
    private String belongtoGroup;
    private List<InquiryTypeForm> typeList;

    public String getBelongtoGroup() {
        return belongtoGroup;
    }

    public void setBelongtoGroup(String belongtoGroup) {
        this.belongtoGroup = belongtoGroup;
    }

    public List<InquiryTypeForm> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<InquiryTypeForm> typeList) {
        this.typeList = typeList;
    }
}
