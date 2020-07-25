package com.moft.xfapply.logic.download;

public class LoadInfo {
    public long fileSize;// 文件大小
    private long complete;// 完成度
    private String urlstring;// 下载器标识

    public LoadInfo(int fileSize, int complete, String urlstring) {
        this.fileSize = fileSize;
        this.complete = complete;
        this.urlstring = urlstring;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public long getComplete() {
        return complete;
    }

    public void setComplete(long complete) {
        this.complete = complete;
    }

    public void increaseComplete(int complete) {
        this.complete += complete;  
    }  
    
    public boolean isComplete() {
        return complete == fileSize;
    }
    
    public int getPercent() {
        return Math.round(complete * 100 / fileSize);
    }
  
    public String getUrlstring() {  
        return urlstring;  
    }  
  
    public void setUrlstring(String urlstring) {  
        this.urlstring = urlstring;  
    }  
  
    @Override  
    public String toString() {  
        return "LoadInfo [fileSize=" + fileSize + ", complete=" + complete  
                + ", urlstring=" + urlstring + "]";  
    }  
}
