package com.springboot.model;

import com.springboot.domain.*;

import java.util.List;

public class TaskImportModel {
    private Task task;
    private List<TaskCheck> taskCheckList;
    private List<Enterprise> enterpriseList;
    private List<EnterpriseDetail> enterpriseDetailList;
    private List<TaskDisposition> taskDispositionList;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public List<TaskCheck> getTaskCheckList() {
        return taskCheckList;
    }

    public void setTaskCheckList(List<TaskCheck> taskCheckList) {
        this.taskCheckList = taskCheckList;
    }

    public List<Enterprise> getEnterpriseList() {
        return enterpriseList;
    }

    public void setEnterpriseList(List<Enterprise> enterpriseList) {
        this.enterpriseList = enterpriseList;
    }

    public List<EnterpriseDetail> getEnterpriseDetailList() {
        return enterpriseDetailList;
    }

    public void setEnterpriseDetailList(List<EnterpriseDetail> enterpriseDetailList) {
        this.enterpriseDetailList = enterpriseDetailList;
    }

    public List<TaskDisposition> getTaskDispositionList() {
        return taskDispositionList;
    }

    public void setTaskDispositionList(List<TaskDisposition> taskDispositionList) {
        this.taskDispositionList = taskDispositionList;
    }
}
