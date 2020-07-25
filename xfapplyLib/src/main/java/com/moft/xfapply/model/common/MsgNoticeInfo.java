package com.moft.xfapply.model.common;

import java.util.Date;


public class MsgNoticeInfo implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String  uuid;
    private String  noticeType;
    private String noticeEntityType;
    private String  noticeEntityUuid;
    private String title;
    private String  content;
    private Boolean isRead;
    private String  sender;
    private String  senderCode;
    private Date    createDate;
    private String  remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @Title: getUuid
     * @return uuid
     */
    public String getUuid() {
        return uuid;
    }
    
    /**
     * @Title: setUuid
     * @param uuid
     * @Description: the uuid to set
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    /**
     * @Title: getNoticeType
     * @return noticeType
     */
    public String getNoticeType() {
        return noticeType;
    }
    
    /**
     * @Title: setNoticeType
     * @param noticeType
     * @Description: the noticeType to set
     */
    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getNoticeEntityType() {
        return noticeEntityType;
    }

    public void setNoticeEntityType(String noticeEntityType) {
        this.noticeEntityType = noticeEntityType;
    }

    /**
     * @Title: getNoticeEntityUuid
     * @return noticeEntityUuid
     */
    public String getNoticeEntityUuid() {
        return noticeEntityUuid;
    }
    
    /**
     * @Title: setNoticeEntityUuid
     * @param noticeEntityUuid
     * @Description: the noticeEntityUuid to set
     */
    public void setNoticeEntityUuid(String noticeEntityUuid) {
        this.noticeEntityUuid = noticeEntityUuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @Title: getContent
     * @return content
     */
    public String getContent() {
        return content;
    }
    
    /**
     * @Title: setContent
     * @param content
     * @Description: the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean read) {
        isRead = read;
    }
    /**
     * @Title: getSender
     * @return sender
     */
    public String getSender() {
        return sender;
    }
    
    /**
     * @Title: setSender
     * @param sender
     * @Description: the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }
    
    /**
     * @Title: getSenderCode
     * @return senderCode
     */
    public String getSenderCode() {
        return senderCode;
    }
    
    /**
     * @Title: setSenderCode
     * @param senderCode
     * @Description: the senderCode to set
     */
    public void setSenderCode(String senderCode) {
        this.senderCode = senderCode;
    }
    
    /**
     * @Title: getCreateDate
     * @return createDate
     */
    public Date getCreateDate() {
        return createDate;
    }
    
    /**
     * @Title: setCreateDate
     * @param createDate
     * @Description: the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    /**
     * @Title: getRemark
     * @return remark
     */
    public String getRemark() {
        return remark;
    }
    
    /**
     * @Title: setRemark
     * @param remark
     * @Description: the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
}
