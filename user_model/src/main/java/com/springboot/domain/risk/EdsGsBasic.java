package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

@Data
@TableName("eds_gs_basic")
public class EdsGsBasic extends BaseDomain {
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
     * 企业名称
     */
    @TableField(value = "enterprisename")
    private String enterpriseName;
    /**
     * 统一社会信用代码
     */
    @TableField(value = "creditcode")
    private String creditCode;
    /**
     * 组织机构代码
     */
    @TableField(value = "orgcode")
    private String orgCode;
    /**
     * 法人姓名
     */
    @TableField(value = "frname")
    private String frName;
    /**
     * 工商注册号
     */
    @TableField(value = "regno")
    private String regNo;
    /**
     * 注册资金（单位：万元）
     */
    @TableField(value = "regcap")
    private String regCap;
    /**
     * 注册币种
     */
    @TableField(value = "regcapcur")
    private String regCapCur;
    /**
     * 开业日期（YYYY-MM-DD）
     */
    @TableField(value = "esdate")
    private String esDate;
    /**
     * 经营期限自（YYYY-MM-DD）
     */
    @TableField(value = "openfrom")
    private String openFrom;
    /**
     * 经营期限至（YYYY-MM-DD）
     */
    @TableField(value = "opento")
    private String openTo;
    /**
     * 企业（机构）类型
     */
    @TableField(value = "enterprisetype")
    private String enterpriseType;
    /**
     * 经营状态（在营、注销、吊销、其他）
     */
    @TableField(value = "enterprisestatus")
    private String enterpriseStatus;
    /**
     * 注销日期
     */
    @TableField(value = "canceldate")
    private String cancelDate;
    /**
     * 吊销日期
     */
    @TableField(value = "revokedate")
    private String revokeDate;
    /**
     * 注册地址
     */
    @TableField(value = "address")
    private String address;
    /**
     * 许可经营项目
     */
    @TableField(value = "abuitem")
    private String abuItem;
    /**
     * 一般经营项目
     */
    @TableField(value = "cbuitem")
    private String cbuItem;
    /**
     * 经营（业务）范围
     */
    @TableField(value = "operatescope")
    private String operateScope;
    /**
     * 经营(业务)范围及方式
     */
    @TableField(value = "operatescopeandform")
    private String operateScopeAndForm;
    /**
     * 登记机关
     */
    @TableField(value = "regorg")
    private String regOrg;
    /**
     * 最后年检年度
     */
    @TableField(value = "ancheyear")
    private String anCheYear;
    /**
     * 最后年检日期
     */
    @TableField(value = "anchedate")
    private String anCheDate;
    /**
     * 行业门类代码
     */
    @TableField(value = "industryphycode")
    private String industryPhyCode;
    /**
     * 行业门类名称
     */
    @TableField(value = "industryphyname")
    private String industryPhyName;
    /**
     * 国民经济行业代码
     */
    @TableField(value = "industrycode")
    private String industryCode;
    /**
     * 国民经济行业名称
     */
    @TableField(value = "industryname")
    private String industryName;
    /**
     * 实收资本
     */
    @TableField(value = "reccap")
    private String recCap;
    /**
     * 原注册号
     */
    @TableField(value = "oriregno")
    private String oriRegNo;
    /**
     * 核准时间
     */
    @TableField(value = "apprdate")
    private String apprDate;

}
