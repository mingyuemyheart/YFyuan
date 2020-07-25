/**  
 * @Title: VerifyAttachInfo.java
 * @Package com.yunfan.firefighting.fireplan.sqlite.model
 * @Description: 
 * @author cuihj
 * @date 2017年4月26日
 * @version V1.0  
 */
package com.moft.xfapply.model.external.dto;

import com.moft.xfapply.utils.StringUtil;

import java.util.List;

/**
 * @ClassName: FileNodeDBInfo
 * @Description:
 * @author zhangshy
 * @date 2017年4月26日
 */
public class FileNodeDTO extends RestDTO {
    private String name;
    private Boolean isDirectory;
    private String format;
    private String destination;
    private String relativePath;
    private String publishUrl;

    private List<FileNodeDTO> children;

    public String getName() {
        if (StringUtil.isEmpty(name)) {
            return "未知名称";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDirectory() {
        return isDirectory;
    }

    public void setDirectory(Boolean directory) {
        isDirectory = directory;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getPublishUrl() {
        return publishUrl;
    }

    public void setPublishUrl(String publishUrl) {
        this.publishUrl = publishUrl;
    }

    public List<FileNodeDTO> getChildren() {
        return children;
    }

    public void setChildren(List<FileNodeDTO> children) {
        this.children = children;
    }
}
