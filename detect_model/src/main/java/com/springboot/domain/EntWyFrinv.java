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
@TableName("ent_wy_frinvlist")
public class EntWyFrinv extends BaseDomain {
    /**
     * 申请编号ID 由我方生成
     */
    @TableField(value = "req_id")
    private String reqId;
    /**
     * 统一社会信用代码
     */
    @TableField(value = "creditcode")
    @JSONField(name = "creditCode")
    private String creditCode;
    /**
     * 工商注册号
     */
    @TableField(value = "regno")
    @JSONField(name = "regNo")
    private String regNo;
    /**
     * 企业 （机构）类型
     */
    @TableField(value = "enttype")
    @JSONField(name = "entType")
    private String entType;
    /**
     * 法人姓名
     */
    @TableField(value = "frname")
    @JSONField(name = "frName")
    private String frName;
    /**
     * 注册资本
     */
    @TableField(value = "regcap")
    @JSONField(name = "regCap")
    private String regCap;
    /**
     * 注册资本币种
     */
    @TableField(value = "regcapcur")
    @JSONField(name = "regCapCur")
    private String regCapCur;
    /**
     *认缴出资额（万元）
     */
    @TableField(value = "subconam")
    @JSONField(name = "subConAm")
    private String subConAm;
    /**
     *认缴出资币种
     */
    @TableField(value = "subconcur")
    @JSONField(name = "subConCur")
    private String subConCur;
    /**
     *出资比例
     */
    @TableField(value = "fundedratio")
    @JSONField(name = "fundedRatio")
    private String fundedRatio;
    /**
     *出资方式
     */
    @TableField(value = "conform")
    @JSONField(name = "conForm")
    private String conForm;
    /**
     *开业日期
     */
    @TableField(value = "esdate")
    @JSONField(name = "esDate")
    private String esDate;
    /**
     *登记机关
     */
    @TableField(value = "regorg")
    @JSONField(name = "regOrg")
    private String regOrg;
    /**
     * 企业状态
     */
    @TableField(value = "entstatus")
    @JSONField(name = "entStatus")
    private String entStatus;
    /**
     * 注销日期
     */
    @TableField(value = "canceldate")
    @JSONField(name = "cancelDate")
    private String cancelDate;
    /**
     * 吊销日期
     */
    @TableField(value = "revokedate")
    @JSONField(name = "revokeDate")
    private String revokeDate;
    /**
     * 注册地址行政编号
     */
    @TableField(value = "regorgcode")
    @JSONField(name = "regOrgCode")
    private String regOrgCode;
    /**
     * 企业总数量
     */
    @TableField(value = "pinvamount")
    @JSONField(name = "pinvAmount")
    private String pinvAmount;
    /**
     * 企业 （机构）名称
     */
    @TableField(value = "relentname")
    @JSONField(name = "relEntName")
    private String relEntName;

}
