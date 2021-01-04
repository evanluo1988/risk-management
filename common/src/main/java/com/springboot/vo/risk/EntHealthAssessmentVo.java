package com.springboot.vo.risk;

import lombok.Data;

/**
 * 企业健康评价
 */
@Data
public class EntHealthAssessmentVo {
    /**
     * 企业健康检测雷达
     */
    private EntHealthDetectionRadarVo entHealthDetectionRadar;
    /**
     * 企业健康透析
     */
    private EntHealthDialysisVo entHealthDialysis;
}
