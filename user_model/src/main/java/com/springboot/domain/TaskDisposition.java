package com.springboot.domain;

import java.time.LocalDateTime;

public class TaskDisposition extends BaseDomain {
    private static final long serialVersionUID = -8959866075037296525L;
    /**
     * 任务核查ID
     */
    private Long taskCheckId;
    /**
     * 涉案金额
     */
    private String money;
    /**
     * 涉案人数
     */
    private String involvePeople;
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

    public Long getTaskCheckId() {
        return taskCheckId;
    }

    public void setTaskCheckId(Long taskCheckId) {
        this.taskCheckId = taskCheckId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getInvolvePeople() {
        return involvePeople;
    }

    public void setInvolvePeople(String involvePeople) {
        this.involvePeople = involvePeople;
    }

    public String getFundRaisingMode() {
        return fundRaisingMode;
    }

    public void setFundRaisingMode(String fundRaisingMode) {
        this.fundRaisingMode = fundRaisingMode;
    }

    public String getOutstandingFund() {
        return outstandingFund;
    }

    public void setOutstandingFund(String outstandingFund) {
        this.outstandingFund = outstandingFund;
    }

    public String getWithdrawalFund() {
        return withdrawalFund;
    }

    public void setWithdrawalFund(String withdrawalFund) {
        this.withdrawalFund = withdrawalFund;
    }

    public String getInvolveInternet() {
        return involveInternet;
    }

    public void setInvolveInternet(String involveInternet) {
        this.involveInternet = involveInternet;
    }

    public String getRegionalScale() {
        return regionalScale;
    }

    public void setRegionalScale(String regionalScale) {
        this.regionalScale = regionalScale;
    }

    public String getInvolveArea() {
        return involveArea;
    }

    public void setInvolveArea(String involveArea) {
        this.involveArea = involveArea;
    }

    public String getRiskDetails() {
        return riskDetails;
    }

    public void setRiskDetails(String riskDetails) {
        this.riskDetails = riskDetails;
    }

    public LocalDateTime getWarnInterviewTime() {
        return warnInterviewTime;
    }

    public void setWarnInterviewTime(LocalDateTime warnInterviewTime) {
        this.warnInterviewTime = warnInterviewTime;
    }

    public LocalDateTime getOrderRectificationTime() {
        return orderRectificationTime;
    }

    public void setOrderRectificationTime(LocalDateTime orderRectificationTime) {
        this.orderRectificationTime = orderRectificationTime;
    }

    public LocalDateTime getStopRectificationTime() {
        return stopRectificationTime;
    }

    public void setStopRectificationTime(LocalDateTime stopRectificationTime) {
        this.stopRectificationTime = stopRectificationTime;
    }

    public LocalDateTime getFreezingFundsTime() {
        return freezingFundsTime;
    }

    public void setFreezingFundsTime(LocalDateTime freezingFundsTime) {
        this.freezingFundsTime = freezingFundsTime;
    }

    public LocalDateTime getOtherTime() {
        return otherTime;
    }

    public void setOtherTime(LocalDateTime otherTime) {
        this.otherTime = otherTime;
    }
}
