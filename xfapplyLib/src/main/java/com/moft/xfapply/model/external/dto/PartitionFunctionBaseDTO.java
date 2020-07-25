package com.moft.xfapply.model.external.dto;

import java.util.Date;

/**
 * partition_function_base
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class PartitionFunctionBaseDTO extends PartitionBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -4945205602248672853L;

    /** uuid */

    /** 建筑结构
 */
    private String structureType;

    public String getStructureType() {
        return structureType;
    }

    public void setStructureType(String structureType) {
        this.structureType = structureType;
    }
}