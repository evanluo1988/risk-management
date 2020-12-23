package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("quota_dimensions")
public class QuotaDimension extends BaseDomain {
    /**
     * 维度类型
     */
    @TableField("dimension_type")
    private String dimensionType;
    /**
     * 维度名字
     */
    @TableField("dimension_name")
    private String dimensionName;
    /**
     * 维度编码
     */
    @TableField("dimension_code")
    private String dimensionCode;

}
