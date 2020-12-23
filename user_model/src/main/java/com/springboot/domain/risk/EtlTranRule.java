package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@TableName("etl_tran_rules")
public class EtlTranRule extends BaseDomain {
    @TableField(value = "xh")
    private String xh;
    @TableField(value = "source_table")
    private String sourceTable;
    @TableField(value = "target_table")
    private String targetTable;
    @TableField(value = "tran_rule")
    private String tranRule;
    @TableField(value = "status")
    private String status;
    @TableField(value = "mark")
    private String mark;
    @TableField(value = "type")
    private String type;
}
