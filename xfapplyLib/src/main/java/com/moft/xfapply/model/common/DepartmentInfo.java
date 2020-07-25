package com.moft.xfapply.model.common;

/**
 * Created by Administrator on 2019/4/22 0022.
 */

public class DepartmentInfo {
    private String uuid;
    private String parentUuid;
    private Integer grade;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getParentUuid() {
        return parentUuid;
    }

    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
