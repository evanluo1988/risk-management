package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("quota_grands")
public class QuotaGrand extends BaseDomain {
    /**
     * 分档名称
     */
    @TableField(value = "grand_name")
    private String grandName;
    /**
     * 分档码
     */
    @TableField(value = "grand_code")
    private String grandCode;
    /**
     * 上限值
     */
    @TableField(value = "grand_upper")
    private BigDecimal grandUpper;
    /**
     * 下线值
     */
    @TableField(value = "grand_lower")
    private BigDecimal grandLower;
    /**
     * 指标值
     */
    @TableField(value = "quota_value")
    private String quotaValue;
    /**
     * 是否是理想区间（Y是，N否）
     */
    @TableField(value = "ideal_interval")
    private String idealInterval;
    /**
     * 减分
     */
    @TableField(value = "minus_points")
    private Integer minusPoints;
}
