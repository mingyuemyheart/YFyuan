package com.moft.xfapply.model.external.dto;

/**
 * partition_device
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class PartitionDeviceDTO extends PartitionFunctionBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -1683402538072439760L;


    /** 使用性质
 */
    private String usageType;

    /** 占地面积
 */
    private Double floorArea;

    /** 装置高度（m）
 */
    private Double height;

    /** 装置组成
 */
    private String compositionJson;

    /** 装置原料

 */
    private String materialJson;

    /** 装置产物
 */
    private String productJson;

    /** 技术人员
 */
    private String technicalPerson;

    /** 技术人员联系方式
 */
    private String technicalPersonContact;

    /** 工艺流程
 */
    private String processFlow;

    /**
     * 获取使用性质

     * 
     * @return 使用性质

     */
    public String getUsageType() {
        return this.usageType;
    }

    /**
     * 设置使用性质

     * 
     * @param usageType
     *          使用性质

     */
    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    /**
     * 获取占地面积

     * 
     * @return 占地面积

     */
    public Double getFloorArea() {
        return this.floorArea;
    }

    /**
     * 设置占地面积

     * 
     * @param floorArea
     *          占地面积

     */
    public void setFloorArea(Double floorArea) {
        this.floorArea = floorArea;
    }

    /**
     * 获取装置高度（m）

     * 
     * @return 装置高度（m）

     */
    public Double getHeight() {
        return this.height;
    }

    /**
     * 设置装置高度（m）

     * 
     * @param height
     *          装置高度（m）

     */
    public void setHeight(Double height) {
        this.height = height;
    }

    /**
     * 获取装置组成

     * 
     * @return 装置组成

     */
    public String getCompositionJson() {
        return this.compositionJson;
    }

    /**
     * 设置装置组成

     * 
     * @param compositionJson
     *          装置组成

     */
    public void setCompositionJson(String compositionJson) {
        this.compositionJson = compositionJson;
    }

    /**
     * 获取装置原料


     * 
     * @return 装置原料


     */
    public String getMaterialJson() {
        return this.materialJson;
    }

    /**
     * 设置装置原料


     * 
     * @param materialJson
     *          装置原料


     */
    public void setMaterialJson(String materialJson) {
        this.materialJson = materialJson;
    }

    /**
     * 获取装置产物

     * 
     * @return 装置产物

     */
    public String getProductJson() {
        return this.productJson;
    }

    /**
     * 设置装置产物

     * 
     * @param productJson
     *          装置产物

     */
    public void setProductJson(String productJson) {
        this.productJson = productJson;
    }

    /**
     * 获取技术人员

     * 
     * @return 技术人员

     */
    public String getTechnicalPerson() {
        return this.technicalPerson;
    }

    /**
     * 设置技术人员

     * 
     * @param technicalPerson
     *          技术人员

     */
    public void setTechnicalPerson(String technicalPerson) {
        this.technicalPerson = technicalPerson;
    }

    /**
     * 获取技术人员联系方式

     * 
     * @return 技术人员联系方式

     */
    public String getTechnicalPersonContact() {
        return this.technicalPersonContact;
    }

    /**
     * 设置技术人员联系方式

     * 
     * @param technicalPersonContact
     *          技术人员联系方式

     */
    public void setTechnicalPersonContact(String technicalPersonContact) {
        this.technicalPersonContact = technicalPersonContact;
    }

    /**
     * 获取工艺流程

     * 
     * @return 工艺流程

     */
    public String getProcessFlow() {
        return this.processFlow;
    }

    /**
     * 设置工艺流程

     * 
     * @param processFlow
     *          工艺流程

     */
    public void setProcessFlow(String processFlow) {
        this.processFlow = processFlow;
    }
}