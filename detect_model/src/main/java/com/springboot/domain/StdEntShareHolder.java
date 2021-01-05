package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author 刘宏飞
 * @Date 2020/12/16 14:33
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper=true)
@TableName(value = "std_ent_share_holder_list")
public class StdEntShareHolder extends BaseDomain {

    private String reqId;

    /**
     * 股东名称
     */
    @TableField(value = "shareholdername")
    private String shareHolderName;

    /**
     * 认缴出资额
     */
    private String subconam;

    /**
     * 出资日期
     */
    @TableField(value = "condate")
    private String conDate;

    /**
     * 出资比例
     */
    private String fundedratio;

    /**
     * 股东类型
     */
    private String invtype;

    /**
     * 出资方式
     */
    private String conform;
}
