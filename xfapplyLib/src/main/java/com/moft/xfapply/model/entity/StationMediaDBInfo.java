/**
 * Copyright (C), 2012-2018, 大连云帆科技有限公司
 * FileName: CompAttachFileDTO
 * Author:   wangxu
 * Date:     2018/3/20 17:14
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.moft.xfapply.model.entity;

import android.graphics.Bitmap;

import com.moft.xfapply.model.base.IMediaInfo;
import com.moft.xfapply.utils.BitmapUtil;
import com.moft.xfapply.utils.StorageUtil;
import com.moft.xfapply.utils.StringUtil;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Property;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;

import java.util.Date;

/**
 * 附件(MaterialMedia)
 * 
 * @author wangxy
 * @version 1.0.0 2017-04-14
 */
@Table(name = "station_media")
public class StationMediaDBInfo implements IMediaInfo {
    @Transient
    private static final long serialVersionUID = -1L;

    /** 主键标识 */
    @Id
    private String uuid;

    /** 水源类型 */
    private String type;

    /** 根据type的类型，存储不同的uuid */
    @Property(column = "station_uuid")
    private String stationUuid;

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
    private int picWidth = 120;

    /**
     * 获取主键标识
     *
     * @return 主键标识
     */
    public String getUuid() {
        return this.uuid;
    }

    /**
     * 设置主键标识
     *
     * @param uuid
     *          主键标识
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 获取水源类型
     *
     * @return 水源类型
     */
    public String getType() {
        return this.type;
    }

    /**
     * 设置水源类型
     *
     * @param type
     *          水源类型
     */
    public void setType(String type) {
        this.type = type;
    }

    public String getStationUuid() {
        return stationUuid;
    }

    public void setStationUuid(String stationUuid) {
        this.stationUuid = stationUuid;
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

    public String getMediaDescription() {
        return mediaDescription;
    }

    public void setMediaDescription(String mediaDescription) {
        this.mediaDescription = mediaDescription;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    /**
     * 获取是否删除
     *
     * @return 是否删除
     */
    public Boolean getDeleted() {
        return this.deleted;
    }

    /**
     * 设置是否删除
     *
     * @param deleted
     *          是否删除
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取数据版本
     *
     * @return 数据版本
     */
    public Integer getVersion() {
        return this.version;
    }

    /**
     * 设置数据版本
     *
     * @param version
     *          数据版本
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 获取创建人
     *
     * @return 创建人
     */
    public String getCreatePerson() {
        return this.createPerson;
    }

    /**
     * 设置创建人
     *
     * @param createPerson
     *          创建人
     */
    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    public Date getCreateDate() {
        return this.createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate
     *          创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdatePerson() {
        return this.updatePerson;
    }

    public void setUpdatePerson(String updatePersion) {
        this.updatePerson = updatePersion;
    }

    /**
     * 获取更新时间
     *
     * @return 更新时间
     */
    public Date getUpdateDate() {
        return this.updateDate;
    }

    /**
     * 设置更新时间
     *
     * @param updateDate
     *          更新时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取备注
     *
     * @return 备注
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置备注
     *
     * @param remark
     *          备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getLocalThumbPath() {
        return localThumbPath;
    }

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

    @Override
    public String getBelongToUuid() {
        return stationUuid;
    }

    @Override
    public void setBelongToUuid(String belongToUuid) {
        stationUuid = belongToUuid;
    }
}