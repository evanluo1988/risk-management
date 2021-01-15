package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("user_lock")
public class UserLock extends BaseDomain{
    @TableField(value = "login_name")
    private String loginName;
    @TableField(value = "lock_time")
    private LocalDateTime lockTime;
    @TableField(value = "login_count")
    private Integer loginCount;
}
