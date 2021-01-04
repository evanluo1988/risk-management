package com.springboot.domain.risk;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author 刘宏飞
 * @Date 2020/12/15 15:02
 * @Version 1.0
 */
@Data
@ToString
@EqualsAndHashCode(exclude = {"id","createTime","createBy","updateTime","updateBy","reqId","businessId"})
@TableName(value = "ent_wy_mortgagecanlist")
public class EntWyMortgagecan extends BaseDomain {
    /**
     * 申请编号ID 由我方生成
     */
    @TableField(value = "req_id")
    private String reqId;
    /**
     * 业务申请编号
     */
    @TableField(value = "businessid")
    private String businessId;

    /**
     * 登记编号
     */
    @TableField(value = "mabregno")
    @JSONField(name = "mabRegNo")
    private String mabRegNo;

    /**
     * 注销日期
     */
    @TableField(value = "mabcandate")
    @JSONField(name = "mabCanDate")
    private String mabCanDate;

    /**
     * 注销原因
     */
    @TableField(value = "mabcanreason")
    @JSONField(name = "mabCanReason")
    private String mabCanReason;
}
