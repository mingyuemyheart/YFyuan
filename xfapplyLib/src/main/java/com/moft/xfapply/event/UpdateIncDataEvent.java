package com.moft.xfapply.event;

/**
 * Created by wangquan on 2016/11/12.
 */

public class UpdateIncDataEvent {
    private String belongtoGroup;

    public UpdateIncDataEvent(String belongtoGroup) {
        this.belongtoGroup = belongtoGroup;
    }

    public String getBelongtoGroup() {
        return belongtoGroup;
    }

    public void setBelongtoGroup(String belongtoGroup) {
        this.belongtoGroup = belongtoGroup;
    }
}
