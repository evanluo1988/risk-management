package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("cloud_info_timeliness")
public class CloudInfoTimeliness extends BaseDomain {
    @TableField(value = "req_id")
    private String reqId;
    @TableField(value = "entname")
    private String entName;
    @TableField(value = "expire_time")
    private LocalDateTime expireTime;
    @TableField(value = "status")
    private String status;
}
