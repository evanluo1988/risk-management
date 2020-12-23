package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper=true)
@TableName("std_legal_ent_unexecuted")
public class StdLegalEntUnexecuted extends BaseDomain {
    @TableField(value = "req_id")
    private String reqId;
    @TableField(value = "businessid")
    private String businessId;
    @TableField(value = "iid")
    private String iid;
    @TableField(value = "iname")
    private String iname;
    @TableField(value = "cardnnum")
    private String cardnNum;
    @TableField(value = "casecode")
    private String caseCode;
    @TableField(value = "courtname")
    private String courtName;
    @TableField(value = "areaname")
    private String areaName;
    @TableField(value = "gistid")
    private String gistId;
    @TableField(value = "regdate")
    private LocalDate regDate;
    @TableField(value = "gistunit")
    private String gistUnit;
    @TableField(value = "performance")
    private String performance;
    @TableField(value = "disrupttypename")
    private String disruptTypeName;
    @TableField(value = "duty")
    private String duty;
    @TableField(value = "publishdate")
    private LocalDate publishDate;
    @TableField(value = "inputtime")
    private LocalDate inputTime;
    @TableField(value = "updatetime")
    private LocalDate thirdUpdateTime;
}
