package com.springboot.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@TableName("eds_gs_xzcf")
public class EdsGsXzcf extends BaseDomain {
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
     * todo 案发时间
     */
    @TableField(value = "casetime")
    @JSONField(name = "")
    private String caseTime;
    /**
     * todo 案由
     */
    @TableField(value = "")
    @JSONField(name = "")
    private String caseReason;
    /**
     * todo 案件类型
     */
    @TableField(value = "casetype")
    @JSONField(name = "")
    private String caseType;
    /**
     * 执行类别
     */
    @TableField(value = "exesort")
    @JSONField(name = "")
    private String exeSort;
    /**
     * 案件结果
     */
    @TableField(value = "caseresult")
    @JSONField(name= "")
    private String caseResult;
    /**
     *  处罚决定书签发日期
     */
    @TableField(value = "pendecissdate")
    @JSONField(name = "penDecissDate")
    private String penDecissDate;
    /**
     * 处罚机关
     */
    @TableField(value = "penauth")
    @JSONField(name = "penAuth")
    private String penAuth;
    /**
     * 主要违法事实
     */
    @TableField(value = "illegfact")
    @JSONField(name = "illegFact")
    private String illegFact;
    /**
     * 处罚依据
     */
    @TableField(value = "penbasis")
    @JSONField(name = "")
    private String penBasis;
    /**
     * 处罚种类
     */
    @TableField(value = "pentype")
    @JSONField(name = "penType")
    private String penType;
    /**
     * 处罚结果
     */
    @TableField(value = "penresult")
    @JSONField(name = "penResult")
    private String penResult;
    /**
     * 处罚金额
     */
    @TableField(value = "penam")
    @JSONField(name = "")
    private String penAm;
    /**
     * 处罚执行情况
     */
    @TableField(value = "penexest")
    @JSONField(name = "")
    private String penExest;
    /**
     * 处罚决定书文号
     */
    @TableField(value = "pendecno")
    @JSONField(name = "penDecNo")
    private String penDecNo;
}
