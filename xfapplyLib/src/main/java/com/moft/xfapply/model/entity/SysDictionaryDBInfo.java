package com.moft.xfapply.model.entity;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Property;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;

import java.io.Serializable;

/**
 * 系统数据字典(sys_dictionary)
 *
 * @author zhangshy
 * @version 1.0.0 2018-05-21
 */
@Table(name = "sys_dictionary")
public class SysDictionaryDBInfo implements Serializable {
    /**
     * 版本号
     */
    @Transient
    private static final long serialVersionUID = -907016478043538066L;

    /**
     * 唯一标识
     */
    @Id
    private String  uuid;
    /**
     * 名称
     */
    private String  name;
    /**
     * 编码
     */
    private String  code;
    /**
     * 配置标识
     */
    @Property(column = "config_uuid")
    private String  configUuid;
    /**
     * 父uuid
     */
    @Property(column = "parent_uuid")
    private String  parentUuid;
    /**
     * 节点的深度
     */
    private Integer depth;
    /**
     * 权重
     */
    private Integer weight;
    /**
     * 备注
     */
    private String  remark;
    /**
     * 获取唯一标识
     *
     * @return 唯一标识
     */
    public String getUuid() {
        return this.uuid;
    }

    /**
     * 设置唯一标识
     *
     * @param uuid 唯一标识
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获取名称
     *
     * @return 名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取编码
     *
     * @return 编码
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 设置编码
     *
     * @param code 编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取配置标识
     *
     * @return 配置标识
     */
    public String getConfigUuid() {
        return this.configUuid;
    }

    /**
     * 设置配置标识
     *
     * @param configUuid 配置标识
     */
    public void setConfigUuid(String configUuid) {
        this.configUuid = configUuid;
    }

    /**
     * 获取父uuid
     *
     * @return 父uuid
     */
    public String getParentUuid() {
        return this.parentUuid;
    }

    /**
     * 设置父uuid
     *
     * @param parentUuid 父uuid
     */
    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    /**
     * 获取节点的深度
     *
     * @return 节点的深度
     */
    public Integer getDepth() {
        return this.depth;
    }

    /**
     * 设置节点的深度
     *
     * @param depth 节点的深度
     */
    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    /**
     * 获取权重
     *
     * @return 权重
     */
    public Integer getWeight() {
        return this.weight;
    }

    /**
     * 设置权重
     *
     * @param weight 权重
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * 获取备注
     *
     * @return 备注
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

}