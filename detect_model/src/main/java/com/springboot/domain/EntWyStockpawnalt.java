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
 * @Date 2020/12/15 14:28
 * @Version 1.0
 */
@Data
@ToString
@EqualsAndHashCode(exclude = {"id","createTime","createBy","updateTime","updateBy","reqId","businessId"})
@TableName(value = "ent_wy_stockpawnaltlist")
public class EntWyStockpawnalt extends BaseDomain {

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
     * 关联内容
     */
    @TableField(value = "reldetails")
    @JSONField(name = "relDetails")
    private String relDetails;

    /**
     * 变更日期
     */
    @TableField(value = "stkpawnaltdate")
    @JSONField(name = "stkPawnAltDate")
    private String stkPawnAltDate;

    /**
     * 变更内容
     */
    @TableField(value = "stkpawnaltdetails")
    @JSONField(name = "stkPawnAltDetails")
    private String stkPawnAltDetails;
}
