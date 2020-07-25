package com.moft.xfapply.model.external.base;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/6/6 0006.
 */

public interface IMediaDTO extends Serializable {
    public String getUuid();
    public void setUuid(String uuid);
    public String getMediaUuid();
    public void setMediaUuid(String mediaUuid);
    public String getMediaName();
    public void setMediaName(String mediaName);
    public String getMediaFormat();
    public void setMediaFormat(String mediaFormat);
    public String getMediaUrl();
    public void setMediaUrl(String mediaUrl);
    public String getMediaDescription();
    public void setMediaDescription(String mediaDescription);
    public String getThumbnailUrl();
    public void setThumbnailUrl(String thumbnailUrl);
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
    public String getRemark();
    public void setRemark(String remark);
    public void setBelongToUuid(String belongToUuid);
    public String getBelongToUuid();
}
