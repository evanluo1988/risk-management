package com.springboot.vo;

import com.springboot.domain.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TaskCheckPageVo {
    /**
     *
     */
    private Long id;
    /**
     * 任务编号
     */
    private String taskNumber;
    /**
     * 核查企业名称
     */
    private String enterpriseName;
    /**
     * 核查地区
     */
    private String checkRegion;
    /**
     * 相关线索
     */
    private String relatedClues;
    /**
     * 处置阶段
     */
    private String disposalStage;
    /**
     * 下发状态
     */
    private String assignment;
    /**
     * 完成情况
     */
    private String checkStatus;
    /**
     * 发起时间
     */
    private LocalDate startTime;
    /**
     * 到期时间
     */
    private LocalDate expireTime;
    /**
     * 是否逾期
     */
    private Boolean overdue;
    /**
     * 退回统计
     */
    private Long refundCount;

    private Long areaId;

    private String areaName;
    // 核查街道联系方式
    private List<User> areaContact;
}
