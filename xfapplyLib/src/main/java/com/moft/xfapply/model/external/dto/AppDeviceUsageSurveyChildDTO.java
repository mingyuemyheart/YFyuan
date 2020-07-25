package com.moft.xfapply.model.external.dto;

/**
 * 终端日均使用(AppDeviceUsageSurvey)
 * 
 * @author zhao.chq
 * @version 1.0.0 2017-12-19
 */
public class AppDeviceUsageSurveyChildDTO extends RestDTO implements java.io.Serializable {
    /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 691675076063297411L;

    private String departmentCode;
    private String departmentName;

    private String brigadeCode;
    private String brigadeName;

    private String detachmentCode;
    private String detachmentName;

    private String battalionCode;
    private String battalionName;

    private String squadronCode;
    private String squadronName;

    private Float  cjOperateTime;
    private Float  yyOperateTime;
    private Integer  cjTotleCnt;
    private Integer  yyTotalCnt;
    private Integer  cjUseCnt;
    private Integer  yyUseCnt;
    private Integer  totalCnt;

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

    public String getBrigadeCode() {
        return brigadeCode;
    }

    public void setBrigadeCode(String brigadeCode) {
        this.brigadeCode = brigadeCode;
    }

    public String getBrigadeName() {
        return brigadeName;
    }

    public void setBrigadeName(String brigadeName) {
        this.brigadeName = brigadeName;
    }

    public String getDetachmentCode() {
        return detachmentCode;
    }

    public void setDetachmentCode(String detachmentCode) {
        this.detachmentCode = detachmentCode;
    }

    public String getDetachmentName() {
        return detachmentName;
    }

    public void setDetachmentName(String detachmentName) {
        this.detachmentName = detachmentName;
    }

    public String getBattalionCode() {
        return battalionCode;
    }

    public void setBattalionCode(String battalionCode) {
        this.battalionCode = battalionCode;
    }

    public String getBattalionName() {
        return battalionName;
    }

    public void setBattalionName(String battalionName) {
        this.battalionName = battalionName;
    }

    public String getSquadronCode() {
        return squadronCode;
    }

    public void setSquadronCode(String squadronCode) {
        this.squadronCode = squadronCode;
    }

    public String getSquadronName() {
        return squadronName;
    }

    public void setSquadronName(String squadronName) {
        this.squadronName = squadronName;
    }

    public Float getCjOperateTime() {
        return cjOperateTime;
    }

    public void setCjOperateTime(Float cjOperateTime) {
        this.cjOperateTime = cjOperateTime;
    }

    public Float getYyOperateTime() {
        return yyOperateTime;
    }

    public void setYyOperateTime(Float yyOperateTime) {
        this.yyOperateTime = yyOperateTime;
    }

    public Integer getCjTotleCnt() {
        return cjTotleCnt;
    }

    public void setCjTotleCnt(Integer cjTotleCnt) {
        this.cjTotleCnt = cjTotleCnt;
    }

    public Integer getYyTotalCnt() {
        return yyTotalCnt;
    }

    public void setYyTotalCnt(Integer yyTotalCnt) {
        this.yyTotalCnt = yyTotalCnt;
    }

    public Integer getCjUseCnt() {
        return cjUseCnt;
    }

    public void setCjUseCnt(Integer cjUseCnt) {
        this.cjUseCnt = cjUseCnt;
    }

    public Integer getYyUseCnt() {
        return yyUseCnt;
    }

    public void setYyUseCnt(Integer yyUseCnt) {
        this.yyUseCnt = yyUseCnt;
    }

    public Integer getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(Integer totalCnt) {
        this.totalCnt = totalCnt;
    }
}