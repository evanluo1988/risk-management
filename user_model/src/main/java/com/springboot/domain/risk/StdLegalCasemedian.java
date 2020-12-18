package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

@Data
@TableName("std_legal_casemedian")
public class StdLegalCasemedian extends BaseDomain {
    /**
     * 申请编号ID 由我方生成
     */
    @TableField(value = "req_id")
    private String reqId;
    /**
     * 业务申请编号
     */
    @TableField(value = "businessid")
    private String businessId;
    /**
     * 序列号
     */
    @TableField(value = "serialno")
    private String serialNo;
    /**
     * 案由分类
     */
    @TableField(value = "caseclass")
    private String caseClass;
    /**
     * 涉案金额
     */
    @TableField(value = "payment")
    private String payment;
    /**
     * 审理结果
     */
    @TableField(value = "sentencebrief")
    private String sentenceBrief;
    /**
     * 审理程序
     */
    @TableField(value = "phase")
    private String phase;
    /**
     * 法院等级
     */
    @TableField(value = "courtlevel")
    private String courtLevel;
    /**
     * 案件影响力
     */
    @TableField(value = "caseimpact")
    private String caseImpact;
    /**
     * 案件结果对客户影响
     */
    @TableField(value = "sentenceeffect")
    private String sentenceEffect;
    /**
     * 案由等级
     */
    @TableField(value = "casereasonlevel")
    private String caseReasonLevel;
    /**
     * 案件重要性
     */
    @TableField(value = "caselevel")
    private String caseLevel;
    /**
     * 结案状态
     */
    @TableField(value = "caseresult")
    private String caseResult;
    /**
     * 间隔年数
     */
    @TableField(value = "intervalyear")
    private String intervalYear;
    /**
     * 涉案金额比例
     */
    @TableField(value = "paymentscale")
    private String paymentScale;
    /**
     * 诉讼地位
     */
    @TableField(value = "lawstatus")
    private String lawStatus;
    /**
     * 案件风险等级
     */
    @TableField(value = "caserisklevel")
    private String caseRiskLevel;

}
