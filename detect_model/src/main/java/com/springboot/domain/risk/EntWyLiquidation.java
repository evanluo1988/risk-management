package com.springboot.domain.risk;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(exclude = {"id","createTime","createBy","updateTime","updateBy","reqId","businessId"})
@TableName("ent_wy_liquidationlist")
public class EntWyLiquidation extends BaseDomain {
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
     * 清算负责人
     */
    @TableField(value = "liqprincipal")
    @JSONField(name = "liqPrincipal")
    private String liqPrincipal;
    /**
     * 清算组成员
     */
    @TableField(value = "liqmen")
    @JSONField(name = "liqMen")
    private String liqMen;
    /**
     * 清算完结情况
     */
    @TableField(value = "liqsituation")
    @JSONField(name = "liqSituation")
    private String liqSituation;
    /**
     * 清算完结日期
     */
    @TableField(value = "liqenddate")
    @JSONField(name = "liqEndDate")
    private String liqEndDate;
    /**
     * 债务承接人
     */
    @TableField(value = "debttranee")
    @JSONField(name = "debtTranee")
    private String debtTranee;
    /**
     * 债权承接人
     */
    @TableField(value = "claimtranee")
    @JSONField(name = "claimTranee")
    private String claimTranee;
    /**
     * 地址
     */
    @TableField(value = "address")
    @JSONField(name = "address")
    private String address;
    /**
     * 联系电话
     */
    @TableField(value = "telephone")
    @JSONField(name = "telephone")
    private String telephone;
}
