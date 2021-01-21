package com.springboot.vo;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/20 17:36
 */
@Data
public class TaskDispositionVo {
    private Long id;
    /**
     * 涉案人数
     */
    private String involvePeople;
    /**
     * 涉案金额
     */
    private Long money;
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
    private LocalDate warnInterviewTime;
    /**
     * 责令整改时间
     */
    private LocalDate orderRectificationTime;
    /**
     * 停业整顿时间
     */
    private LocalDate stopRectificationTime;
    /**
     * 查封、冻结资金时间
     */
    private LocalDate freezingFundsTime;
    /**
     * 其他时间
     */
    private LocalDate otherTime;
}
