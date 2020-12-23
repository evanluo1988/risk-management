package com.springboot.vo.risk;

import lombok.Data;

/**
 * 企业基本信息
 */
@Data
public class EntBasicInformationVo {
    /**
     * 法定代表人
     */
    private String lrName;
    /**
     * 企业(机构)类型
     */
    private String entType;
    /**
     * 注册资本
     */
    private String regCap;
    /**
     * 币种
     */
    private String regCapCur;
    /**
     * 实缴资本
     */
    private String recCap;
    /**
     * 最后年检年度
     */
    private String ancheYear;
    /**
     * 成立日期
     */
    private String esDate;
    /**
     * 注销日期
     */
    private String canDate;
    /**
     * 经营状态
     */
    private String regStatus;
    /**
     * 吊销日期
     */
    private String revDate;
    /**
     * 行业
     */
    private String industry;
    /**
     * 员工人数
     */
    private String empNum;
    /**
     * 经营期限自
     */
    private String openFrom;
    /**
     * 经营期限至
     */
    private String openTo;
    /**
     * 工商注册号
     */
    private String regNo;
    /**
     * 统一社会信用代码
     */
    private String creditCode;
    /**
     * 组织机构代码
     */
    private String orgCode;
    /**
     * 注册地址
     */
    private String address;
    /**
     * 登记机关
     */
    private String regOrg;
    /**
     * 经营范围
     */
    private String operateScope;
}
