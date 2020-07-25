package com.moft.xfapply.model.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * @Author: 王泉
 * @Date:   2019-02-25
 * No.              Date.          Modifier    Description
 * 【T886049】      2019-02-25     王泉       终端与平台数据一致，实时同步（新建数据）
 */

public class IncOfflineTypeProcess {
    public enum ProcessStatus {
        IDLE,
        EXECUTING,
        FINISH
    }
    private ProcessStatus status;
    private String type;
    private Date updateDate;
    private List<String> exceptions;

    public IncOfflineTypeProcess() {
        this.status = ProcessStatus.IDLE;
        this.exceptions = new ArrayList<>();
    }

    public ProcessStatus getStatus() {
        return status;
    }

    public void setStatus(ProcessStatus status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public List<String> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<String> exceptions) {
        this.exceptions = exceptions;
    }
}
