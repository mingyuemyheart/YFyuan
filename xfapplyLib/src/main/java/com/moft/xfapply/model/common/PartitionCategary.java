package com.moft.xfapply.model.common;

import com.moft.xfapply.model.base.IPartitionInfo;

import java.util.List;

/**
 * Created by Administrator on 2019/3/26 0026.
 */

public class PartitionCategary {
    private String name;
    private String address;
    List<IPartitionInfo> partitionInfos;

    public PartitionCategary(String name, String address, List<IPartitionInfo> partitionInfos) {
        this.name = name;
        this.address = address;
        this.partitionInfos = partitionInfos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IPartitionInfo> getPartitionInfos() {
        return partitionInfos;
    }

    public void setPartitionInfos(List<IPartitionInfo> partitionInfos) {
        this.partitionInfos = partitionInfos;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
