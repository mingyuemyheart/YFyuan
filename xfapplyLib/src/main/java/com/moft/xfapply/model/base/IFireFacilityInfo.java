package com.moft.xfapply.model.base;

import com.moft.xfapply.model.common.PropertyDes;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/5/23 0023.
 */

public interface IFireFacilityInfo extends Serializable {
    public String getUuid();
    public void setUuid(String uuid);
    public String getDepartmentUuid();
    public void setDepartmentUuid(String departmentUuid);
    public String getName();
    public void setName(String name);
    public String getPartitionUuid();
    public void setPartitionUuid(String partitionUuid);
    public String getFacilityType();
    public void setFacilityType(String facilityType);
    public String getGeometryText();
    public void setGeometryText(String geometryText);
    public String getLongitude();
    public void setLongitude(String longitude);
    public String getLatitude();
    public void setLatitude(String latitude);
    public Integer getCount();
    public String getLocation();
    public void setLocation(String location);
    public void setCount(Integer count);
    public String getDataJson();
    public void setDataJson(String dataJson);
    public Integer getVersion();
    public void setVersion(Integer version);
    public Boolean getDeleted();
    public void setDeleted(Boolean deleted);
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
    public List<PropertyDes> getPdListDetail();
    public void setAdapter(IGeomElementInfo elementInfo);
    public String getAddress();
    public void setAddress(String address);
    public String getPlanDiagram();
    public void setPlanDiagram(String planDiagram);
}
