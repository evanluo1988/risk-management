package com.springboot.vo.risk;

import com.springboot.util.StrUtils;
import lombok.Builder;
import lombok.Data;

/**
 * 涉诉案件
 * @Author 刘宏飞
 * @Date 2020/12/23 17:06
 * @Version 1.0
 */
@Data
@Builder
public class LitigaCaseVo {
    /**
     * 案号
     */
    private String caseCode;

    /**
     * 案件类型
     */
    private String docClass;

    /**
     * 案由
     */
    private String caseReason;

    /**
     * 诉讼地位
     */
    private String lawStatus;

    /**
     * 间隔时间（年）
     */
    private String intervalYear;

    /**
     * 涉嫌金额（元）
     */
    private String payment;

    /**
     * 法院等级
     */
    private String courtLevel;

    /**
     * 审理结果
     */
    private String sentenceBrief;

    /**
     * 案件结果对客户的影响
     */
    private String sentenceEffect;

    public String getPayment() {
        return StrUtils.getMoney(payment);
    }

    public String getIntervalYear() {
        return StrUtils.getIntStr(intervalYear);
    }
}