package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@TableName("eds_gs_gqdjlsxx")
public class EdsGsGqdjlsxx extends BaseDomain {
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
     * 冻结文号
     */
    @TableField(value = "frodocno")
    private String froDocNo;
    /**
     * 冻结机关
     */
    @TableField(value = "froauth")
    private String froAuth;
    /**
     * 冻结起始日期
     */
    @TableField(value = "frofrom")
    private String froFrom;
    /**
     * 冻结截至日期
     */
    @TableField(value = "froto")
    private String froTo;
    /**
     * 冻结金额
     */
    @TableField(value = "froam")
    private String froAm;
    /**
     * 解冻机关
     */
    @TableField(value = "thawauth")
    private String thawAuth;
    /**
     * 解冻文号
     */
    @TableField(value = "thawdocno")
    private String thawDocNo;
    /**
     * 解冻日期
     */
    @TableField(value = "thawdate")
    private String thawDate;
    /**
     * 解冻说明
     */
    @TableField(value = "thawcomment")
    private String thawComment;
}
