package com.springboot.domain;

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
@TableName("ent_wy_mortgagereglist")
public class EntWyMortgagereg extends BaseDomain {
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
     * 登记日期
     */
    @TableField(value = "mabregdate")
    @JSONField(name = "mabRegDate")
    private String mabRegDate;
    /**
     * 登记机关
     */
    @TableField(value = "mabregorg")
    @JSONField(name = "mabRegOrg")
    private String mabRegOrg;
    /**
     * 被担保债券种类
     */
    @TableField(value = "mabguartype")
    @JSONField(name = "mabGuarType")
    private String mabGuarType;
    /**
     * 被担保债权数额(万元)
     */
    @TableField(value = "mabguaramount")
    @JSONField(name = "mabGuarAmount")
    private String mabGuarAmount;
    /**
     * 省份代码
     */
    @TableField(value = "mabprovcode")
    @JSONField(name = "mabProvCode")
    private String mabProvCode;
    /**
     * 担保范围
     */
    @TableField(value = "mabguarrange")
    @JSONField(name = "mabGuarRange")
    private String mabGuarRange;
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
     * 状态
     */
    @TableField(value = "mabstatus")
    @JSONField(name = "mabStatus")
    private String mabStatus;
}
