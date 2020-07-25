/**  
 * @Title: VerifyAttachInfo.java
 * @Package com.yunfan.firefighting.fireplan.sqlite.model
 * @Description: 
 * @author cuihj
 * @date 2017年4月26日
 * @version V1.0  
 */
package com.moft.xfapply.model.entity;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Property;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;

/**
 * @ClassName: FileNodeDBInfo
 * @Description:
 * @author zhangshy
 * @date 2017年4月26日
 */
@Table(name = "file_node")
public class FileNodeDBInfo implements java.io.Serializable {

    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    @Property(column = "publish_url")
    private String publishUrl;
    @Property(column = "name")
    private String name;
    @Property(column = "directory")
    private Boolean directory;
    @Property(column = "format")
    private String format;
    @Property(column = "destination")
    private String destination;
    @Property(column = "relative_path")
    private String relativePath;

    @Property(column = "local_path")
    private String localPath;
    @Property(column = "local_thumb_path")
    private String localThumbPath;

    private String children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDirectory() {
        return directory;
    }

    public void setDirectory(Boolean directory) {
        this.directory = directory;
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

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }


}
