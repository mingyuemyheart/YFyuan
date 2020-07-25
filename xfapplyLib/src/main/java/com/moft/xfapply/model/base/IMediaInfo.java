package com.moft.xfapply.model.base;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2018/6/6 0006.
 */

public interface IMediaInfo extends Serializable {
    public String getUuid();
    public void setUuid(String uuid);
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
    public String getLocalPath();
    public void setLocalPath(String localPath);
    public String getLocalThumbPath();
    public void setLocalThumbPath(String localThumbPath);
    public void saveFullImage(Bitmap bitmap);
    public Bitmap saveThumbnailImage(Bitmap bitmap);
    public Bitmap saveThumbnailImage();
    public void setBelongToUuid(String belongToUuid);
    public String getBelongToUuid();
    public String getBelongtoGroup();

    public interface OnAttachMediaUpdatedListener {
        void onUpdated(IMediaInfo info);
    }
}
