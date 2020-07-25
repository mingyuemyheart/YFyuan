package com.moft.xfapply.model.external.dto;

/**
 * 行政区域(sys_district)
 * 
 * @author zhangshy
 * @version 1.0.0 2018-05-21
 */
public class SysDistrictDTO  extends RestDTO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 6570507154935499734L;

    /** 唯一标识 */
    private String uuid;

    /** 编码 */
    private String code;

    /** 名称 */
    private String name;

    /** 父code */
    private String parentCode;

    /** 等级 */
    private Integer grade;

    /** 城市 */
    private String city;

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
     *          唯一标识
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
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
     * 获取父code
     * 
     * @return 父code
     */
    public String getParentCode() {
        return this.parentCode;
    }

    /**
     * 设置父code
     * 
     * @param parentCode
     *          父code
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    /**
     * 获取等级
     * 
     * @return 等级
     */
    public Integer getGrade() {
        return this.grade;
    }

    /**
     * 设置等级
     * 
     * @param grade
     *          等级
     */
    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    /**
     * 获取城市
     * 
     * @return 城市
     */
    public String getCity() {
        return this.city;
    }

    /**
     * 设置城市
     * 
     * @param city
     *          城市
     */
    public void setCity(String city) {
        this.city = city;
    }
}