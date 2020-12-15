package com.springboot.domain.risk;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

@Data
@TableName("legal_wy_bzxr_com")
public class LegalWyBzxrCom extends BaseDomain {
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
     * 被执行人姓名/名称
     */
    @JSONField(name = "PNAME")
    @TableField(value = "pname")
    private String pname;
    /**
     * 案号
     */
    @JSONField(name = "CASECODE")
    @TableField(value = "casecode")
    private String caseCode;
    /**
     * 执行法院
     */
    @JSONField(name = "EXECCOURTNAME")
    @TableField(value = "execcourtname")
    private String execCourtName;
    /**
     * 执行标的
     */
    @JSONField(name = "EXECMONEY")
    @TableField(value = "execmoney")
    private String execMoney;
    /**
     * 身份证号码/组织机构代码
     */
    @JSONField(name = "PARTYCARDNUM")
    @TableField(value = "partycardnum")
    private String partyCardNum;
    /**
     * 立案时间
     */
    @JSONField(name = "CASECREATETIME")
    @TableField(value = "casecreatetime")
    private String caseCreateTime;
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
