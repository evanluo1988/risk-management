package com.springboot.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.springboot.domain.*;
import com.springboot.easyexcel.converter.InformCheckStatusConverter;
import com.springboot.easyexcel.converter.TaskEstablishedTimeConverter;
import com.springboot.util.ConvertUtils;
import com.springboot.util.DateUtils;
import com.springboot.utils.UserAuthInfoContext;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class TaskImportVo {

    @ExcelProperty(value = {"单号"}, index=0)
    private String taskNumber;

    @ExcelProperty(value = {"核查企业"}, index=1)
    private String enterpriseName;

    @ExcelProperty(value = {"核查地区"}, index=2)
    private String checkRegion;

    @ExcelProperty(value = {"创建时间"}, index=3)
    private Date startTimeStr;

    @ExcelProperty(value = {"风险等级"}, index = 4)
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

    @ExcelProperty(value = {"处置阶段"}, index = 11)
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

    @ExcelProperty(value = {"是否涉及互联网"}, index = 17)
    private String involveInternet;

    @ExcelProperty(value = {"跨省跨区情况"}, index = 18)
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

    @ExcelProperty(value = {"司法立案"}, index = 26)
    private String judicialCase;

    @ExcelProperty(value = {"立案地区"}, index = 27)
    private String casePlace;

    @ExcelProperty(value = {"立案时间"}, index = 28)
    private Date caseTime;

    @ExcelProperty(value = {"行政处罚"}, index = 29)
    private String punishment;

    @ExcelProperty(value = {"相关线索"}, index = 30)
    private String relatedClues;

    public String getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getCheckRegion() {
        return checkRegion;
    }

    public void setCheckRegion(String checkRegion) {
        this.checkRegion = checkRegion;
    }

    public Date getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(Date startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public LocalDateTime getEstablishedTime() {
        return establishedTime;
    }

    public void setEstablishedTime(LocalDateTime establishedTime) {
        this.establishedTime = establishedTime;
    }

    public String getRegistrationAmount() {
        return registrationAmount;
    }

    public void setRegistrationAmount(String registrationAmount) {
        this.registrationAmount = registrationAmount;
    }

    public String getManagementStatus() {
        return managementStatus;
    }

    public void setManagementStatus(String managementStatus) {
        this.managementStatus = managementStatus;
    }

    public String getRegistrationAddress() {
        return registrationAddress;
    }

    public void setRegistrationAddress(String registrationAddress) {
        this.registrationAddress = registrationAddress;
    }

    public String getDisposalStage() {
        return disposalStage;
    }

    public void setDisposalStage(String disposalStage) {
        this.disposalStage = disposalStage;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
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

    public String getEmployeesNumber() {
        return employeesNumber;
    }

    public void setEmployeesNumber(String employeesNumber) {
        this.employeesNumber = employeesNumber;
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

    public Date getWarnInterviewTime() {
        return warnInterviewTime;
    }

    public void setWarnInterviewTime(Date warnInterviewTime) {
        this.warnInterviewTime = warnInterviewTime;
    }

    public Date getOrderRectificationTime() {
        return orderRectificationTime;
    }

    public void setOrderRectificationTime(Date orderRectificationTime) {
        this.orderRectificationTime = orderRectificationTime;
    }

    public Date getStopRectificationTime() {
        return stopRectificationTime;
    }

    public void setStopRectificationTime(Date stopRectificationTime) {
        this.stopRectificationTime = stopRectificationTime;
    }

    public Date getFreezingFundsTime() {
        return freezingFundsTime;
    }

    public void setFreezingFundsTime(Date freezingFundsTime) {
        this.freezingFundsTime = freezingFundsTime;
    }

    public Date getOtherTime() {
        return otherTime;
    }

    public void setOtherTime(Date otherTime) {
        this.otherTime = otherTime;
    }

    public String getJudicialCase() {
        return judicialCase;
    }

    public void setJudicialCase(String judicialCase) {
        this.judicialCase = judicialCase;
    }

    public String getCasePlace() {
        return casePlace;
    }

    public void setCasePlace(String casePlace) {
        this.casePlace = casePlace;
    }

    public Date getCaseTime() {
        return caseTime;
    }

    public void setCaseTime(Date caseTime) {
        this.caseTime = caseTime;
    }

    public String getPunishment() {
        return punishment;
    }

    public void setPunishment(String punishment) {
        this.punishment = punishment;
    }

    public String getRelatedClues() {
        return relatedClues;
    }

    public void setRelatedClues(String relatedClues) {
        this.relatedClues = relatedClues;
    }

    public Task toTask() {
        Task task = ConvertUtils.sourceToTarget(this, Task.class);
        task.setStartTime(DateUtils.dateToLocalDateTime(this.startTimeStr));
        task.setCreateBy(UserAuthInfoContext.getUserName());
        task.setCreateTime(new Date());
        return task;
    }

    public TaskCheck toTaskCheck(){
        TaskCheck taskCheck = ConvertUtils.sourceToTarget(this, TaskCheck.class);
        taskCheck.setCreateBy(UserAuthInfoContext.getUserName());
        taskCheck.setCreateTime(new Date());
        return taskCheck;
    }

    public Enterprise toEnterprise(){
        Enterprise enterprise = ConvertUtils.sourceToTarget(this, Enterprise.class);
        enterprise.setCreateBy(UserAuthInfoContext.getUserName());
        enterprise.setCreateTime(new Date());
        return enterprise;
    }

    public EnterpriseDetail toEnterpriseDetail(){
        EnterpriseDetail enterpriseDetail = ConvertUtils.sourceToTarget(this, EnterpriseDetail.class);
        enterpriseDetail.setCreateBy(UserAuthInfoContext.getUserName());
        enterpriseDetail.setCreateTime(new Date());
        return enterpriseDetail;
    }

    public TaskDisposition toTaskDisposition(){
        TaskDisposition taskDisposition = ConvertUtils.sourceToTarget(this, TaskDisposition.class);
        taskDisposition.setCreateBy(UserAuthInfoContext.getUserName());
        taskDisposition.setCreateTime(new Date());
        return taskDisposition;
    }
}
