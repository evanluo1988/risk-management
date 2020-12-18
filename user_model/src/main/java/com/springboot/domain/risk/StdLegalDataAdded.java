package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

@Data
@TableName("std_legal_data_added")
public class StdLegalDataAdded extends BaseDomain {
    @TableField(value = "req_id")
    private String reqId;
    @TableField(value = "serialno")
    private String serialNo;
    @TableField(value = "entname")
    private String entName;
    @TableField(value = "varname")
    private String varName;
    @TableField(value = "varlabel")
    private String varLabel;
    @TableField(value = "legalvalue")
    private String legalValue;
    @TableField(value = "valuelabel")
    private String valueLabel;
    @TableField(value = "needingverify")
    private String needingVerify;

    private String flag;
}
