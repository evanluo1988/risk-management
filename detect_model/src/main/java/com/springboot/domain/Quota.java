package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "quotas")
public class Quota extends BaseDomain {
    /**
     * 指标码
     */
    @TableField(value = "quota_code")
    private String quotaCode;
    /**
     * 分档码
     */
    @TableField(value = "grand_code")
    private String grandCode;
    /**
     * 指标名称
     */
    @TableField(value = "quota_name")
    private String quotaName;
    /**
     * 指标类型
     */
    @TableField(value = "quota_type")
    private String quotaType;
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
    /**
     * 指标维度模块ID
     */
    @TableField(value = "model_id")
    private Long modelId;
    /**
     * 指标一级维度ID
     */
    @TableField(value = "first_level_id")
    private String firstLevelId;
    /**
     * 指标二级维度ID
     */
    @TableField(value = "second_level_id")
    private String secondLevelId;
    /**
     * 时段
     */
    @TableField(value = "time_interval")
    private String timeInterval;

}
