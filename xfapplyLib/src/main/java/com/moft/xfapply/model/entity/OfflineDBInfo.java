package com.moft.xfapply.model.entity;

import java.util.Date;
import java.util.UUID;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Property;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;

@Table(name = "dt_offline_db")
public class OfflineDBInfo implements java.io.Serializable {
    /**
     * @Fields serialVersionUID :
     */
    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    private int id;// 主键
    @Property(column = "belongto_group")
    private String  belongtoGroup; //城市
    private String name;// 名称
    @Property(column = "data_type")
    private String dataType; // 数据类型
    private String url;
    private int percent;
    @Property(column = "version_no")
    private Integer versionNo;
    private String status;
    @Property(column = "update_date")
    private Date updateDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getBelongtoGroup() {
        return belongtoGroup;
    }

    public void setBelongtoGroup(String belongtoGroup) {
        this.belongtoGroup = belongtoGroup;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getPercent() {
        return percent;
    }
    public void setPercent(int percent) {
        this.percent = percent;
    }

    public Integer getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Integer versionNo) {
        this.versionNo = versionNo;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
