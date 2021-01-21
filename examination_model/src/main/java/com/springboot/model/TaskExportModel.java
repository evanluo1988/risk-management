package com.springboot.model;

import lombok.Data;

import java.util.Objects;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/6 10:18
 */
@Data
public class TaskExportModel {
    private String taskNumber;
    private String enterpriseName;
    private String checkRegion;
    private String startTime;
    private String riskLevel;
    private String registrationNumber;
    private String legalPerson;
    private String establishedTime;
    private String registrationAmount;
    private String managementStatus;
    private String registrationAddress;
    private String disposalStage;
    private String industry;
    private String businessAddress;
    private Long money;
    private Float moneyFloat;
    private String involvePeople;
    private String employeesNumber;
    private String involveInternet;
    private String regionalScale;
    private String involveArea;
    private String riskDetails;
    private String warnInterviewTime;
    private String orderRectificationTime;
    private String stopRectificationTime;
    private String freezingFundsTime;
    private String otherTime;
    private String judicialCase;
    private String casePlace;
    private String caseTime;
    private String punishment;
    private String relatedClues;
    private String expireTime;

    public Float getMoneyFloat() {
        if (Objects.nonNull(money)) {
            moneyFloat = Float.valueOf(String.valueOf(money)) / 100;
        }
        return moneyFloat;
    }
}
