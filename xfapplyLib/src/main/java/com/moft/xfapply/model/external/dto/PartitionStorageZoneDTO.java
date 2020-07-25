package com.moft.xfapply.model.external.dto;

/**
 * partition_storage_zone
 * 
 * @author wangx
 * @version 1.0.0 2018-05-29
 */
public class PartitionStorageZoneDTO extends PartitionFunctionBaseDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -1348612020321860359L;


    /** 总容积（m³）
 */
    private Double totalVolume;

    /** 储罐数量
 */
    private Integer storageCount;

    /** 存储介质
 */
    private String storageMedia;

    /** 储罐间隔
 */
    private Double spacing;

    /** 技术人员
 */
    private String technicalPerson;

    /** 技术人员联系方式
 */
    private String technicalPersonContact;


    /**
     * 获取总容积（m³）

     * 
     * @return 总容积（m³）

     */
    public Double getTotalVolume() {
        return this.totalVolume;
    }

    /**
     * 设置总容积（m³）

     * 
     * @param totalVolume
     *          总容积（m³）

     */
    public void setTotalVolume(Double totalVolume) {
        this.totalVolume = totalVolume;
    }

    /**
     * 获取储罐数量

     * 
     * @return 储罐数量

     */
    public Integer getStorageCount() {
        return this.storageCount;
    }

    /**
     * 设置储罐数量

     * 
     * @param storageCount
     *          储罐数量

     */
    public void setStorageCount(Integer storageCount) {
        this.storageCount = storageCount;
    }

    /**
     * 获取存储介质

     * 
     * @return 存储介质

     */
    public String getStorageMedia() {
        return this.storageMedia;
    }

    /**
     * 设置存储介质

     * 
     * @param storageMedia
     *          存储介质

     */
    public void setStorageMedia(String storageMedia) {
        this.storageMedia = storageMedia;
    }

    /**
     * 获取储罐间隔

     * 
     * @return 储罐间隔

     */
    public Double getSpacing() {
        return this.spacing;
    }

    /**
     * 设置储罐间隔

     * 
     * @param spacing
     *          储罐间隔

     */
    public void setSpacing(Double spacing) {
        this.spacing = spacing;
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
}