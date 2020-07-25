package com.moft.xfapply.model.external.dto;

/**
 * 建筑群(partition_structure_group)
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class PartitionStructureGroupDTO extends PartitionStructureDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 5887715681945656801L;

    /** dummy */
    private String dummy;

    /**
     * 获取dummy
     * 
     * @return dummy
     */
    public String getDummy() {
        return this.dummy;
    }

    /**
     * 设置dummy
     * 
     * @param dummy
     */
    public void setDummy(String dummy) {
        this.dummy = dummy;
    }
}