package com.moft.xfapply.model.KeyUnitJson;

import com.moft.xfapply.model.base.IPropertyPartInfo;
import com.moft.xfapply.model.external.AppDefs;
import com.moft.xfapply.utils.StringUtil;
import com.moft.xfapply.utils.Utils;

/**
 * Created by Administrator on 2018/6/14 0014.
 */

public class HazardMediaJsonInfo implements IPropertyPartInfo {
    private String name;
    private String nature;
    private Double storage;
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAttr1() {
        return StringUtil.get(storage);
    }

    @Override
    public void setAttr1(String attr1) {
        storage = Utils.convertToDouble(attr1);
    }

    @Override
    public String getAttr2() {
        return null;
    }

    @Override
    public void setAttr2(String attr2) {

    }

    @Override
    public String getDescription() {
        return nature;
    }

    @Override
    public void setDescription(String description) {
        nature = description;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public Double getStorage() {
        return storage;
    }

    public void setStorage(Double storage) {
        this.storage = storage;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String getType() {
        return AppDefs.KeyUnitJsonType.JSON_HAZARD_MEDIA.toString();
    }
}
