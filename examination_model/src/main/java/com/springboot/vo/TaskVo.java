package com.springboot.vo;

import com.springboot.config.DateFormatConfig;
import com.springboot.page.PageIn;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class TaskVo extends PageIn {
    /**
     * 处置阶段
     */
    private String disposalStage;
    /**
     * 发起时间
     */
    @DateTimeFormat(pattern = DateFormatConfig.DATE_FORMAT)
    private LocalDate taskTimeStart;
    @DateTimeFormat(pattern = DateFormatConfig.DATE_FORMAT)
    private LocalDate taskTimeEnd;
    /**
     * 是否逾期
     */
    private Boolean overdue;
    /**
     * 到期时间
     */
    @DateTimeFormat(pattern = DateFormatConfig.DATE_FORMAT)
    private LocalDate taskExpireStart;
    @DateTimeFormat(pattern = DateFormatConfig.DATE_FORMAT)
    private LocalDate taskExpireEnd;
    /**
     * 核查企业名称
     */
    private String enterpriseName;
    /**
     * 核查状态
     */
    private String checkStatus;
    /**
     * 分配状态
     */
    private String assignment;
    /**
     * 核查区域
     */
    private Long areaId;

}
