package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.risk.StdLegalEnterpriseExecuted;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author 刘宏飞
 * @Date 2020/12/18 13:24
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("std_legal_enterprise_executed_temp")
public class StdLegalEnterpriseExecutedTemp extends StdLegalEnterpriseExecuted {
    /**
     * 风险等级
     */
    @TableField(value = "caserisklevel")
    private String caseRiskLevel;
    private Long stdLegalEnterpriseExecutedId;
}
