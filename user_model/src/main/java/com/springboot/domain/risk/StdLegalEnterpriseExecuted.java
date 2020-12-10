package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("std_legal_enterprise_executed")
public class StdLegalEnterpriseExecuted extends BaseDomain {
    @TableField(value = "req_id")
    private String reqId;
    @TableField(value = "businessid")
    private String businessid;
    @TableField(value = "serialno")
    private String serialNo;
    @TableField(value = "pname")
    private String pname;
    @TableField(value = "partycardnum")
    private String partyCardNum;
    @TableField(value = "casecode")
    private String caseCode;
    @TableField(value = "execcourtname")
    private String execCourtName;
    @TableField(value = "execmoney")
    private String execMoney;
    @TableField(value = "casecreatetime")
    private LocalDate caseCreateTime;
    @TableField(value = "inputtime")
    private LocalDate inputTime;
    @TableField(value = "updatetime")
    private LocalDate thirdUpdateTime;
}
