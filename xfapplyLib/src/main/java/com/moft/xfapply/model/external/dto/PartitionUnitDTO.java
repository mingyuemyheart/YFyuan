package com.moft.xfapply.model.external.dto;

/**
 * partition_unit
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class PartitionUnitDTO extends PartitionBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -3564277108753903649L;

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