package com.springboot.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.springboot.easyexcel.LocalDateTimeConverter;
import com.springboot.easyexcel.LocalDateTimeFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 投诉举报
 *
 * @Author 刘宏飞
 * @Date 2020/11/30 14:21
 * @Version 1.0
 */
@Data
public class InformVo {
    /**
     * 线索编号
     */
    private String clueNumber;

    /**
     * 是否实名举报，1为匿名，0为实名
     */
    private String anonymous;
    /**
     * 举报人姓名
     */
    private String personName;
    /**
     * 举报人身份证号
     */
    private String identityCard;

    /**
     * 联系方式
     */
    private String contactWay;
    /**
     * 单位/住址
     */
    private String address;
    /**
     * 举报来源
     */
    private String source;
    /**
     * 举报时间
     */
    //@ExcelProperty(converter = LocalDateTimeConverter.class)
    //@LocalDateTimeFormat("yyyy/MM/dd")
    private String informTimeStr;

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
    //@ExcelProperty(converter = LocalDateTimeConverter.class)
    //@LocalDateTimeFormat("yyyy/MM/dd")
    private String crimeTimeStr;

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
     * 举报附件
     */
    private String informAttachment;
    /**
     * 核查状态
     */
    private String checkStatus;
    /**
     * 核查单位
     */
    private String checkUnit;
    /**
     * 核查时间
     */
    //@ExcelProperty(converter = LocalDateTimeConverter.class)
    //@LocalDateTimeFormat("yyyy/MM/dd")
    private String checkTimeStr;
    /**
     * 是否逾期，1为逾期，0为未逾期
     */
    private String overdue;
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
     * 线索属实性审核
     */
    private String verification;
    /**
     * 核查对应企业全称
     */
    private String checkEnterpriseName;
    /**
     * 核查详情
     */
    private String checkDetails;
    /**
     * 处置措施
     */
    private String disposalMeasures;
    /**
     * 是否移交，1为移交，0为未移交
     */
    private String transfer;

    /**
     * 移交单位
     */
    private String transferUnit;

    /**
     * 移交原因
     */
    private String transferReason;
    /**
     * 核查附件
     */
    private String informCheckAttachment;
    /**
     * 奖励情况
     */
    private String rewardContent;
    /**
     * 奖励金额
     */
    private String rewardAmount;

    /**
     * 奖励时间
     */
    //@ExcelProperty(converter = LocalDateTimeConverter.class)
    //@LocalDateTimeFormat("yyyy/MM/dd")
    private String rewardTimeStr;
}
