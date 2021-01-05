package com.springboot.vo.risk;

import com.springboot.util.StrUtils;
import lombok.Data;

/**
 * 透析列表
 */
@Data
public class DialysisVo {
    /**
     * 指标ID
     */
    private Long quotaId;
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

    public String getActualValue() {
        if(quotaId == 49L) {
            return StrUtils.getRatioStr(actualValue);
        }else if (quotaId == 21L){
            return StrUtils.getRatioStr(actualValue);
        }else if (quotaId == 24L){
            return StrUtils.getIntStr(actualValue);
        }
        return actualValue;
    }
}
