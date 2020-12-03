package com.springboot.domain;

import java.time.LocalDateTime;

public class EnterpriseDetail extends BaseDomain {
    private static final long serialVersionUID = -5024580412531918526L;
    /**
     * 企业ID
     */
    private Long enterpriseId;
    /**
     * 处置阶段
     */
    private String disposalStage;
    /**
     * 是否司立案
     */
    private Boolean judicialCase;
    /**
     * 立案地区
     */
    private String casePlace;
    /**
     * 立案时间
     */
    private LocalDateTime caseTime;
    /**
     * 行政处罚
     */
    private String punishment;

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getDisposalStage() {
        return disposalStage;
    }

    public void setDisposalStage(String disposalStage) {
        this.disposalStage = disposalStage;
    }

    public Boolean getJudicialCase() {
        return judicialCase;
    }

    public void setJudicialCase(Boolean judicialCase) {
        this.judicialCase = judicialCase;
    }

    public String getCasePlace() {
        return casePlace;
    }

    public void setCasePlace(String casePlace) {
        this.casePlace = casePlace;
    }

    public LocalDateTime getCaseTime() {
        return caseTime;
    }

    public void setCaseTime(LocalDateTime caseTime) {
        this.caseTime = caseTime;
    }

    public String getPunishment() {
        return punishment;
    }

    public void setPunishment(String punishment) {
        this.punishment = punishment;
    }
}
