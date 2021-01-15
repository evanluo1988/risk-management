package com.springboot.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.springboot.domain.*;
import com.springboot.easyexcel.converter.*;
import com.springboot.utils.ConvertUtils;
import com.springboot.utils.DateUtils;
import com.springboot.utils.ServerCacheUtils;
import com.springboot.utils.UserAuthInfoContext;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

@Data
public class TaskImportVo {

    @ExcelProperty(value = {"单号"}, index = 0)
    private String taskNumber;

    @ExcelProperty(value = {"核查企业"}, index = 1)
    private String enterpriseName;

    @ExcelProperty(value = {"核查地区"}, index = 2)
    private String checkRegion;

    @ExcelProperty(value = {"创建时间"}, index = 3)
    private Date startTimeStr;

    @ExcelProperty(value = {"风险等级"}, index = 4, converter = RiskLevelConverter.class)
    private String riskLevel;

    @ExcelProperty(value = {"注册号"}, index = 5)
    private String registrationNumber;

    @ExcelProperty(value = {"法人代表人姓名"}, index = 6)
    private String legalPerson;

    @ExcelProperty(value = {"开业日期"}, index = 7, converter = TaskEstablishedTimeConverter.class)
    private LocalDateTime establishedTime;

    @ExcelProperty(value = {"注册资本"}, index = 8)
    private String registrationAmount;

    @ExcelProperty(value = {"经营状态"}, index = 9)
    private String managementStatus;

    @ExcelProperty(value = {"住址"}, index = 10)
    private String registrationAddress;

    @ExcelProperty(value = {"处置阶段"}, index = 11, converter = DisposalStageConverter.class)
    private String disposalStage;

    @ExcelProperty(value = {"所属行业"}, index = 12)
    private String industry;

    @ExcelProperty(value = {"真实地区"}, index = 13)
    private String businessAddress;

    @ExcelProperty(value = {"涉案金额"}, index = 14)
    private String money;

    @ExcelProperty(value = {"涉案人数"}, index = 15)
    private String involvePeople;

    @ExcelProperty(value = {"员工人数"}, index = 16)
    private String employeesNumber;

    @ExcelProperty(value = {"是否涉及互联网"}, index = 17, converter = InvolveInternetConverter.class)
    private String involveInternet;

    @ExcelProperty(value = {"跨省跨区情况"}, index = 18 ,converter = RegionalScaleConverter.class)
    private String regionalScale;

    @ExcelProperty(value = {"涉及地区"}, index = 19)
    private String involveArea;

    @ExcelProperty(value = {"核查风险详情"}, index = 20)
    private String riskDetails;

    @ExcelProperty(value = {"警署约谈"}, index = 21)
    private Date warnInterviewTime;

    @ExcelProperty(value = {"责令整改"}, index = 22)
    private Date orderRectificationTime;

    @ExcelProperty(value = {"停业整顿"}, index = 23)
    private Date stopRectificationTime;

    @ExcelProperty(value = {"查封、冻结资金"}, index = 24)
    private Date freezingFundsTime;

    @ExcelProperty(value = {"其他"}, index = 25)
    private Date otherTime;

    @ExcelProperty(value = {"司法立案"}, index = 26 ,converter = JudicialCaseConverter.class)
    private Integer judicialCase;

    @ExcelProperty(value = {"立案地区"}, index = 27)
    private String casePlace;

    @ExcelProperty(value = {"立案时间"}, index = 28)
    private Date caseTime;

    @ExcelProperty(value = {"行政处罚"}, index = 29)
    private String punishment;

    @ExcelProperty(value = {"相关线索"}, index = 30)
    private String relatedClues;

    @ExcelProperty(value = {"逾期时间"},index = 31)
    private Date expireTimeStr;

    public Task toTask() {
        Task task = ConvertUtils.sourceToTarget(this, Task.class);
        task.setStartTime(DateUtils.dateToLocalDateTime(this.startTimeStr));
        task.setCreateBy(UserAuthInfoContext.getUserName());
        task.setCreateTime(new Date());
        return task;
    }

    public TaskCheck toTaskCheck() {
        TaskCheck taskCheck = ConvertUtils.sourceToTarget(this, TaskCheck.class);
        taskCheck.setAreaId(ServerCacheUtils.getAreaByName(this.getCheckRegion()).getId());
        taskCheck.setExpireTime(Objects.isNull(expireTimeStr) ? null : expireTimeStr.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        taskCheck.setCreateBy(UserAuthInfoContext.getUserName());
        taskCheck.setCreateTime(new Date());
        return taskCheck;
    }

    public Enterprise toEnterprise() {
        Enterprise enterprise = ConvertUtils.sourceToTarget(this, Enterprise.class);
        enterprise.setCreateBy(UserAuthInfoContext.getUserName());
        enterprise.setCreateTime(new Date());
        return enterprise;
    }

    public TaskDisposition toTaskDisposition() {
        TaskDisposition taskDisposition = ConvertUtils.sourceToTarget(this, TaskDisposition.class);
        taskDisposition.setCreateBy(UserAuthInfoContext.getUserName());
        taskDisposition.setCreateTime(new Date());
        return taskDisposition;
    }
}
