package com.springboot.model;

import com.springboot.domain.Task;
import com.springboot.util.DateUtils;
import com.springboot.vo.TaskVo;

public class TaskModel extends Task {
    private String taskStatus;
    private Boolean overdue;
    private int waitCheckCount;

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Boolean getOverdue() {
        return overdue;
    }

    public void setOverdue(Boolean overdue) {
        this.overdue = overdue;
    }

    public int getWaitCheckCount() {
        return waitCheckCount;
    }

    public void setWaitCheckCount(int waitCheckCount) {
        this.waitCheckCount = waitCheckCount;
    }

    public TaskVo convertVo(){
        TaskVo taskVo = new TaskVo();
        taskVo.setId(this.getId());
        taskVo.setTaskNumber(this.getTaskNumber());
        taskVo.setTaskName(this.getTaskName());
        taskVo.setStartTime(this.getStartTime() != null ? DateUtils.convertDateStr(this.getStartTime()):"");
        taskVo.setTaskSource(this.getTaskSource());
        taskVo.setWaitCheckCount(this.getWaitCheckCount());
        taskVo.setTaskStatus(this.getTaskStatus());
        taskVo.setDueTime(this.getDueTime() != null ? DateUtils.convertDateStr(this.getStartTime()):"");
        taskVo.setOverdue(this.getOverdue());
        return taskVo;
    }
}
