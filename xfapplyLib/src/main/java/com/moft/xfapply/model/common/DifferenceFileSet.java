package com.moft.xfapply.model.common;

import java.io.File;
import java.util.List;

/**
 * Created by wangquan on 2017/4/25.
 */

public class DifferenceFileSet {
    private List<String> fileNameList;
    private List<String> deleteFileList;	// Attach Record uuid
    private File[] files;
    private String[] fileKeys;

    public List<String> getFileNameList() {
        return fileNameList;
    }

    public void setFileNameList(List<String> fileNameList) {
        this.fileNameList = fileNameList;
    }

    public List<String> getDeleteFileList() {
        return deleteFileList;
    }

    public void setDeleteFileList(List<String> deleteFileList) {
        this.deleteFileList = deleteFileList;
    }

    public File[] getFiles() {
        return files;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }

    public String[] getFileKeys() {
        return fileKeys;
    }

    public void setFileKeys(String[] fileKeys) {
        this.fileKeys = fileKeys;
    }
}
