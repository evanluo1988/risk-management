package com.springboot.model;

import lombok.Data;

import java.util.Date;
import java.util.Objects;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/5 16:20
 */
@Data
public class InformExportModel {

    /**
     * 线索编号
     */
    private String clueNumber;

    /**
     * 是否实名举报，1为匿名，0为实名
     */
    private Boolean anonymous;
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
    private String informTime;
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
    private String crimeTime;
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
     * 核查时间
     */
    private String checkTime;
    /**
     * 是否逾期，1为逾期，0为未逾期
     */
    private Boolean overdue;
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
     * 核查附件
     */
    private String informCheckAttachment;
    /**
     * 奖励情况
     */
    private String rewardStatus;
    /**
     * 奖励金额
     */
    private Float rewardAmountFloat;
    private Long rewardAmount;
    /**
     * 奖励时间
     */
    private String rewardTime;
    /**
     * 逾期时间
     */
    private String expireTime;

    private Long areaId;

    public Float getRewardAmountFloat() {
        if (Objects.nonNull(rewardAmount)){
            rewardAmountFloat = Float.valueOf(String.valueOf(rewardAmount))/100;
        }
        return rewardAmountFloat;
    }
}
