package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@TableName("eds_gs_dcdywxx")
public class EdsGsDcdywxx extends BaseDomain {
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
     * 抵押ID
     */
    @TableField(value = "morreg_id")
    private String morregId;
    /**
     * 抵押物名称
     */
    @TableField(value = "guaname")
    private String guaName;
    /**
     * 数量
     */
    @TableField(value = "quan")
    private String quan;
    /**
     * 价值（万元）
     */
    @TableField(value = "value")
    private String value;
}
