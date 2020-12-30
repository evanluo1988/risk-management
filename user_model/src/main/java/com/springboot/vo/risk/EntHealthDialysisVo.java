package com.springboot.vo.risk;

import lombok.Data;

import java.util.List;

/**
 * 企业健康检测透析
 */
@Data
public class EntHealthDialysisVo {
    /**
     * 五维雷达透析一览表
     */
    FiveDRaderVo fiveDRader;
    /**
     * 经营稳定性
     */
    List<DialysisVo> businessStabilityList;
    /**
     * 知识产权价值度透析
     */
    List<DialysisVo> intellectualPropertyList;
    /**
     * 经营风险透析
     */
    List<DialysisVo> businessRiskList;
    /**
     * 法律风险透析
     */
    List<DialysisVo> legalRiskList;

}
