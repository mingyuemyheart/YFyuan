package com.moft.xfapply.model.external.dto;

/**
 * partition_storage
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class PartitionStorageDTO extends PartitionBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -9192474081761950877L;

    /** 储罐类型
 */
    private String type;

    /** 容量（m³）
 */
    private Double capacity;

    /** 直径（m）
 */
    private Double diameter;

    /** 高度（m）
 */
    private Double height;

    /** 周长（m）
 */
    private Double perimeter;

    /** 罐顶面积（㎡）
 */
    private Double topArea;

    /** 工作压力
 */
    private String pressure;

    /** 存储温度
 */
    private String temperature;

    /** 存储介质
 */
    private String storageMediaJson;


    /**
     * 获取储罐类型

     * 
     * @return 储罐类型

     */
    public String getType() {
        return this.type;
    }

    /**
     * 设置储罐类型

     * 
     * @param type
     *          储罐类型

     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取容量（m³）

     * 
     * @return 容量（m³）

     */
    public Double getCapacity() {
        return this.capacity;
    }

    /**
     * 设置容量（m³）

     * 
     * @param capacity
     *          容量（m³）

     */
    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    /**
     * 获取直径（m）

     * 
     * @return 直径（m）

     */
    public Double getDiameter() {
        return this.diameter;
    }

    /**
     * 设置直径（m）

     * 
     * @param diameter
     *          直径（m）

     */
    public void setDiameter(Double diameter) {
        this.diameter = diameter;
    }

    /**
     * 获取高度（m）

     * 
     * @return 高度（m）

     */
    public Double getHeight() {
        return this.height;
    }

    /**
     * 设置高度（m）

     * 
     * @param height
     *          高度（m）

     */
    public void setHeight(Double height) {
        this.height = height;
    }

    /**
     * 获取周长（m）

     * 
     * @return 周长（m）

     */
    public Double getPerimeter() {
        return this.perimeter;
    }

    /**
     * 设置周长（m）

     * 
     * @param perimeter
     *          周长（m）

     */
    public void setPerimeter(Double perimeter) {
        this.perimeter = perimeter;
    }

    /**
     * 获取罐顶面积（㎡）

     * 
     * @return 罐顶面积（㎡）

     */
    public Double getTopArea() {
        return this.topArea;
    }

    /**
     * 设置罐顶面积（㎡）

     * 
     * @param topArea
     *          罐顶面积（㎡）

     */
    public void setTopArea(Double topArea) {
        this.topArea = topArea;
    }

    /**
     * 获取工作压力

     * 
     * @return 工作压力

     */
    public String getPressure() {
        return this.pressure;
    }

    /**
     * 设置工作压力

     * 
     * @param pressure
     *          工作压力

     */
    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    /**
     * 获取存储温度

     * 
     * @return 存储温度

     */
    public String getTemperature() {
        return this.temperature;
    }

    /**
     * 设置存储温度

     * 
     * @param temperature
     *          存储温度

     */
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    /**
     * 获取存储介质

     * 
     * @return 存储介质

     */
    public String getStorageMediaJson() {
        return this.storageMediaJson;
    }

    /**
     * 设置存储介质

     * 
     * @param storageMediaJson
     *          存储介质

     */
    public void setStorageMediaJson(String storageMediaJson) {
        this.storageMediaJson = storageMediaJson;
    }
}