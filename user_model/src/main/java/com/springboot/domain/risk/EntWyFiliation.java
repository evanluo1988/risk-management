package com.springboot.domain.risk;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

@Data
@TableName("ent_wy_filiationlist")
public class EntWyFiliation extends BaseDomain {
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
     * 分支机构名称
     */
    @TableField(value = "brname")
    @JSONField(name  ="brName")
    private String brName;
    /**
     * 分支机构统一社会信用代码
     */
    @TableField(value = "brcreditcode")
    @JSONField(name = "brCreditCode")
    private String brCreditCode;
    /**
     * 分支机构工商注册号
     */
    @TableField(value = "brregno")
    @JSONField(name = "brRegNo")
    private String brRegNo;
    /**
     * 分支机构登记机关
     */
    @TableField(value = "brregorg")
    @JSONField(name = "brRegOrg")
    private String brRegOrg;
}
