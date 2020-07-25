package com.moft.xfapply.model.external.dto;

/**
 * partition_floor
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class PartitionFloorDTO extends PartitionBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -3324782197376672527L;


    /** 所属建筑标识
 */
    private String stuctureUuid;

    /** 楼层分类
 */
    private String floorType;

    /** 功能描述
 */
    private String functionDescription;

    /** 具体楼层描述
 */
    private String floors;

    /** 层数
 */
    private Integer floorCount;


    /**
     * 获取所属建筑标识

     * 
     * @return 所属建筑标识

     */
    public String getStuctureUuid() {
        return this.stuctureUuid;
    }

    /**
     * 设置所属建筑标识

     * 
     * @param stuctureUuid
     *          所属建筑标识

     */
    public void setStuctureUuid(String stuctureUuid) {
        this.stuctureUuid = stuctureUuid;
    }

    /**
     * 获取楼层分类

     * 
     * @return 楼层分类

     */
    public String getFloorType() {
        return this.floorType;
    }

    /**
     * 设置楼层分类

     * 
     * @param floorType
     *          楼层分类

     */
    public void setFloorType(String floorType) {
        this.floorType = floorType;
    }

    /**
     * 获取功能描述

     * 
     * @return 功能描述

     */
    public String getFunctionDescription() {
        return this.functionDescription;
    }

    /**
     * 设置功能描述

     * 
     * @param functionDescription
     *          功能描述

     */
    public void setFunctionDescription(String functionDescription) {
        this.functionDescription = functionDescription;
    }

    /**
     * 获取具体楼层描述

     * 
     * @return 具体楼层描述

     */
    public String getFloors() {
        return this.floors;
    }

    /**
     * 设置具体楼层描述

     * 
     * @param floors
     *          具体楼层描述

     */
    public void setFloors(String floors) {
        this.floors = floors;
    }

    /**
     * 获取层数

     * 
     * @return 层数

     */
    public Integer getFloorCount() {
        return this.floorCount;
    }

    /**
     * 设置层数

     * 
     * @param floorCount
     *          层数

     */
    public void setFloorCount(Integer floorCount) {
        this.floorCount = floorCount;
    }
}