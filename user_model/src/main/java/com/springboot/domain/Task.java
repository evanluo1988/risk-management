package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("tasks")
public class Task extends BaseDomain {

    private static final long serialVersionUID = -6444412657849554771L;
    /**
     * 任务编号
     */
    private String taskNumber;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 发起时间
     */
    private LocalDateTime startTime;
    /**
     * 发起单位
     */
    private String startUnit;
    /**
     * 任务来源
     */
    private String taskSource;
    /**
     * 到期时间
     */
    private LocalDateTime dueTime;
}
