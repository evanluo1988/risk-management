package com.springboot.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/20 16:23
 */
@Data
public class InformCheckVo {
    private Long id;
    /**
     * 核查单位
     */
    private Long checkUnit;

    /**
     * 核查时间
     */
    private LocalDateTime checkTime;

    /**
     * 企业全称
     */
    private String enterpriseName;

    /**
     * 核查地点
     */
    private String checkPlace;

    /**
     * 是否逾期，1为逾期，0为未逾期
     */
    private Boolean overdue;

    /**
     * 涉及行业
     */
    private String industry;

    /**
     * 线索属实性审核
     */
    private String verification;

    /**
     * 核查详情
     */
    private String checkDetails;

    /**
     * 核查对应企业全称
     */
    private String checkEnterpriseName;

    /**
     * 处置措施
     */
    private String disposalMeasures;

    /**
     * 是否移交，1为移交，0为未移交
     */
    private Boolean transfer;

    /**
     * 移交单位
     */
    private String transferUnit;

    /**
     * 移交原因
     */
    private String transferReason;

    /**
     * 举报ID
     */
    private Long informId;

    /**
     * 附件
     */
    private String attachment;

}
