package com.moft.xfapply.model.entity;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Property;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;

/**
 * key_unit_region_partition
 * 
 * @author wangx
 * @version 1.0.0 2018-05-22
 */
@Table(name = "key_unit_region_partition")
public class KeyUnitRegionPartitionDBInfo implements java.io.Serializable {
    /** 版本号 */
    @Transient
    private static final long serialVersionUID = -2349663316487142892L;

    @Id
    private String uuid; // 唯一标识
    /** 重点单位标识
 */
    @Property(column = "key_unit_region_uuid")
    private String keyUnitRegionUuid;
    /** 分区标识
 */
    @Property(column = "partition_uuid")
    private String partitionUuid;
    /** belongtoGroup */
    @Property(column = "belongto_group")
    private String belongtoGroup;

    /**
     * 获取重点单位标识

     * 
     * @return 重点单位标识

     */
    public String getKeyUnitRegionUuid() {
        return this.keyUnitRegionUuid;
    }

    /**
     * 设置重点单位标识

     * 
     * @param keyUnitRegionUuid
     *          重点单位标识

     */
    public void setKeyUnitRegionUuid(String keyUnitRegionUuid) {
        this.keyUnitRegionUuid = keyUnitRegionUuid;
    }

    /**
     * 获取分区标识

     * 
     * @return 分区标识

     */
    public String getPartitionUuid() {
        return this.partitionUuid;
    }

    /**
     * 设置分区标识

     * 
     * @param partitionUuid
     *          分区标识

     */
    public void setPartitionUuid(String partitionUuid) {
        this.partitionUuid = partitionUuid;
    }

    /**
     * 获取belongtoGroup
     * 
     * @return belongtoGroup
     */
    public String getBelongtoGroup() {
        return this.belongtoGroup;
    }

    /**
     * 设置belongtoGroup
     * 
     * @param belongtoGroup
     */
    public void setBelongtoGroup(String belongtoGroup) {
        this.belongtoGroup = belongtoGroup;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}