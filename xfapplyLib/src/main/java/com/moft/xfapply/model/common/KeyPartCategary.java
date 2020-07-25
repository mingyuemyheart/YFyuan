package com.moft.xfapply.model.common;

import com.moft.xfapply.model.base.IKeyPartInfo;

import java.util.List;

/**
 * Created by Administrator on 2019/3/26 0026.
 */

public class KeyPartCategary {
    private String name;
    List<IKeyPartInfo> keyPartInfos;

    public KeyPartCategary(String name, List<IKeyPartInfo> keyPartInfos) {
        this.name = name;
        this.keyPartInfos = keyPartInfos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IKeyPartInfo> getKeyPartInfos() {
        return keyPartInfos;
    }

    public void setKeyPartInfos(List<IKeyPartInfo> keyPartInfos) {
        this.keyPartInfos = keyPartInfos;
    }
}
