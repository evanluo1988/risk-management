package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper=true)
@Accessors(chain = true)
@TableName("task_checks")
public class TaskCheck extends BaseDomain {
    private static final long serialVersionUID = 8846340164072346603L;
    /**
     * 任务ID
     */
    private Long taskId;
    /**
     * 企业ID
     */
    private Long enterpriseId;
    /**
     * 核查地区
     */
    private String checkRegion;
    /**
     * 相关线索
     */
    private String relatedClues;
    /**
     * 核查状态
     */
    private String checkStatus;
    /**
     * 是否逾期
     */
    private Boolean overdue;
    /**
     * 分发状态
     */
    private String assignment;
    /**
     * 分发区域
     */
    private Long areaId;
    private String enable;
    /**
     * 逾期时间
     */
    private LocalDate expireTime;
}
