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
@TableName("ent_wy_shareholderlist")
public class EntWyShareholder extends BaseDomain {
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
     * 股东名称
     */
    @TableField(value = "shareholdername")
    @JSONField(name = "shareholderName")
    private String shareholderName;
    /**
     * 股东类型
     */
    @TableField(value = "shareholdertype")
    @JSONField(name = "shareholderType")
    private String shareholderType;
    /**
     * 认缴出资额（单位：万元）
     */
    @TableField(value = "subconam")
    @JSONField(name = "subConAm")
    private String subConAm;
    /**
     * 币种
     */
    @TableField(value = "subconcur")
    @JSONField(name = "subConCur")
    private String subConCur;
    /**
     * 币种
     */
    @TableField(value = "subconform")
    @JSONField(name = "subConForm")
    private String subConForm;
    /**
     * 出资比例
     */
    @TableField(value = "fundedratio")
    @JSONField(name = "fundedRatio")
    private String fundedRatio;
    /**
     * 认缴出资日期
     */
    @TableField(value = "subcondate")
    @JSONField(name = "subConDate")
    private String subConDate;
    /**
     * 国别
     */
    @TableField(value = "country")
    @JSONField(name = "country")
    private String country;
}
