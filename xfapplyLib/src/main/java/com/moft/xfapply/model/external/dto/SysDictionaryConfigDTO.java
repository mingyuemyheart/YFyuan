package com.moft.xfapply.model.external.dto;

/**
 * 数据字典配置(sys_dictionary_config)
 * 
 * @author zhangshy
 * @version 1.0.0 2018-05-21
 */
public class SysDictionaryConfigDTO  extends RestDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 4539749284349935472L;

    /** 唯一标识 */
    private String uuid;

    /** 名称 */
    private String name;

    /** 编码 */
    private String code;

    /** 权重 */
    private Integer weight;

    /** 备注 */
    private String remark;

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
     * @param uuid
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
     * @param name
     *          名称
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
     * @param code
     *          编码
     */
    public void setCode(String code) {
        this.code = code;
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
     * @param weight
     *          权重
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
     * @param remark
     *          备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}