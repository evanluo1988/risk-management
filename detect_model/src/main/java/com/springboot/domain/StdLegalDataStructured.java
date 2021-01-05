package com.springboot.domain;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@TableName("std_legal_data_structured")
public class StdLegalDataStructured extends BaseDomain {
    @TableField(value = "req_id")
    private String reqId;
    @TableField(value = "businessid")
    private String businessId;
    @TableField(value = "serialno")
    private String serialno;
    @TableField(value = "entname")
    private String entName;
    @TableField(value = "orgcode")
    private String orgCode;
    @TableField(value = "ptype")
    private String ptype;
    @TableField(value = "caseno")
    private String caseNo;
    @TableField(value = "casereason")
    private String caseReason;
    @TableField(value = "docuclass")
    private String docuClass;
    @TableField(value = "target")
    private String target;
    @TableField(value = "targettype")
    private String targetType;
    @TableField(value = "pdesc")
    private String pdesc;
    @TableField(value = "plaintiff")
    private String plaintiff;
    @TableField(value = "party")
    private String party;
    @TableField(value = "judgementresult")
    private String judgementResult;
    @TableField(value = "pdate")
    private LocalDate pdate;
    @TableField(value = "casedate")
    private LocalDate caseDate;
    @TableField(value = "court")
    private String court;
    @TableField(value = "department")
    private String department;
    @TableField(value = "winstaff")
    private String winStaff;
    @TableField(value = "importstaff")
    private String importStaff;
    @TableField(value = "agent")
    private String agent;
    @TableField(value = "paybank")
    private String payBank;
    @TableField(value = "payee")
    private String payee;
    @TableField(value = "targetamount")
    private BigDecimal targetAmount;
    @TableField(value = "telno")
    private String telno;
    @TableField(value = "endorser")
    private String endorser;
    @TableField(value = "holder")
    private String holder;
    @TableField(value = "tickettime")
    private LocalDate ticketTime;
    @TableField(value = "expirationdate")
    private LocalDate expirationDate;
    @TableField(value = "billnumer")
    private String billnumer;
    @TableField(value = "province")
    private String province;
    @TableField(value = "city")
    private String city;
    @TableField(value = "secretary")
    private String secretary;
    @TableField(value = "chiefjudge")
    private String chiefJudge;
    @TableField(value = "judge")
    private String judge;
    @TableField(value = "lawstatus")
    private String lawstatus;
    @TableField(value = "datasource")
    private String dataSource;
    @TableField(value = "collectiondate")
    private LocalDate collectionDate;
}
