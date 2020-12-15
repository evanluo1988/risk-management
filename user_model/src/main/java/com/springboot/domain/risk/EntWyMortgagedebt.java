package com.springboot.domain.risk;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

/**
 * @Author 刘宏飞
 * @Date 2020/12/15 14:56
 * @Version 1.0
 */
@Data
@TableName(value = "ent_wy_mortgagedebtlist")
public class EntWyMortgagedebt extends BaseDomain {
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
     * 被担保债券种类
     */
    @TableField(value = "mabdebttype")
    @JSONField(name = "mabDebtType")
    private String mabDebtType;

    /**
     * 被担保债权数额(万元)
     */
    @TableField(value = "mabdebtamount")
    @JSONField(name = "mabDebtAmount")
    private String mabDebtAmount;

    /**
     * 履行债务开始日期
     */
    @TableField(value = "debtstartdate")
    @JSONField(name = "debtStartDate")
    private String debtStartDate;

    /**
     * 履行债务结束日期
     */
    @TableField(value = "debtenddate")
    @JSONField(name = "debtEndDate")
    private String debtEndDate;

    /**
     * 担保范围
     */
    @TableField(value = "mabdebtrange")
    @JSONField(name = "mabDebtRange")
    private String mabDebtRange;

    /**
     * 备注
     */
    @TableField(value = "mabdebtremark")
    @JSONField(name = "mabDebtRemark")
    private String mabDebtRemark;
}
