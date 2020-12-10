package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

@Data
@TableName("eds_gs_fddbrzqtqyrzxx")
public class EdsGsFddbrzqtqyrzxx extends BaseDomain {
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
     * 法人姓名
     */
    @TableField(value = "frname")
    private String frName;
    /**
     * 企业 （机构）名称
     */
    @TableField(value = "entname")
    private String relEntName;
    /**
     * 统一社会信用代码(预留字段，无值)
     */
    @TableField(value = "creditcode")
    private String creditCode;
    /**
     * 注册号
     */
    @TableField(value = "regno")
    private String regNo;
    /**
     * 企业 （机构）类型
     */
    @TableField(value = "enttype")
    private String entType;
    /**
     * 注册资本
     */
    @TableField(value = "regcap")
    private String regCap;
    /**
     * 注册资本币种
     */
    @TableField(value = "regcapcur")
    private String regCapCur;
    /**
     * 企业状态
     */
    @TableField(value = "entstatus")
    private String entStatus;
    /**
     * 注销日期
     */
    @TableField(value = "candate")
    private String cancelDate;
    /**
     * 吊销日期
     */
    @TableField(value = "revdate")
    private String revokeDate;
    /**
     * 登记机关
     */
    @TableField(value = "regorg")
    private String regOrg;
    /**
     * 职务
     */
    @TableField(value = "position")
    private String position;
    /**
     * 是否法定代表人
     */
    @TableField(value = "lerepsign")
    private String isFrName;
    /**
     * 开业日期
     */
    @TableField(value = "esdate")
    private String esDate;
}
