package com.moft.xfapply.model.external.base;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/30 0030.
 */

public interface ISourceDataSnapshotDTO extends Serializable {
    public String getUuid();
    public void setUuid(String uuid);
    public String getEntityUuid();
    public void setEntityUuid(String entityUuid);
    public String getMaintainTaskUuid();
    public void setMaintainTaskUuid(String maintainTaskUuid);
    public Boolean getEditable();
    public void setEditable(Boolean editable);
    public String getJsonData();
    public String getEntityData();
    public String getOperateType();
}
