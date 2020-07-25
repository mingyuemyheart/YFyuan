package com.moft.xfapply.model.common;

import java.util.ArrayList;
import java.util.List;

/*
 * @Author: 王泉
 * @Date:   2019-02-25
 * No.              Date.          Modifier    Description
 * 【T886049】      2019-02-25     王泉       终端与平台数据一致，实时同步（新建数据）
 */

public class IncOfflineProcess {
    public enum ProcessStatus {
        IDLE,
        EXECUTING,
        FINISH,
        EXCEPTION
    }
    private ProcessStatus status;
    private List<IncOfflineTypeProcess> typeProcesses;
    private String message;

    public IncOfflineProcess() {
        this.status = ProcessStatus.IDLE;
        this.typeProcesses = new ArrayList<>();
    }

    public ProcessStatus getStatus() {
        return status;
    }

    public void setStatus(ProcessStatus status) {
        this.status = status;
    }

    public List<IncOfflineTypeProcess> getTypeProcesses() {
        return typeProcesses;
    }

    public void setTypeProcesses(List<IncOfflineTypeProcess> typeProcesses) {
        this.typeProcesses = typeProcesses;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
