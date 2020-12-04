package com.springboot.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDisposition extends BaseDomain {
    private static final long serialVersionUID = -8959866075037296525L;
    /**
     * 任务核查ID
     */
    private Long taskCheckId;
    /**
     * 涉案人数
     */
    private String involvePeople;
    /**
     * 涉案金额
     */
    private String money;
    /**
     * 员工人数
     */
    private String employeesNumber;
    /**
     * 集资模式
     */
    private String fundRaisingMode;
    /**
     * 未兑付资金
     */
    private String outstandingFund;
    /**
     * 清退资金
     */
    private String withdrawalFund;
    /**
     * 是否涉及互联网
     */
    private String involveInternet;
    /**
     * 地域规模
     */
    private String regionalScale;
    /**
     * 涉及地区
     */
    private String involveArea;
    /**
     * 核查风险详情
     */
    private String riskDetails;
    /**
     * 警示约谈时间
     */
    private LocalDateTime warnInterviewTime;
    /**
     * 责令整改时间
     */
    private LocalDateTime orderRectificationTime;
    /**
     * 停业整顿时间
     */
    private LocalDateTime stopRectificationTime;
    /**
     * 查封、冻结资金时间
     */
    private LocalDateTime freezingFundsTime;
    /**
     * 其他时间
     */
    private LocalDateTime otherTime;
}
