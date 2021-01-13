package com.springboot.vo.risk;

import com.springboot.utils.StrUtils;
import lombok.Data;

/**
 * 企业健康检测雷达
 */
@Data
public class EntHealthDetectionRadarVo {
    /**
     * 企业名称
     */
    private String entName;
    /**
     * 所属行业
     */
    private String industry;
    /**
     * 统一社会信用代码
     */
    private String creditCode;
    /**
     * 注册资本
     */
    private String regCap;
    /**
     * 币种
     */
    private String regCapCur;
    /**
     * 法定代表人
     */
    private String lrName;
    /**
     * 经营年限
     */
    private String establishPeriod;
    /**
     * 经营稳定性评分
     */
    private String businessStabilityScore;
    /**
     * 知识产权价值度评分
     */
    private String intellectualPropertyScore;
    /**
     * 经营风险评分
     */
    private String businessRiskScore;
    /**
     * 法律风险评分
     */
    private String legalRiskScore;

    public String getRegCap() {
        return StrUtils.getMoneyText(regCap, regCapCur);
    }
}
