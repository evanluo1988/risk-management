package com.springboot.domain.risk;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@TableName("legal_wy_ssjghsj")
public class LegalWySsjghsj extends BaseDomain {
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
     * 序列号
     */
    @JSONField(name = "SERIALNO")
    @TableField(value = "serialno")
    private String serialno;
    /**
     * 公告类型
     */
    @JSONField(name = "PTYPE")
    @TableField(value = "ptype")
    private String ptype;
    /**
     * 公告法院/裁决机构/委托法院
     */
    @JSONField(name = "COURT")
    @TableField(value = "court")
    private String court;
    /**
     * 案件标题/标的名称
     */
    @JSONField(name = "TARGET")
    @TableField(value = "target")
    private String target;
    /**
     * 公告内容
     */
    @JSONField(name = "PDESC")
    @TableField(value = "pdesc")
    private String pdesc;
    /**
     * 发布时间
     */
    @JSONField(name = "PDATE")
    @TableField(value = "pdate")
    private String pdate;
    /**
     * 数据采集日期
     */
    @JSONField(name = "COLLECTIONDATE")
    @TableField(value = "collectiondate")
    private String collectionDate;
    /**
     * 数据来源
     */
    @JSONField(name = "DATASOURCE")
    @TableField(value = "datasource")
    private String datasource;
    /**
     * 案号
     */
    @JSONField(name = "CASENO")
    @TableField(value = "caseno")
    private String caseNo;
    /**
     * 立案时间
     */
    @JSONField(name = "CASEDATE")
    @TableField(value = "casedate")
    private String caseDate;
    /**
     * 代理人/付款方
     */
    @JSONField(name = "AGENT")
    @TableField(value = "agent")
    private String agent;
    /**
     * 案件性质
     */
    @JSONField(name = "DOCUCLASS")
    @TableField(value = "docuclass")
    private String docuclass;
    /**
     * 标的类型
     */
    @JSONField(name = "TARGETTYPE")
    @TableField(value = "targetType")
    private String targetType;
    /**
     * 参考价格/金额/执行标的金额/清算金额
     */
    @JSONField(name = "TARGETAMOUNT")
    @TableField(value = "targetamount")
    private String targetAmount;
    /**
     * 联系方式
     */
    @JSONField(name = "TELNO")
    @TableField(value = "telno")
    private String telno;
    /**
     * 省份
     */
    @JSONField(name = "PROVINCE")
    @TableField(value = "province")
    private String province;
    /**
     * 城市
     */
    @JSONField(name = "CITY")
    @TableField(value = "city")
    private String city;
    /**
     * 案由
     */
    @JSONField(name = "CASEREASON")
    @TableField(value = "casereason")
    private String caseReason;
    /**
     * 付款方
     */
    @JSONField(name = "PAYBANK")
    @TableField(value = "paybank")
    private String payBank;
    /**
     * 收款方
     */
    @JSONField(name = "PAYEE")
    @TableField(value = "payee")
    private String payee;
    /**
     * 非基本当事人
     */
    @JSONField(name = "ENDORSER")
    @TableField(value = "endorser")
    private String endorser;
    /**
     * 持票人
     */
    @JSONField(name = "HOLDER")
    @TableField(value = "holder")
    private String holder;
    /**
     * 出票日期
     */
    @JSONField(name = "TICKETTIME")
    @TableField(value = "tickettime")
    private String ticketTime;
    /**
     * 票据号
     */
    @JSONField(name = "BILLNUMER")
    @TableField(value = "billnumer")
    private String billnumer;
    /**
     * 到期日期
     */
    @JSONField(name = "EXPIRATIONDATE")
    @TableField(value = "expirationdate")
    private String expirationDate;
    /**
     * 案件承办部门
     */
    @JSONField(name = "DEPARTMENT")
    @TableField(value = "department")
    private String department;
    /**
     * 书记员
     */
    @JSONField(name = "SECRETARY")
    @TableField(value = "secretary")
    private String secretary;
    /**
     * 审判长
     */
    @JSONField(name = "CHIEFJUDGE")
    @TableField(value = "chiefjudge")
    private String chiefJudge;
    /**
     * 审判员
     */
    @JSONField(name = "JUDGE")
    @TableField(value = "judge")
    private String judge;
    /**
     * 企业名称
     */
    @JSONField(name = "ENTNAME")
    @TableField(value = "entname")
    private String entName;
    /**
     * 当事人/破产人/票据申请人/ 送达人/被执行人/被告
     */
    @JSONField(name = "PARTY")
    @TableField(value = "party")
    private String party;
    /**
     * 申请人/出票人/申请执行人/原告/公示人
     */
    @JSONField(name = "PLAINTIFF")
    @TableField(value = "plaintiff")
    private String plaintiff;
    /**
     * 判决结果
     */
    @JSONField(name = "JUDGEMENTRESULT")
    @TableField(value = "judgementresult")
    private String judgementResult;
    /**
     * 胜诉方
     */
    @JSONField(name = "WINSTAFF")
    @TableField(value = "winstaff")
    private String winStaff;
    /**
     * 重要第三方
     */
    @JSONField(name = "IMPORTSTAFF")
    @TableField(value = "importstaff")
    private String importStaff;
    /**
     * 诉讼地位
     */
    @JSONField(name = "LAWSTATUS")
    @TableField(value = "lawstatus")
    private String lawStatus;
}
