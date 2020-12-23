package com.springboot.vo.risk;

import lombok.Data;

/**
 * 透析列表
 */
@Data
public class DialysisVo {
    /**
     * 评价雷达
     */
    private String evaluationRadar;
    /**
     * 评价维度
     */
    private String evaluationDimension;
    /**
     * 时点/时段
     */
    private String timeInterval;
    /**
     * 关注项
     */
    private String concerns;
    /**
     * 客户实际值
     */
    private String actualValue;
    /**
     * 理想区间值
     */
    private String idealIntervalValue;
}
