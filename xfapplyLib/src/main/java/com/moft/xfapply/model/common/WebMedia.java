package com.moft.xfapply.model.common;

import android.graphics.Bitmap;

import com.moft.xfapply.model.base.IMediaInfo;

import java.util.Date;

/**
 * Created by Administrator on 2018/6/7 0007.
 */

public class WebMedia implements IMediaInfo {

    private String uuid;
    private String mediaName;
    private String mediaUrl;
    private String mediaFormat;
    private String mediaDescription;

    @Override
    public String getMediaDescription() {
        return mediaDescription;
    }

    @Override
    public void setMediaDescription(String mediaDescription) {
        this.mediaDescription = mediaDescription;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getMediaName() {
        return mediaName;
    }

    @Override
    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    @Override
    public String getMediaUrl() {
        return mediaUrl;
    }

    @Override
    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    @Override
    public String getMediaFormat() {
        return mediaFormat;
    }

    @Override
    public void setMediaFormat(String mediaFormat) {
        this.mediaFormat = mediaFormat;
    }

    @Override
    public String getThumbnailUrl() {
        return null;
    }

    @Override
    public void setThumbnailUrl(String thumbnailUrl) {

    }

    @Override
    public Boolean getDeleted() {
        return null;
    }

    @Override
    public void setDeleted(Boolean deleted) {

    }

    @Override
    public Integer getVersion() {
        return null;
    }

    @Override
    public void setVersion(Integer version) {

    }

    @Override
    public String getCreatePerson() {
        return null;
    }

    @Override
    public void setCreatePerson(String createPerson) {

    }

    @Override
    public Date getCreateDate() {
        return null;
    }

    @Override
    public void setCreateDate(Date createDate) {

    }

    @Override
    public String getUpdatePerson() {
        return null;
    }

    @Override
    public void setUpdatePerson(String updatePerson) {

    }

    @Override
    public Date getUpdateDate() {
        return null;
    }

    @Override
    public void setUpdateDate(Date updateDate) {

    }

    @Override
    public String getRemark() {
        return null;
    }

    @Override
    public void setRemark(String remark) {

    }

    @Override
    public String getLocalPath() {
        return null;
    }

    @Override
    public void setLocalPath(String localPath) {

    }

    @Override
    public String getLocalThumbPath() {
        return null;
    }

    @Override
    public void setLocalThumbPath(String localThumbPath) {

    }

    @Override
    public void saveFullImage(Bitmap bitmap) {

    }

    @Override
    public Bitmap saveThumbnailImage(Bitmap bitmap) {
        return null;
    }

    @Override
    public Bitmap saveThumbnailImage() {
        return null;
    }

    @Override
    public void setBelongToUuid(String belongToUuid) {

    }

    @Override
    public String getBelongToUuid() {
        return null;
    }

    @Override
    public String getBelongtoGroup() {
        return null;
    }
}
