package com.springboot.domain.risk;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

@Data
@TableName("ent_wy_mortgagebasiclist")
public class EntWyMortgagebasic extends BaseDomain {
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
     * 被担保债权数额(万元)
     */
    @TableField(value = "mabguaramount")
    @JSONField(name = "mabGuarAmount")
    private String mabGuarAmount;
    /**
     * 公示日期
     */
    @TableField(value = "mabpubdate")
    @JSONField(name = "mabpubDate")
    private String mabpubDate;
    /**
     * 状态
     */
    @TableField(value = "mabstatus")
    @JSONField(name = "mabStatus")
    private String mabStatus;
}
