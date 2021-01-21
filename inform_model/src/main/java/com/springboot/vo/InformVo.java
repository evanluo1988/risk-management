package com.springboot.vo;

import com.springboot.config.DateFormatConfig;
import com.springboot.page.PageIn;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author 刘宏飞
 * @Date 2020/12/1 16:42
 * @Version 1.0
 */
@Data
public class InformVo extends PageIn {
    /**
     * 线索属实性审核
     */
    private String verification;
    /**
     * 举报查询开始时间
     */
    @DateTimeFormat(pattern = DateFormatConfig.DATE_FORMAT)
    private LocalDate informTimeStart;
    /**
     * 举报查询结束时间
     */
    @DateTimeFormat(pattern = DateFormatConfig.DATE_FORMAT)
    private LocalDate informTimeEnd;
    /**
     * 被举报对象名称
     */
    private String informName;
    /**
     * 是否逾期
     */
    private Boolean overdue;
    /**
     * 核查状态
     */
    private String checkStatus;
    /**
     * 奖励情况
     */
    private String rewardStatus;
    /**
     * 区域ID
     */
    private Long areaId;
    /**
     * 分派状态
     */
    private String assignment;
    /**
     * 核查开始时间
     */
    @DateTimeFormat(pattern = DateFormatConfig.DATE_FORMAT)
    private LocalDate checkTimeStart;
    /**
     * 核查结束时间
     */
    @DateTimeFormat(pattern = DateFormatConfig.DATE_FORMAT)
    private LocalDate checkTimeEnd;
}
