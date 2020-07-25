package com.moft.xfapply.model.external.dto;

import com.moft.xfapply.model.external.params.InquiryTypeForm;

import java.util.List;

public class UpdateDataDTO {
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
