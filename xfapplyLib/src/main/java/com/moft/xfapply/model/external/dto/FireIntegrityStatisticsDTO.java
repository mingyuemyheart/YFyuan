package com.moft.xfapply.model.external.dto;

/**
 * (superivise_zttq)
 *
 * @author wangxy
 * @version 1.0.0 2017-04-14
 */
public class FireIntegrityStatisticsDTO extends RestDTO implements java.io.Serializable {

    /** 版本号 */
    private static final long serialVersionUID = -198776563479663973L;

    /** 组织编码 */
    private String departmentCode;
    /** 组织名称 */
    private String departmentName;

    /** 支队名称 */
    private String detachmentName;

    /** 大队名称 */
    private String battalionName;

    /** 中队名称 */
    private String squadronName;

    /** 支队code */
    private String detachmentCode;

    /** 大队code */
    private String battalionCode;

    /** 中队code */
    private String squadronCode;

    /** entityType */
    private String entityType;

    /** totalCnt */
    private Integer totalCnt;

    /** totalQuality */
    private Double totalQuality;

    /** 50% */
    private Integer firstZone;

    /** 50%-60% */
    private Integer secondZone;

    /** 60%-70% */
    private Integer thirdZone;

    /** 70%-80% */
    private Integer fourthZone;

    /** 80%-90% */
    private Integer fifthZone;

    /** 90%-100% */
    private Integer sixthZone;

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDetachmentName() {
        return detachmentName;
    }

    public void setDetachmentName(String detachmentName) {
        this.detachmentName = detachmentName;
    }

    public String getBattalionName() {
        return battalionName;
    }

    public void setBattalionName(String battalionName) {
        this.battalionName = battalionName;
    }

    public String getSquadronName() {
        return squadronName;
    }

    public void setSquadronName(String squadronName) {
        this.squadronName = squadronName;
    }

    public String getDetachmentCode() {
        return detachmentCode;
    }

    public void setDetachmentCode(String detachmentCode) {
        this.detachmentCode = detachmentCode;
    }

    public String getBattalionCode() {
        return battalionCode;
    }

    public void setBattalionCode(String battalionCode) {
        this.battalionCode = battalionCode;
    }

    public String getSquadronCode() {
        return squadronCode;
    }

    public void setSquadronCode(String squadronCode) {
        this.squadronCode = squadronCode;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Integer getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(Integer totalCnt) {
        this.totalCnt = totalCnt;
    }

    public Double getTotalQuality() {
        return totalQuality;
    }

    public void setTotalQuality(Double totalQuality) {
        this.totalQuality = totalQuality;
    }

    public Integer getFirstZone() {
        return firstZone;
    }

    public void setFirstZone(Integer firstZone) {
        this.firstZone = firstZone;
    }

    public Integer getSecondZone() {
        return secondZone;
    }

    public void setSecondZone(Integer secondZone) {
        this.secondZone = secondZone;
    }

    public Integer getThirdZone() {
        return thirdZone;
    }

    public void setThirdZone(Integer thirdZone) {
        this.thirdZone = thirdZone;
    }

    public Integer getFourthZone() {
        return fourthZone;
    }

    public void setFourthZone(Integer fourthZone) {
        this.fourthZone = fourthZone;
    }

    public Integer getFifthZone() {
        return fifthZone;
    }

    public void setFifthZone(Integer fifthZone) {
        this.fifthZone = fifthZone;
    }

    public Integer getSixthZone() {
        return sixthZone;
    }

    public void setSixthZone(Integer sixthZone) {
        this.sixthZone = sixthZone;
    }
}