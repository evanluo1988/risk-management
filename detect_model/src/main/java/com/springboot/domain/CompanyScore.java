package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lhf
 * @date 2021/3/3 4:41 下午
 **/
@Data
@TableName("companies_score")
@EqualsAndHashCode(callSuper = true)
public class CompanyScore extends BaseDomain{
    private String reqId;
    /**
     * 经营稳定性综合得分
     */
    private double businessStabilityScore;
    /**
     * 知识产权价值度综合得分
     */
    private double intellectualPropertyScore;
    /**
     * 经营风险综合得分
     */
    private double businessRiskScore;
    /**
     * 法律风险综合得分
     */
    private double legalRiskScore;
}
