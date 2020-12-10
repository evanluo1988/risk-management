package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

@Data
@TableName("eds_gs_fzjgxx")
public class EdsGsFzjgxx extends BaseDomain {
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
    private String brName;
    /**
     * 分支机构企业注册号
     */
    @TableField(value = "brregno")
    private String brRegNo;
}
