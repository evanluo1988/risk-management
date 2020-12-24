package com.springboot.vo.risk;

import com.springboot.util.StrUtils;
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
     * 行业
     */
    private String industry;
    /**
     * 注销日期
     */
    private String canDate;
    /**
     * 经营期限自
     */
    private String openFrom;
    /**
     * 吊销日期
     */
    private String revDate;
    /**
     * 工商注册号
     */
    private String regNo;
    /**
     * 员工人数
     */
    private String empNum;
    /**
     * 统一社会信用代码
     */
    private String creditCode;
    /**
     * 经营期限至
     */
    private String openTo;
    /**
     * 注册地址
     */
    private String address;
    /**
     * 组织机构代码
     */
    private String orgCode;
    /**
     * 登记机关
     */
    private String regOrg;
    /**
     * 经营范围
     */
    private String operateScope;


    public String getRegCap() {
        return StrUtils.getMoneyText(regCap, regCapCur);
    }

    public String getRecCap() {
        return StrUtils.getMoneyText(recCap, regCapCur);
    }

    public String getCanDate() {
        return StrUtils.getDataStr(canDate);
    }

    public String getOpenFrom() {
        return StrUtils.getDataStr(openFrom);
    }

    public String getRevDate() {
        return StrUtils.getDataStr(revDate);
    }

    public String getOpenTo() {
        return StrUtils.getDataStr(openTo);
    }
}
