package com.moft.xfapply.model.entity;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Property;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "data_verify")
public class DataVerifyDBInfo implements Serializable {
    /**
     * @Fields serialVersionUID :
     */
    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    private String uuid; // 唯一标识
    @Property(column = "ele_uuid")
    private String eleUuid; //资源Uuid
    @Property(column = "snapshot_uuid")
    private String snapshotUuid; // 快照Uuid
    @Property(column = "ele_code")
    private String eleCode; // 编码
    @Property(column = "ele_name")
    private String eleName; // 名称
    @Property(column = "ele_type")
    private String eleType; // 分类
    private String address;
    @Property(column = "update_person")
    private String updatePerson; // 最新更新者
    @Property(column = "create_date")
    private Date createDate; // 创建时间
    @Property(column = "update_date")
    private Date updateDate; // 更新时间

    @Property(column = "belongto_group")
    private String belongtoGroup;//本地字段 城市
    @Property(column = "opera_type")
    private String operaType;//本地字段 操作类型
    private String status;//本地字段 状态
    @Property(column = "verify_note")
    private String verifyNote; // 审核意见
    private String remark; // 备注
    @Property(column = "submit_reason")
    private String submitReason; //提交备注

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEleUuid() {
        return eleUuid;
    }

    public void setEleUuid(String eleUuid) {
        this.eleUuid = eleUuid;
    }

    public String getSnapshotUuid() {
        return snapshotUuid;
    }

    public void setSnapshotUuid(String snapshotUuid) {
        this.snapshotUuid = snapshotUuid;
    }

    public String getEleCode() {
        return eleCode;
    }

    public void setEleCode(String eleCode) {
        this.eleCode = eleCode;
    }

    public String getEleName() {
        return eleName;
    }

    public void setEleName(String eleName) {
        this.eleName = eleName;
    }

    public String getEleType() {
        return eleType;
    }

    public void setEleType(String eleType) {
        this.eleType = eleType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getBelongtoGroup() {
        return belongtoGroup;
    }

    public void setBelongtoGroup(String belongtoGroup) {
        this.belongtoGroup = belongtoGroup;
    }

    public String getOperaType() {
        return operaType;
    }

    public void setOperaType(String operaType) {
        this.operaType = operaType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVerifyNote() {
        return verifyNote;
    }

    public void setVerifyNote(String verifyNote) {
        this.verifyNote = verifyNote;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSubmitReason() {
        return submitReason;
    }

    public void setSubmitReason(String submitReason) {
        this.submitReason = submitReason;
    }
}
