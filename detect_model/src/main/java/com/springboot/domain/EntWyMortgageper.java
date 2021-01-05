package com.springboot.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author 刘宏飞
 * @Date 2020/12/15 14:46
 * @Version 1.0
 */
@Data
@ToString
@EqualsAndHashCode(exclude = {"id","createTime","createBy","updateTime","updateBy","reqId","businessId"})
@TableName(value = "ent_wy_mortgageperlist")
public class EntWyMortgageper extends BaseDomain {
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
     * 抵押权人名称
     */
    @TableField(value = "mabpername")
    @JSONField(name = "mabPerName")
    private String mabPerName;

    /**
     * 抵押权人证照/证件类型
     */
    @TableField(value = "mabpercertype")
    @JSONField(name = "mabPerCerType")
    private String mabPerCerType;

    /**
     * 抵押权人证照/证件号码
     */
    @TableField(value = "mabpercerid")
    @JSONField(name = "mabPerCerId")
    private String mabPerCerId;

    /**
     * 所在地
     */
    @TableField(value = "mabperaddress")
    @JSONField(name = "mabPerAddress")
    private String mabPerAddress;
}
