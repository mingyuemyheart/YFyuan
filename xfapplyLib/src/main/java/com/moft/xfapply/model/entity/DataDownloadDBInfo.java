package com.moft.xfapply.model.entity;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Property;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;

@Table(name = "data_download")
public class DataDownloadDBInfo implements java.io.Serializable {
    /**
     * @Fields serialVersionUID :
     */
    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    private int id;
    @Property(column = "thread_id")
    private int threadId;// 下载器id
    @Property(column = "start_pos")
    private long startPos;// 开始点
    @Property(column = "end_pos")
    private long endPos;// 结束点
    @Property(column = "compelete_size")
    private long compeleteSize;// 完成度
    private String url;// 下载器网络标识

    public DataDownloadDBInfo() {

    }
  
    public DataDownloadDBInfo(int threadId, long startPos, long endPos,
                              long compeleteSize, String url) {
        this.threadId = threadId;  
        this.startPos = startPos;  
        this.endPos = endPos;  
        this.compeleteSize = compeleteSize;  
        this.url = url;  
    }
  
    public String getUrl() {  
        return url;  
    }  
  
    public void setUrl(String url) {  
        this.url = url;  
    }  
  
    public int getThreadId() {  
        return threadId;  
    }  
  
    public void setThreadId(int threadId) {  
        this.threadId = threadId;  
    }

    public long getStartPos() {
        return startPos;
    }

    public void setStartPos(long startPos) {
        this.startPos = startPos;
    }

    public long getEndPos() {
        return endPos;
    }

    public void setEndPos(long endPos) {
        this.endPos = endPos;
    }

    public long getCompeleteSize() {
        return compeleteSize;
    }

    public void setCompeleteSize(long compeleteSize) {
        this.compeleteSize = compeleteSize;
    }

    @Override
    public String toString() {  
        return "DownloadInfo [threadId=" + threadId + ", startPos=" + startPos  
                + ", endPos=" + endPos + ", compeleteSize=" + compeleteSize  
                + "]";  
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }  
}  