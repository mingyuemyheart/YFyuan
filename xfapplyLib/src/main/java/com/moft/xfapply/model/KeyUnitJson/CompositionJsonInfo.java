package com.moft.xfapply.model.KeyUnitJson;

import com.moft.xfapply.model.base.IPropertyPartInfo;
import com.moft.xfapply.model.external.AppDefs;

/**
 * Created by Administrator on 2018/6/14 0014.
 */

public class CompositionJsonInfo implements IPropertyPartInfo {
    private String name;
    private String function;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAttr1() {
        return null;
    }

    @Override
    public void setAttr1(String attr1) {

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
        return function;
    }

    @Override
    public void setDescription(String description) {
        function = description;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    @Override
    public String getType() {
        return AppDefs.KeyUnitJsonType.JSON_COMPOSITION.toString();
    }
}
