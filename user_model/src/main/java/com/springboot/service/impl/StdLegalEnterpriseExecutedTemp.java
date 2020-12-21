package com.springboot.service.impl;

import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.risk.StdLegalEnterpriseExecuted;
import lombok.Data;

/**
 * @Author 刘宏飞
 * @Date 2020/12/18 13:24
 * @Version 1.0
 */
@Data
@TableName("std_legal_enterprise_executed_temp")
public class StdLegalEnterpriseExecutedTemp extends StdLegalEnterpriseExecuted {
    /**
     * 风险等级
     */
    private String caseRiskLevel;
    private Long stdLegalEnterpriseExecutedId;
}
