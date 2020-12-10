package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

@Data
@TableName(value = "quotas")
public class Quota extends BaseDomain {
    /**
     * 指标码
     */
    @TableField(value = "quota_code")
    private String quotaCode;
    /**
     * 指标名称
     */
    @TableField(value = "quota_name")
    private String quotaName;
    /**
     * 指标维度Id
     */
    @TableField(value = "quota_dimension_id")
    private Long quotaDimensionId;
    /**
     * 指标计算规则
     */
    @TableField(value = "quota_rule")
    private String quotaRule;
    /**
     * 计算指标类型（JAVA or SQL）
     */
    @TableField(value = "cul_type")
    private String culType;
    /**
     * 是否启用
     */
    @TableField(value = "enable")
    private String enable;
    /**
     * 备注
     */
    @TableField(value = "mark")
    private String mark;
}
