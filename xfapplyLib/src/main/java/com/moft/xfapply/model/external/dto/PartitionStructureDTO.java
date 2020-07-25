package com.moft.xfapply.model.external.dto;

/**
 * partition_structure
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class PartitionStructureDTO extends PartitionFunctionBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1471978906336917630L;


    /** 使用性质
 */
    private String usageType;

    /** 占地面积
 */
    private Double floorArea;

    /** 建筑面积
 */
    private Double structureArea;

    /** 地上高度（m）
 */
    private Double heightOverground;

    /** 地下高度（m）
 */
    private Double heightUnderground;

    /** 地上层数
 */
    private Integer floorOverground;

    /** 地下层数
 */
    private Integer floorUnderground;

    /** 功能描述
 */
    private String functionDescription;

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    public Double getFloorArea() {
        return floorArea;
    }

    public void setFloorArea(Double floorArea) {
        this.floorArea = floorArea;
    }

    public Double getStructureArea() {
        return structureArea;
    }

    public void setStructureArea(Double structureArea) {
        this.structureArea = structureArea;
    }

    public Double getHeightOverground() {
        return heightOverground;
    }

    public void setHeightOverground(Double heightOverground) {
        this.heightOverground = heightOverground;
    }

    public Double getHeightUnderground() {
        return heightUnderground;
    }

    public void setHeightUnderground(Double heightUnderground) {
        this.heightUnderground = heightUnderground;
    }

    public Integer getFloorOverground() {
        return floorOverground;
    }

    public void setFloorOverground(Integer floorOverground) {
        this.floorOverground = floorOverground;
    }

    public Integer getFloorUnderground() {
        return floorUnderground;
    }

    public void setFloorUnderground(Integer floorUnderground) {
        this.floorUnderground = floorUnderground;
    }

    public String getFunctionDescription() {
        return functionDescription;
    }

    public void setFunctionDescription(String functionDescription) {
        this.functionDescription = functionDescription;
    }
}