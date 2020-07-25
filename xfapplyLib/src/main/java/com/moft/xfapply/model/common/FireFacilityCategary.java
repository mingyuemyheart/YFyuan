package com.moft.xfapply.model.common;

import com.moft.xfapply.model.base.IFireFacilityInfo;

import java.util.List;

/**
 * Created by Administrator on 2019/3/26 0026.
 */

public class FireFacilityCategary {
    private String name;
    private List<IFireFacilityInfo> fireFacilityInfos;

    public FireFacilityCategary(String name, List<IFireFacilityInfo> fireFacilityInfos) {
        this.name = name;
        this.fireFacilityInfos = fireFacilityInfos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IFireFacilityInfo> getFireFacilityInfos() {
        return fireFacilityInfos;
    }

    public void setFireFacilityInfos(List<IFireFacilityInfo> fireFacilityInfos) {
        this.fireFacilityInfos = fireFacilityInfos;
    }
}
