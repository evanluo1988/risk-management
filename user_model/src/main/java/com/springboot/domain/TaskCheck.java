package com.springboot.domain;

import lombok.Data;

@Data
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
}
