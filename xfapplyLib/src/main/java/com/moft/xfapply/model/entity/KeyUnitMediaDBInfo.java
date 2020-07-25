package com.moft.xfapply.model.entity;

import android.graphics.Bitmap;

import com.moft.xfapply.model.base.IMediaInfo;
import com.moft.xfapply.model.external.base.IMediaDTO;
import com.moft.xfapply.model.external.dto.RestDTO;
import com.moft.xfapply.utils.BitmapUtil;
import com.moft.xfapply.utils.StorageUtil;
import com.moft.xfapply.utils.StringUtil;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Property;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;

import java.util.Date;

/**
 * key_unit_media
 * 
 * @author wangx
 * @version 1.0.0 2018-06-02
 */
@Table(name = "key_unit_media")
public class KeyUnitMediaDBInfo implements IMediaInfo {
    @Transient
    private static final long serialVersionUID = -1L;

    /** 唯一标识 */
    @Id
    private String uuid;

    @Property(column = "key_unit_uuid")
    private String keyUnitUuid;

    /** 所属对象类型 */
    @Property(column = "belongto_type")
    private String belongToType;

    /** 所属对象标识 */
    @Property(column = "belongto_uuid")
    private String belongToUuid;

    /** 文件分类
    数据字典：总体平面图、楼层平面图、附加图片 */
    private String classification;

    @Property(column = "media_uuid")
    private String mediaUuid;

    /** 文件名称 */
    @Property(column = "media_name")
    private String mediaName;

    /** 文件格式（后缀） */
    @Property(column = "media_format")
    private String mediaFormat;

    /** 外部访问路径 */
    @Property(column = "media_url")
    private String mediaUrl;

    /** 概要描述 */
    @Property(column = "media_description")
    private String mediaDescription;

    /** thumbnail访问路径 */
    @Property(column = "thumbnail_url")
    private String thumbnailUrl;

    @Property(column = "belongto_group")
    private String belongtoGroup;

    /** 是否删除 */
    private Boolean deleted;

    /** 数据版本 */
    private Integer version;

    /** 创建人 */
    @Property(column = "create_person")
    private String createPerson;

    /** 创建时间 */
    @Property(column = "create_date")
    private Date createDate;

    /** 更新人 */
    @Property(column = "update_person")
    private String updatePerson;

    /** 更新时间 */
    @Property(column = "update_date")
    private Date updateDate;

    /** 备注 */
    private String remark;

    @Property(column = "local_path")
    private String localPath;
    @Property(column = "local_thumb_path")
    private String localThumbPath;

    @Transient
    private int picWidth = 240;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getBelongToType() {
        return belongToType;
    }

    public void setBelongToType(String belongToType) {
        this.belongToType = belongToType;
    }

    public String getBelongToUuid() {
        return belongToUuid;
    }

    public void setBelongToUuid(String belongToUuid) {
        this.belongToUuid = belongToUuid;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getMediaUuid() {
        return mediaUuid;
    }

    public void setMediaUuid(String mediaUuid) {
        this.mediaUuid = mediaUuid;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public String getMediaFormat() {
        return mediaFormat;
    }

    public void setMediaFormat(String mediaFormat) {
        this.mediaFormat = mediaFormat;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    @Override
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @Override
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getKeyUnitUuid() {
        return keyUnitUuid;
    }

    public void setKeyUnitUuid(String keyUnitUuid) {
        this.keyUnitUuid = keyUnitUuid;
    }

    public String getMediaDescription() {
        return mediaDescription;
    }

    public void setMediaDescription(String mediaDescription) {
        this.mediaDescription = mediaDescription;
    }

    @Override
    public String getLocalPath() {
        return localPath;
    }

    @Override
    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    @Override
    public String getLocalThumbPath() {
        return localThumbPath;
    }

    @Override
    public void setLocalThumbPath(String localThumbPath) {
        this.localThumbPath = localThumbPath;
    }

    public String getBelongtoGroup() {
        return belongtoGroup;
    }

    public void setBelongtoGroup(String belongtoGroup) {
        this.belongtoGroup = belongtoGroup;
    }
    /*
    public Bitmap saveFullImage(Bitmap bitmap) {
        Bitmap thBM = null;
        try {
            String attachPath = StorageUtil.getAttachPath();
            String cachePath = StorageUtil.getCachePath();

            String path = mediaUrl;
            String fName = path.substring(path.lastIndexOf("/") + 1);

            localPath = attachPath + fName;
            localThumbPath = cachePath + "thumb_" + fName;

            BitmapUtil.saveBitmap(localPath, bitmap);

            thBM = BitmapUtil.resizeBitmap(localPath, picWidth, picWidth);
            thBM = BitmapUtil.centerSquareScaleBitmap(thBM,
                    ((thBM.getWidth() > thBM.getHeight()) ? thBM.getHeight() : thBM.getWidth()));

            BitmapUtil.saveBitmap(localThumbPath, thBM);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return thBM;
    }
    */
    public Bitmap saveThumbnailImage() {
        Bitmap thBM = null;

        try {
            String cachePath = StorageUtil.getCachePath();

            String path = localPath;
            String fName = path.substring(path.lastIndexOf("/") + 1);
            localThumbPath = cachePath + "_thumb_" + fName;

            Bitmap thBitmap = BitmapUtil.resizeBitmap(localPath, picWidth, picWidth);
            thBM = BitmapUtil.centerSquareScaleBitmap(thBitmap,
                    ((thBitmap.getWidth() > thBitmap.getHeight()) ? thBitmap.getHeight() : thBitmap.getWidth()));

            BitmapUtil.saveBitmap(localThumbPath, thBM);
            thBitmap.recycle();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return thBM;
    }
    //ID:886059 19-02-18 【数据】查看详细数据，有大量附件图片，会导致异常崩溃OOM。 王泉 开始
    public void saveFullImage(Bitmap bitmap) {
        try {
            String attachPath = StorageUtil.getAttachPath();

            String path = mediaUrl;
            String fName = path.substring(path.lastIndexOf("/") + 1);

            localPath = attachPath + fName;

            BitmapUtil.saveBitmap(localPath, bitmap);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap saveThumbnailImage(Bitmap bitmap) {
        Bitmap thBM = null;
        try {
            String cachePath = StorageUtil.getCachePath();

            String path = !StringUtil.isEmpty(thumbnailUrl) ? thumbnailUrl : mediaUrl;
            String fName = path.substring(path.lastIndexOf("/") + 1);

            localThumbPath = cachePath + "_thumb_" + fName;
            thBM = BitmapUtil.centerSquareScaleBitmap(bitmap,
                    ((bitmap.getWidth() > bitmap.getHeight()) ? bitmap.getHeight() : bitmap.getWidth()));
            BitmapUtil.saveBitmap(localThumbPath, thBM);
            bitmap.recycle();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return thBM;
    }
    //ID:886059 19-02-18 【数据】查看详细数据，有大量附件图片，会导致异常崩溃OOM。 王泉 结束
}