package com.moft.xfapply.model.base;

import com.moft.xfapply.model.common.PropertyDes;
import com.moft.xfapply.model.entity.KeyUnitDBInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/5/23 0023.
 */

public interface IPartitionInfo extends Serializable {
    public String getUuid();
    public void setUuid(String uuid);
    public String getName();
    public void setName(String name);
    public String getPartitionType();
    public void setPartitionType(String partitionType);
    public String getBelongToPartitionUuid();
    public void setBelongToPartitionUuid(String belongToPartitionUuid);
    public String getDepartmentUuid();
    public void setDepartmentUuid(String departmentUuid);
    public String getAddress();
    public void setAddress(String address);
    public String getGeometryText();
    public void setGeometryText(String geometryText);
    public String getAdjacentEast();
    public void setAdjacentEast(String adjacentEast);
    public String getAdjacentWest();
    public void setAdjacentWest(String adjacentWest);
    public String getAdjacentSouth();
    public void setAdjacentSouth(String adjacentSouth);
    public String getAdjacentNorth();
    public void setAdjacentNorth(String adjacentNorth);
    public String getRouteFromStation();
    public void setRouteFromStation(String routeFromStation);
    public Integer getTimeStationArrive();
    public void setTimeStationArrive(Integer timeStationArrive);
    public Boolean getDeleted();
    public void setDeleted(Boolean deleted);
    public Integer getVersion();
    public void setVersion(Integer version);
    public String getCreatePerson();
    public void setCreatePerson(String createPerson);
    public Date getCreateDate();
    public void setCreateDate(Date createDate);
    public String getUpdatePerson();
    public void setUpdatePerson(String updatePerson);
    public Date getUpdateDate();
    public void setUpdateDate(Date updateDate);
    public String getLongitude();
    public void setLongitude(String longitude);
    public String getLatitude();
    public void setLatitude(String latitude);
    public String getRemark();
    public void setRemark(String remark);
    public String getBelongtoGroup();
    public void setBelongtoGroup(String belongtoGroup);
    public List<PropertyDes> getPdListDetail();
    public PropertyDes getNamePropertyDes();
    public void setAdapter(IGeomElementInfo elementInfo);
}
