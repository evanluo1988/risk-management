package com.springboot.domain.risk;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

/**
 * @Author 刘宏飞
 * @Date 2020/12/15 14:22
 * @Version 1.0
 */
@Data
@TableName(value = "ent_wy_stockpawncanlist")
public class EntWyStockpawncan extends BaseDomain {

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
     * 注销日期
     */
    @TableField(value = "stkpawncandate")
    @JSONField(name = "stkPawnCanDate")
    private String stkPawnCanDate;

    /**
     * 注销原因
     */
    @TableField(value = "stkpawncanrreason")
    @JSONField(name = "stkPawnCanRreason")
    private String stkPawnCanRreason;
}
