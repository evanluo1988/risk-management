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
@TableName("ent_wy_mortgagepawnlist")
public class EntWyMortgagepawn extends BaseDomain {
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
     * 名称
     */
    @TableField(value = "mabpawnname")
    @JSONField(name = "mabPawnName")
    private String mabPawnName;
    /**
     * 所有权或使用权归属
     */
    @TableField(value = "mabpawnowner")
    @JSONField(name = "mabPawnOwner")
    private String mabPawnOwner;
    /**
     * 数量、质量、状况、所在地等情况
     */
    @TableField(value = "mabpawndetails")
    @JSONField(name = "mabPawnDetails")
    private String mabPawnDetails;
    /**
     * 备注
     */
    @TableField(value = "mabpawnremark")
    @JSONField(name = "mabPawnRemark")
    private String mabPawnRemark;
}
