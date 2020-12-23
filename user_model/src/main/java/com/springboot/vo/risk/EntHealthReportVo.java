package com.springboot.vo.risk;

import lombok.Data;

/**
 * 企业健康报告
 */
@Data
public class EntHealthReportVo {
    /**
     * 企业健康评价
     */
    private EntHealthAssessmentVo entHealthAssessment;
    /**
     * 企业健康详情
     */
    private EntHealthDetailsVo entHealthDetails;
}
