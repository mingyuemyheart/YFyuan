package com.moft.xfapply.model.external.dto;

/**
 * 单体建筑表(partition_structure_mono)
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class PartitionStructureMonoDTO extends PartitionStructureDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 3129505594669449868L;


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