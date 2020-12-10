package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

@Data
@TableName("eds_gs_qyzyglry")
public class EdsGsQyzyglry extends BaseDomain {
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
     * 姓名
     */
    @TableField(value = "name")
    private String name;
    /**
     * 职务
     */
    @TableField(value = "position")
    private String position;
    /**
     * 性别
     */
    @TableField(value = "sex")
    private String sex;
}
