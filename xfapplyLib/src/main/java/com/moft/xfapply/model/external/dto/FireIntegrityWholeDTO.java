package com.moft.xfapply.model.external.dto;

import java.util.List;

/**
 * (superivise_zttq)
 *
 * @author wangxy
 * @version 1.0.0 2017-04-14
 */
public class FireIntegrityWholeDTO extends RestDTO implements java.io.Serializable {

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

    private List<FireIntegrityStatisticsDTO> fireIntegrityStatisticsDTOS;

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

    public List<FireIntegrityStatisticsDTO> getFireIntegrityStatisticsDTOS() {
        return fireIntegrityStatisticsDTOS;
    }

    public void setFireIntegrityStatisticsDTOS(List<FireIntegrityStatisticsDTO> fireIntegrityStatisticsDTOS) {
        this.fireIntegrityStatisticsDTOS = fireIntegrityStatisticsDTOS;
    }
}