package com.springboot.domain.risk;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

@Data
@TableName("eds_gs_qsxx")
public class EdsGsQsxx extends BaseDomain {
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
     * 清算责任人
     */
    @TableField(value = "ligprincipal")
    @JSONField(name = "liqPrincipal")
    private String liqPrincipal;
    /**
     * 清算组成员
     */
    @TableField(value = "ligmen")
    @JSONField(name="liqMen")
    private String ligMen;
    /**
     * 清算完结情况
     */
    @TableField(value = "ligst")
    @JSONField(name= "liqSituation")
    private String ligst;
    /**
     * 清算完结日期
     */
    @TableField(value = "ligenddate")
    @JSONField(name = "liqEndDate")
    private String ligEndDate;
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

}
