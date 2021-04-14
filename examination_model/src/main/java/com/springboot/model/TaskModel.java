package com.springboot.model;

import com.springboot.domain.Task;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskModel extends Task {
    private String taskStatus;
    private Boolean overdue;
    private int waitCheckCount;
    private String enterpriseName;
    private String checkStatus;
    private String disposalStage;
    private String assignment;
    private String checkRegion;
    private LocalDate expireTime;
    private String relatedClues;
    private String enable;
    private Long refundCount;
    private Long areaId;
    private String areaName;

//    public TaskPageVo convertVo(){
//        TaskPageVo taskVo = new TaskPageVo();
//        taskVo.setId(this.getId());
//        taskVo.setTaskNumber(this.getTaskNumber());
//        taskVo.setTaskName(this.getTaskName());
//        taskVo.setStartTime(this.getStartTime() != null ? DateUtils.convertDateStr(this.getStartTime()):"");
//        taskVo.setWaitCheckCount(this.getWaitCheckCount());
//        taskVo.setTaskStatus(this.getTaskStatus());
//        taskVo.setDueTime(this.getDueTime() != null ? DateUtils.convertDateStr(this.getStartTime()):"");
//        return taskVo;
//    }
}
