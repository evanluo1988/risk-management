package com.springboot.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/20 17:35
 */
@Data
public class TaskInfoVo {
    private Long id;
    /**
     * 到期时间
     */
    private LocalDateTime dueTime;
    /**
     * 任务编号
     */
    private String taskNumber;
    /**
     * 发起时间
     */
    private LocalDateTime startTime;
    /**
     * 任务名称
     */
    private String taskName;
}
