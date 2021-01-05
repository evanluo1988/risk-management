package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author 刘宏飞
 * @Date 2020/12/16 14:56
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "std_ent_alter_list")
public class StdEntAlter extends BaseDomain {

    private String reqId;

    /**
     * 年度
     */
    @TableField(exist = false)
    private String altdateYear;

    /**
     * 日期
     */
    private String altdate;

    /**
     * 变更项目
     */
    private String altitem;

    /**
     * 变更前
     */
    private String altbe;

    /**
     * 变更后
     */
    private String altaf;
}
