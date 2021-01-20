package com.springboot.vo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/20 16:22
 */
@Data
public class InformInfoVo {
    private Long id;
    /**
     * 举报来源
     */
    private String source;
    /**
     * 举报时间
     */
    private LocalDateTime informTime;
    /**
     * 被举报对象名称
     */
    private String informName;
    /**
     * 被举报类型
     */
    private String informType;
    /**
     * 案发时间
     */
    private LocalDateTime crimeTime;
    /**
     * 案发地（区）
     */
    private String crimeRegion;
    /**
     * 举报具体内容
     */
    private String informContent;
    /**
     * 涉及金额
     */
    private String involvedAmount;
    /**
     * 核查状态
     */
    private String checkStatus;
    /**
     * 核查单位
     */
    private Long areaId;
    private String areaName;
}
