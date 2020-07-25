package com.moft.xfapply.model.base;

import com.moft.xfapply.model.common.PrimaryAttribute;
import com.moft.xfapply.model.common.PropertyDes;

import java.util.Date;
import java.util.List;

/**
 * Created by wangquan on 2017/4/23.
 */

public interface IGeomElementInfo extends java.io.Serializable {
    public List<PropertyDes> getPdListDetail();
    public void setUuid(String uuid);
    public String getUuid();
    public String getCode();
    public void setCode(String code);
    public String getDepartmentUuid();
    public void setDepartmentUuid(String departmentUuid);
    public String getName();
    public void setName(String name);
    public String getEleType();
    public void setEleType(String eleType);
    public String getAddress();
    public void setAddress(String address);
    public String getGeometryText();
    public void setGeometryText(String geometryText);
    public String getLongitude();
    public void setLongitude(String longitude);
    public String getLatitude();
    public void setLatitude(String latitude);
    public Boolean getDeleted();
    public void setDeleted(Boolean deleted);
    public Integer getVersion();
    public void setVersion(Integer version);
    public String getCreatePerson();
    public void setCreatePerson(String createPerson);
    public String getUpdatePerson();
    public void setUpdatePerson(String updatePerson);
    public Date getCreateDate();
    public void setCreateDate(Date createDate);
    public Date getUpdateDate();
    public void setUpdateDate(Date updateDate);
    public String getRemark();
    public void setRemark(String remark);
    public String getBelongtoGroup();
    public void setBelongtoGroup(String belongtoGroup);
    public Double getDataQuality();
    public void setDataQuality(Double dataQuality);
    public void setAdapter();
    public String toString();
    public PrimaryAttribute getPrimaryValues();
    public String getOutline();
    public boolean isGeoPosValid();
    public String getSubType();
    public void setSubType(String subType);
    public String getResEleType();
}
