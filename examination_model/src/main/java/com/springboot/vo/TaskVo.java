package com.springboot.vo;

import com.springboot.config.DateFormatConfig;
import com.springboot.page.PageIn;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TaskVo extends PageIn {
    public interface DispatcherGroup{ }
    public interface RefundGroup{}
    /**
     * 区域ID
     */
    @NotNull(message = "区域不能为空",groups = DispatcherGroup.class)
    private Long areaId;
    @NotBlank(message = "退回原因必填",groups = RefundGroup.class)
    private String refundReason;

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
     * 完成情况
     */
    private String checkStatus;
    /**
     * 处置阶段
     */
    private String disposalStage;
    /**
     * 下发状态
     */
    private String assignment;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 发起时间
     */
    private String startTime;
    /**
     * 任务来源
     */
    private String taskSource;
    /**
     * 待核查企业数
     */
    private int waitCheckCount;
    /**
     * 任务状态
     */
    private String taskStatus;
    /**
     * 到期时间
     */
    private String dueTime;
    /**
     * 风险等级
     */
    private String riskLevel;

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
    @DateTimeFormat(pattern = DateFormatConfig.DATE_FORMAT)
    private LocalDate warnInterviewTime;
    /**
     * 责令整改时间
     */
    @DateTimeFormat(pattern = DateFormatConfig.DATE_FORMAT)
    private LocalDate orderRectificationTime;
    /**
     * 停业整顿时间
     */
    @DateTimeFormat(pattern = DateFormatConfig.DATE_FORMAT)
    private LocalDate stopRectificationTime;
    /**
     * 查封、冻结资金时间
     */
    @DateTimeFormat(pattern = DateFormatConfig.DATE_FORMAT)
    private LocalDate freezingFundsTime;
    /**
     * 其他时间
     */
    @DateTimeFormat(pattern = DateFormatConfig.DATE_FORMAT)
    private LocalDate otherTime;

    private LocalDate expireTime;

    private String enable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTaskSource() {
        return taskSource;
    }

    public void setTaskSource(String taskSource) {
        this.taskSource = taskSource;
    }

    public int getWaitCheckCount() {
        return waitCheckCount;
    }

    public void setWaitCheckCount(int waitCheckCount) {
        this.waitCheckCount = waitCheckCount;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }
}
