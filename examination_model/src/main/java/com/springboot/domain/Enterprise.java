package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper=true)
@TableName("enterprises")
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
     * 是否司立案
     */
    private Integer judicialCase;
    /**
     * 立案地区
     */
    private String casePlace;
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
     * 处置阶段
     */
    private String disposalStage;
    /**
     * 风险等级
     */
    private String riskLevel;
}
