package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@TableName("cloud_query_log")
public class CloudQueryLog extends BaseDomain {
    @TableField(value = "req_id")
    private String reqId;
    @TableField(value = "entname")
    private String entName;
    @TableField(value = "message")
    private String message;
}
