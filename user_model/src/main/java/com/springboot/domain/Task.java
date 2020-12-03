package com.springboot.domain;

import java.time.LocalDateTime;

public class Task extends BaseDomain {

    private static final long serialVersionUID = -6444412657849554771L;
    /**
     * 任务编号
     */
    private String taskNumber;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 发起时间
     */
    private LocalDateTime startTime;
    /**
     * 发起单位
     */
    private String startUnit;
    /**
     * 任务来源
     */
    private String taskSource;
    /**
     * 到期时间
     */
    private LocalDateTime dueTime;

    public String getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getStartUnit() {
        return startUnit;
    }

    public void setStartUnit(String startUnit) {
        this.startUnit = startUnit;
    }

    public String getTaskSource() {
        return taskSource;
    }

    public void setTaskSource(String taskSource) {
        this.taskSource = taskSource;
    }

    public LocalDateTime getDueTime() {
        return dueTime;
    }

    public void setDueTime(LocalDateTime dueTime) {
        this.dueTime = dueTime;
    }
}
