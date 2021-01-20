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
     * 核查时间
     */
    private LocalDateTime checkTime;
    /**
     * 线索属实性审核
     */
    private String verification;
    /**
     * 企业全称
     */
    private String enterpriseName;
    /**
     * 核查地点
     */
    private String checkPlace;
    /**
     * 涉及行业
     */
    private String industry;
    /**
     * 处置措施
     */
    private String disposalMeasures;
    /**
     * 核查详情
     */
    private String checkDetails;
}
