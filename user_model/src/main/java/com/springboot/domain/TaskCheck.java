package com.springboot.domain;

public class TaskCheck extends BaseDomain {
    private static final long serialVersionUID = 8846340164072346603L;
    /**
     * 任务ID
     */
    private Long taskId;
    /**
     * 企业ID
     */
    private Long enterpriseId;
    /**
     * 核查地区
     */
    private String checkRegion;
    /**
     * 相关线索
     */
    private String relatedClues;
    /**
     * 核查状态
     */
    private String checkStatus;
    /**
     * 是否逾期
     */
    private Boolean overdue;
    /**
     * 分发状态
     */
    private String assignment;
    /**
     * 分发区域
     */
    private Long areaId;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getCheckRegion() {
        return checkRegion;
    }

    public void setCheckRegion(String checkRegion) {
        this.checkRegion = checkRegion;
    }

    public String getRelatedClues() {
        return relatedClues;
    }

    public void setRelatedClues(String relatedClues) {
        this.relatedClues = relatedClues;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Boolean getOverdue() {
        return overdue;
    }

    public void setOverdue(Boolean overdue) {
        this.overdue = overdue;
    }

    public String getAssignment() {
        return assignment;
    }

    public void setAssignment(String assignment) {
        this.assignment = assignment;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }
}
