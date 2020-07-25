package com.moft.xfapply.model.common;

import com.moft.xfapply.app.LvApplication;
import com.moft.xfapply.utils.PreferenceUtils;

public class LayerSelect {
    private int resId;
    private String name;
    private String key;
    private boolean isSelected = false;

    public LayerSelect() {

    }

    public LayerSelect(int r, String n, String k) {
        resId = r;
        name = n;
        key = k;
        isSelected = PreferenceUtils.getPrefBoolean(
                LvApplication.getInstance().getApplicationContext(), key, true);
    }

    public LayerSelect(int r, String n, boolean i) {
        resId = r;
        name = n;
        isSelected = i;
    }

    public String getKey() {
        return key;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        PreferenceUtils.setPrefBoolean(
                LvApplication.getInstance().getApplicationContext(), key, selected);
    }
}
