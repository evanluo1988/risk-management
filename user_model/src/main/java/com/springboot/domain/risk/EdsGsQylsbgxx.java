package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

@Data
@TableName("eds_gs_qylsbgxx")
public class EdsGsQylsbgxx extends BaseDomain {
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
     * 变更日期
     */
    @TableField(value = "altdate")
    private String altDate;
    /**
     * 变更事项
     */
    @TableField(value = "altitem")
    private String altItem;
    /**
     * 变更前内容
     */
    @TableField(value = "altbe")
    private String altBe;
    /**
     * 变更后内容
     */
    @TableField(value = "altaf")
    private String altAf;

}
