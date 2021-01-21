package com.springboot.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/20 17:37
 */
@Data
public class EnterpriseVo {
    private Long id;
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
     * 处置阶段
     */
    private String disposalStage;
    /**
     * 是否司立案
     */
    private Integer judicialCase;
    /**
     * 立案时间
     */
    private LocalDateTime caseTime;
    /**
     * 所属行业
     */
    private String industry;
    /**
     * 实际经营地址
     */
    private String businessAddress;
    /**
     * 行政处罚
     */
    private String punishment;
    /**
     * 风险等级
     */
    private String riskLevel;
}
