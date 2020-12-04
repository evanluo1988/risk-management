package com.springboot.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.springboot.domain.*;
import com.springboot.easyexcel.converter.InformAnonymousConverter;
import com.springboot.easyexcel.converter.InformCheckStatusConverter;
import com.springboot.easyexcel.converter.InformOverDueConverter;
import com.springboot.easyexcel.converter.InformTransferConverter;
import com.springboot.enums.InformAssignmentEnum;
import com.springboot.exception.ServiceException;
import com.springboot.util.ConvertUtils;
import com.springboot.utils.ServerCacheUtils;
import com.springboot.utils.UserAuthInfoContext;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * 投诉举报
 *
 * @Author 刘宏飞
 * @Date 2020/11/30 14:21
 * @Version 1.0
 */
@Data
public class InformImportVo {
    /**
     * 线索编号
     */
    private String clueNumber;

    /**
     * 是否实名举报，1为匿名，0为实名
     */
    @ExcelProperty(converter = InformAnonymousConverter.class)
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
    private Date informTimeStr;

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
    private Date crimeTimeStr;

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
    @ExcelProperty(converter = InformCheckStatusConverter.class)
    private String checkStatus;
    /**
     * 核查单位
     */
    private String checkUnitStr;
    /**
     * 核查时间
     */
    private Date checkTimeStr;
    /**
     * 是否逾期，1为逾期，0为未逾期
     */
    @ExcelProperty(converter = InformOverDueConverter.class)
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
    @ExcelProperty(converter = InformTransferConverter.class)
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
    private String rewardContent;
    /**
     * 奖励金额
     */
    private Float rewardAmountFloat;

    /**
     * 奖励时间
     */
    private Date rewardTimeStr;

    public Inform toInform() {
        Inform inform = ConvertUtils.sourceToTarget(this, Inform.class);
        inform.setAssignment(InformAssignmentEnum.NOT_ASSIGNED.getCode());
        inform.setAttachment(informAttachment);
        inform.setInformTime(Objects.isNull(informTimeStr) ? null : informTimeStr.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        inform.setCrimeTime(Objects.isNull(crimeTimeStr) ? null : crimeTimeStr.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        inform.setCreateBy(UserAuthInfoContext.getUserName());
        inform.setCreateTime(new Date());
        return inform;
    }

    public InformCheck toInformCheck() {
        InformCheck informCheck = ConvertUtils.sourceToTarget(this, InformCheck.class);
        informCheck.setAttachment(informCheckAttachment);
        informCheck.setCheckTime(Objects.isNull(checkTimeStr) ? null : checkTimeStr.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        informCheck.setCreateBy(UserAuthInfoContext.getUserName());
        informCheck.setCreateTime(new Date());

        informCheck.setCheckUnit(Objects.isNull(ServerCacheUtils.getAreaByName(checkUnitStr)) ? null : ServerCacheUtils.getAreaByName(checkUnitStr).getId());
        return informCheck;
    }

    public InformPerson toInformPerson() {
        InformPerson informPerson = ConvertUtils.sourceToTarget(this, InformPerson.class);
        informPerson.setCreateBy(UserAuthInfoContext.getUserName());
        informPerson.setCreateTime(new Date());
        return informPerson;
    }

    public InformReward toInformReward() {
        InformReward informReward = ConvertUtils.sourceToTarget(this, InformReward.class);
        informReward.setRewardTime(Objects.isNull(rewardTimeStr) ? null : rewardTimeStr.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        informReward.setCreateBy(UserAuthInfoContext.getUserName());
        informReward.setCreateTime(new Date());
        informReward.setRewardAmount(Objects.isNull(rewardAmountFloat) ? null : Float.valueOf(rewardAmountFloat * 100).longValue());
        return informReward;
    }
}
