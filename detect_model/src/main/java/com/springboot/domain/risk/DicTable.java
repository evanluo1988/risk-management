package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

/**
 * 字典表
 */
@Data
@TableName("dic_table")
public class DicTable extends BaseDomain {
    @TableField(value = "dic_type")
    private String dicType;
    @TableField(value = "dic_value")
    private String dicValue;
    @TableField(value = "dic_name")
    private String dicName;
    @TableField(value = "dic_mark")
    private String dicMark;
}
