package com.springboot.domain.risk;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@TableName("legal_wy_sxbzxr_com")
public class LegalWySxbzxrCom extends BaseDomain {
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
     * 编号
     */
    @JSONField(name = "ID")
    @TableField(value = "iid")
    private String iid;
    /**
     * 企业名称
     */
    @JSONField(name = "INAME")
    @TableField(value = "iname")
    private String iname;
    /**
     * 案号
     */
    @JSONField(name = "CASECODE")
    @TableField(value = "casecode")
    private String caseCode;
    /**
     * 身份证号码/组织机构代码
     */
    @JSONField(name = "CARDNUM")
    @TableField(value = "cardnum")
    private String cardNum;
    /**
     * 执行法院
     */
    @JSONField(name = "COURTNAME")
    @TableField(value = "courtname")
    private String courtName;
    /**
     * 省份
     */
    @JSONField(name = "AREANAME")
    @TableField(value = "areaname")
    private String areaName;
    /**
     * 执行依据文号
     */
    @JSONField(name = "GISTID")
    @TableField(value = "gistid")
    private String gistId;
    /**
     * 立案时间
     */
    @JSONField(name = "REGDATE")
    @TableField(value = "regdate")
    private String regDate;
    /**
     * 做出执行依据单位
     */
    @JSONField(name = "GISTUNIT")
    @TableField(value = "gistUnit")
    private String gistUnit;
    /**
     * 被执行人的履行情况
     */
    @JSONField(name = "PERFORMANCE")
    @TableField(value = "performance")
    private String performance;
    /**
     * 失信被执行人行为具体情形
     */
    @JSONField(name = "DISRUPTTYPENAME")
    @TableField(value = "disrupttypename")
    private String disruptTypeName;
    /**
     * 发布时间
     */
    @JSONField(name = "PUBLISHDATE")
    @TableField(value = "publishdate")
    private String publishDate;
    /**
     * 失信信息详情
     */
    @JSONField(name = "DUTY")
    @TableField(value = "duty")
    private String duty;
    /**
     * 首次采集日期
     */
    @JSONField(name = "INPUTTIME")
    @TableField(value = "inputtime")
    private String inputTime;
    /**
     * 最新更新日期
     */
    @JSONField(name = "UPDATETIME")
    @TableField(value = "updatetime")
    private String thirdUpdateTime;
}
