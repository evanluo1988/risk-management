package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@TableName("quota_values")
public class QuotaValue extends BaseDomain {
    /**
     *
     */
    @TableField(value = "req_id")
    private String reqId;
    @TableField(value = "quota_id")
    private Long quotaId;
    @TableField(value = "quota_value")
    private String quotaValue;
    @TableField(value = "entname")
    private String entName;
    @TableField(value = "minus_points")
    private Integer minusPoints;
    @TableField(value = "ideal_interval")
    private String idealInterval;
}
