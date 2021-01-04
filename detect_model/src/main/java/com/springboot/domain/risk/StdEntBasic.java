package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("std_ent_basic_list")
public class StdEntBasic extends BaseDomain {
    @TableField(value = "req_id")
    private String reqId;
    /**
     * 企业名称
     */
    @TableField(value = "entname")
    private String entName;
    /**
     * 统一社会信用代码
     */
    @TableField(value = "creditcode")
    private String creditCode;
    /**
     * 工商注册号
     */
    @TableField(value = "regno")
    private String regNo;
    /**
     * 企业（机构）类型
     */
    @TableField(value = "enttype")
    private String entType;
    /**
     * 法定代表人
     */
    @TableField(value = "lrname")
    private String lrName;
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
     * 实收资本
     */
    @TableField(value = "reccap")
    private String recCap;
    /**
     * 开业日期（YYYY-MM-DD）
     */
    @TableField(value = "esdate")
    private LocalDate esDate;
    /**
     * 经营期限自（YYYY-MM-DD）
     */
    @TableField(value = "openfrom")
    private LocalDate openFrom;
    /**
     * 经营期限至（YYYY-MM-DD）
     */
    @TableField(value = "opento")
    private String openTo;
    /**
     * 登记机关
     */
    @TableField(value = "regorg")
    private String regOrg;
    /**
     * 核准时间
     */
    @TableField(value = "apprdate")
    private LocalDate apprDate;
    /**
     * 登记状态
     */
    @TableField(value = "regstatus")
    private String regStatus;
    /**
     * 住所
     */
    @TableField(value = "address")
    private String address;
    /**
     * 许可经营项目
     */
    @TableField(value = "abuitem")
    private String abuItem;
    /**
     * 经营范围
     */
    @TableField(value = "operatescope")
    private String operateScope;
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
     * 注销日期
     */
    @TableField(value = "candate")
    private LocalDate canDate;
    /**
     * 吊销日期
     */
    @TableField(value = "revdate")
    private LocalDate revDate;
    /**
     * 最后年检年度
     */
    @TableField(value = "ancheyear")
    private String ancheYear;
    /**
     * 原注册号
     */
    @TableField(value = "oriregno")
    private String oriregNo;
    /**
     * 企业英文名称
     */
    @TableField(value = "entnameeng")
    private String entNameEng;
    /**
     * 所在省份
     */
    @TableField(value = "regorgprovince")
    private String regOrgProvince;
    /**
     * 所在城市
     */
    @TableField(value = "regorgcity")
    private String regOrgCity;
    /**
     * 所在区/县
     */
    @TableField(value = "regorgdistrict")
    private String regOrgDistrict;
    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;
    /**
     * 联系人电话
     */
    @TableField(value = "tel")
    private String tel;
    /**
     * 员工人数
     */
    @TableField(value = "empnum")
    private String empNum;
    /**
     * 住所所在行政区划
     */
    @TableField(value = "domdistrict")
    private String domDistrict;
    /**
     * 注册地址行政编号
     */
    @TableField(value = "regorgcode")
    private String regOrgCode;
    /**
     * 曾用名
     */
    @TableField(value = "entnameold")
    private String entNameOld;
    /**
     * 企业(机构)类型编码
     */
    @TableField(value = "enttypecode")
    private String entTypeCode;

}
