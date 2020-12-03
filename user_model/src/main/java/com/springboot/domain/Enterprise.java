package com.springboot.domain;

import java.time.LocalDateTime;

public class Enterprise extends BaseDomain {
    private static final long serialVersionUID = 1423774902833637941L;
    /**
     * 企业名称
     */
    private String enterpriseName;
    /**
     * 成立时间
     */
    private LocalDateTime establishedTime;
    /**
     * 经营状态
     */
    private String managementStatus;
    /**
     * 法人代表
     */
    private String legalPerson;
    /**
     * 注册资本
     */
    private String registrationAmount;
    /**
     * 注册地址
     */
    private String registrationAddress;
    /**
     * 注册号
     */
    private String registrationNumber;
    /**
     * 所属行业
     */
    private String industry;
    /**
     * 员工人数
     */
    private String employeesNumber;
    /**
     * 实际经营地址
     */
    private String businessAddress;
    /**
     * 风险等级
     */
    private String riskLevel;

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public LocalDateTime getEstablishedTime() {
        return establishedTime;
    }

    public void setEstablishedTime(LocalDateTime establishedTime) {
        this.establishedTime = establishedTime;
    }

    public String getManagementStatus() {
        return managementStatus;
    }

    public void setManagementStatus(String managementStatus) {
        this.managementStatus = managementStatus;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getRegistrationAmount() {
        return registrationAmount;
    }

    public void setRegistrationAmount(String registrationAmount) {
        this.registrationAmount = registrationAmount;
    }

    public String getRegistrationAddress() {
        return registrationAddress;
    }

    public void setRegistrationAddress(String registrationAddress) {
        this.registrationAddress = registrationAddress;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getEmployeesNumber() {
        return employeesNumber;
    }

    public void setEmployeesNumber(String employeesNumber) {
        this.employeesNumber = employeesNumber;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
}
