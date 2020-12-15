package com.springboot.domain.risk;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

@Data
@TableName("ent_wy_basiclist")
public class EntWyBasic extends BaseDomain {
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
    @JSONField(name = "enterpriseName")
    private String enterpriseName;
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
     * 企业（机构）类型
     */
    @TableField(value = "enterprisetype")
    @JSONField(name = "enterpriseType")
    private String enterpriseType;
    /**
     * 法人姓名
     */
    @TableField(value = "frname")
    @JSONField(name = "frName")
    private String frName;
    /**
     * 注册资金（单位：万元）
     */
    @TableField(value = "regcap")
    @JSONField(name = "regCap")
    private String regCap;
    /**
     * 注册币种
     */
    @TableField(value = "regcapcur")
    @JSONField(name = "regCapCur")
    private String regCapCur;
    /**
     * 实收资本
     */
    @TableField(value = "reccap")
    @JSONField(name = "recCap")
    private String recCap;
    /**
     * 开业日期（YYYY-MM-DD）
     */
    @TableField(value = "esdate")
    @JSONField(name = "esDate")
    private String esDate;
    /**
     * 经营期限自（YYYY-MM-DD）
     */
    @TableField(value = "openfrom")
    @JSONField(name = "openFrom")
    private String openFrom;
    /**
     * 经营期限至（YYYY-MM-DD）
     */
    @TableField(value = "opento")
    @JSONField(name = "openTo")
    private String openTo;
    /**
     * 登记机关
     */
    @TableField(value = "regorg")
    @JSONField(name = "regOrg")
    private String regOrg;
    /**
     * 核准时间
     */
    @TableField(value = "apprdate")
    @JSONField(name = "apprDate")
    private String apprDate;
    /**
     * 经营状态（在营、注销、吊销、其他）
     */
    @TableField(value = "enterprisestatus")
    @JSONField(name = "enterpriseStatus")
    private String enterpriseStatus;
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
     * 注册地址
     */
    @TableField(value = "address")
    @JSONField(name = "address")
    private String address;
    /**
     * 经营（业务）范围
     */
    @TableField(value = "operatescope")
    @JSONField(name = "operateScope")
    private String operateScope;
    /**
     * 许可经营项目
     */
    @TableField(value = "abuitem")
    @JSONField(name = "abuItem")
    private String abuItem;
    /**
     * 国民经济行业代码
     */
    @TableField(value = "industrycode")
    @JSONField(name = "industryCode")
    private String industryCode;
    /**
     * 国民经济行业名称
     */
    @TableField(value = "industryname")
    @JSONField(name = "industryName")
    private String industryName;
    /**
     * 行业门类代码
     */
    @TableField(value = "industryphycode")
    @JSONField(name = "industryPhyCode")
    private String industryPhyCode;
    /**
     * 行业门类名称
     */
    @TableField(value = "industryphyname")
    @JSONField(name = "industryPhyName")
    private String industryPhyName;
    /**
     * 最后年检年度
     */
    @TableField(value = "ancheyear")
    @JSONField(name = "anCheYear")
    private String anCheYear;
    /**
     * 原注册号
     */
    @TableField(value = "oriregno")
    @JSONField(name = "oriRegNo")
    private String oriRegNo;
    /**
     * 联系人电话
     */
    @TableField(value = "telephone")
    @JSONField(name = "telephone")
    private String telephone;
    /**
     * 住所所在行政区划
     */
    @TableField(value = "addrdistrict")
    @JSONField(name = "addrDistrict")
    private String addrDistrict;
    /**
     * 曾用名
     */
    @TableField(value = "enterprisehisname")
    @JSONField(name = "enterpriseHisName")
    private String enterpriseHisName;
    /**
     * 所在省份
     */
    @TableField(value = "regorgprovince")
    @JSONField(name = "regOrgProvince")
    private String regOrgProvince;
    /**
     * 所在城市
     */
    @TableField(value = "regorgcity")
    @JSONField(name = "regOrgCity")
    private String regOrgCity;
    /**
     * 所在区/县
     */
    @TableField(value = "regorgdistrict")
    @JSONField(name = "regOrgDistrict")
    private String regOrgDistrict;
    /**
     * 电子邮箱
     */
    @TableField(value = "email")
    @JSONField(name = "email")
    private String email;
    /**
     * 员工人数
     */
    @TableField(value = "empnum")
    @JSONField(name = "empNum")
    private String empNum;
    /**
     * 注册地址行政编号
     */
    @TableField(value = "regorgcode")
    @JSONField(name = "regOrgCode")
    private String regOrgCode;
    /**
     * 企业(机构)类型编码
     */
    @TableField(value = "enterprisetypecode")
    @JSONField(name = "enterpriseTypeCode")
    private String enterpriseTypeCode;
    /**
     * 企业英文名称
     */
    @TableField(value = "enterpriseengname")
    @JSONField(name = "enterpriseEngName")
    private String enterpriseEngName;

}
