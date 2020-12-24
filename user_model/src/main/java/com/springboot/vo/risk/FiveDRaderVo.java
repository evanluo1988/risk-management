package com.springboot.vo.risk;

import lombok.Data;

import java.util.List;

@Data
public class FiveDRaderVo {
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
    private double LegalRiskScore;

    private List<FiveDRaderItemVo> fiveDRaderItemList;
}
