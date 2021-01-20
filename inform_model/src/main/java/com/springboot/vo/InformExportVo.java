package com.springboot.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.springboot.domain.Area;
import com.springboot.easyexcel.converter.*;
import com.springboot.utils.ServerCacheUtils;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.Objects;
import java.util.Optional;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/5 16:06
 */
@Data
public class InformExportVo {
    /**
     * 线索编号
     */
    @ExcelProperty(value = {"举报人信息","线索编号"},index = 0)
    private String clueNumber;

    /**
     * 是否实名举报，1为匿名，0为实名
     */
    @ExcelProperty(value = {"举报人信息","是否实名举报"},index = 1,converter = InformAnonymousConverter.class)
    private Boolean anonymous;
    /**
     * 举报人姓名
     */
    @ExcelProperty(value = {"举报人信息","举报人姓名"},index = 2)
    private String personName;
    /**
     * 举报人身份证号
     */
    @ExcelProperty(value = {"举报人信息","身份证号"},index = 3)
    private String identityCard;
    /**
     * 联系方式
     */
    @ExcelProperty(value = {"举报人信息","联系电话"},index = 4)
    private String contactWay;
    /**
     * 单位/住址
     */
    @ExcelProperty(value = {"举报人信息","单位住址"},index = 5)
    private String address;
    /**
     * 举报来源
     */
    @ExcelProperty(value = {"举报信息","举报来源"},index = 6,converter = InformSourceConverter.class )
    private String source;
    /**
     * 举报时间
     */
    @ExcelProperty(value = {"举报信息","举报时间"},index = 7)
    private String informTime;
    /**
     * 被举报对象名称
     */
    @ExcelProperty(value = {"举报信息","被举报对象名称"},index = 8)
    private String informName;
    /**
     * 被举报类型
     */
    @ExcelProperty(value = {"举报信息","被举报对象类型"},index = 9)
    private String informType;
    /**
     * 案发时间
     */
    @ExcelProperty(value = {"举报信息","案发时间"},index = 10)
    private String crimeTime;
    /**
     * 案发地（区）
     */
    @ExcelProperty(value = {"举报信息","案发地（区）"},index = 11)
    private String crimeRegion;
    /**
     * 案发详细地址
     */
    @ExcelProperty(value = {"举报信息","案发地（街道或详细地址）"},index = 12)
    private String crimeAddress;
    /**
     * 涉及金额
     */
    @ExcelProperty(value = {"举报信息","涉及金额（元）"},index = 13)
    private String involvedAmount;
    /**
     * 举报具体内容
     */
    @ExcelProperty(value = {"举报信息","举报具体内容"},index = 14)
    private String informContent;
    /**
     * 举报附件
     */
    @ExcelProperty(value = {"举报信息","附件"},index = 15)
    private String informAttachment;
    /**
     * 核查状态
     */
    @ExcelProperty(value = {"核查处置情况","核查状态"},index = 16,converter = InformCheckStatusConverter.class)
    private String checkStatus;

    private Long areaId;
    /**
     * 核查单位
     */
    @ExcelProperty(value = {"核查处置情况","核查单位"},index = 17)
    private String areaName;
    /**
     * 核查时间
     */
    @ExcelProperty(value = {"核查处置情况","核查时间"},index = 18)
    private String checkTime;
    /**
     * 是否逾期，1为逾期，0为未逾期
     */
    @ExcelProperty(value = {"核查处置情况","逾期"},index = 19,converter = InformOverDueConverter.class)
    private Boolean overdue;
    /**
     * 企业全称
     */
    @ExcelProperty(value = {"核查处置情况","企业全称"},index = 20)
    private String enterpriseName;
    /**
     * 核查地点
     */
    @ExcelProperty(value = {"核查处置情况","核查地点"},index = 21)
    private String checkPlace;
    /**
     * 涉及行业
     */
    @ExcelProperty(value = {"核查处置情况","涉及行业"},index = 22)
    private String industry;
    /**
     * 线索属实性审核
     */
    @ExcelProperty(value = {"核查处置情况","线索属实性审核"},index = 23,converter = VerificationConverter.class)
    private String verification;
    /**
     * 核查对应企业全称
     */
    @ExcelProperty(value = {"核查处置情况","核查对应企业全称"},index = 24)
    private String checkEnterpriseName;
    /**
     * 核查详情
     */
    @ExcelProperty(value = {"核查处置情况","核查详情"},index = 25)
    private String checkDetails;
    /**
     * 处置措施
     */
    @ExcelProperty(value = {"核查处置情况","处置措施"},index = 26,converter = DisposalMeasuresConverter.class)
    private String disposalMeasures;
    /**
     * 是否移交，1为移交，0为未移交
     */
    @ExcelProperty(value = {"核查处置情况","是否移交"},index = 27,converter = InformTransferConverter.class)
    private Boolean transfer;
    /**
     * 移交单位
     */
    @ExcelProperty(value = {"核查处置情况","移交单位"},index = 28)
    private String transferUnit;
    /**
     * 移交原因
     */
    @ExcelProperty(value = {"核查处置情况","移交原因"},index = 29)
    private String transferReason;
    /**
     * 核查附件
     */
    @ExcelProperty(value = {"核查处置情况","附件"},index = 30)
    private String informCheckAttachment;
    /**
     * 奖励情况
     */
    @ExcelProperty(value = {"奖励信息","奖励情况"},index = 31)
    private String rewardContent;
    /**
     * 奖励金额
     */
    @ExcelProperty(value = {"奖励信息","奖励金额"},index = 32,converter = InformRewardConverter.class)
    private Float rewardAmountFloat;
    /**
     * 奖励时间
     */
    @ExcelProperty(value = {"奖励信息","奖励时间"},index = 33)
    private String rewardTime;
    /**
     * 逾期时间
     */
    @ExcelProperty(value = {"奖励信息","逾期时间"},index = 34)
    private String expireTime;

    public String getAreaName(){
        if (Objects.isNull(areaId)){
            return areaName = null;
        }else {
            Area areaById = ServerCacheUtils.getAreaById(areaId);
            return Optional.ofNullable(areaById).orElse(new Area()).getAreaName();
        }
    }
}
