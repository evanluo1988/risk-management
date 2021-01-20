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
     * 线索编号
     */
    private String clueNumber;
    /**
     * 举报来源
     */
    private String source;
    /**
     * 是否实名举报，1为匿名，0为实名
     */
    private Boolean anonymous;

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
     * 分派区域ID
     */
    private Long areaId;

    private String areaName;

    /**
     * 案发时间
     */
    private LocalDateTime crimeTime;

    /**
     * 案发地（区）
     */
    private String crimeRegion;

    /**
     * 案发详细地址
     */
    private String crimeAddress;

    /**
     * 涉及金额
     */
    private String involvedAmount;

    /**
     * 举报具体内容
     */
    private String informContent;

    /**
     * 分派状态：NOT_ASSIGNED未分派，ASSIGNED已分派，RETURNED已撤回
     */
    private String assignment;

    /**
     * 核查状态
     */
    private String checkStatus;

    /**
     * 举报人ID
     */
    private Long informPersonId;
    /**
     * 附件
     */
    private String attachment;

    /**
     * 是否禁用：Y为正常，N为禁用
     */
    private String enable;
    /**
     * 逾期时间
     */
    private LocalDate expireTime;
}
