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
 * @Date 2020/12/15 14:34
 * @Version 1.0
 */
@Data
@ToString
@EqualsAndHashCode(exclude = {"id","createTime","createBy","updateTime","updateBy","reqId","businessId"})
@TableName(value = "ent_wy_stockpawnlist")
public class EntWyStockpawn extends BaseDomain {

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
    @TableField(value = "stkpawnregno")
    @JSONField(name = "stkPawnRegNo")
    private String stkPawnRegNo;

    /**
     * 出质人
     */
    @TableField(value = "stkpawnczper")
    @JSONField(name = "stkPawnCzPer")
    private String stkPawnCzPer;

    /**
     * 出质人证件/证件号
     */
    @TableField(value = "stkpawnczcerno")
    @JSONField(name = "stkPawnCzCerNo")
    private String stkPawnCzCerNo;

    /**
     * 出质股权数额
     */
    @TableField(value = "stkpawnczamount")
    @JSONField(name = "stkPawnCzAmount")
    private String stkPawnCzAmount;

    /**
     * 质权人姓名
     */
    @TableField(value = "stkpawnzqper")
    @JSONField(name = "stkPawnZqPer")
    private String stkPawnZqPer;

    /**
     * 质权人证件/证件号
     */
    @TableField(value = "stkpawnzqcerno")
    @JSONField(name = "stkPawnZqCerNo")
    private String stkPawnZqCerNo;

    /**
     * 质权出质设立登记日期
     */
    @TableField(value = "stkpawnregdate")
    @JSONField(name = "stkPawnRegDate")
    private String stkPawnRegDate;

    /**
     * 状态
     */
    @TableField(value = "stkpawnstatus")
    @JSONField(name = "stkPawnStatus")
    private String stkPawnStatus;

    /**
     * 公示日期
     */
    @TableField(value = "stkpawnpubdate")
    @JSONField(name = "stkPawnPubDate")
    private String stkPawnPubDate;

    /**
     * 关联内容
     */
    @TableField(value = "reldetails")
    @JSONField(name = "relDetails")
    private String relDetails;
}
