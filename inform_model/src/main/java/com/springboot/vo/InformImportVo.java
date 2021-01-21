package com.springboot.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.springboot.domain.Inform;
import com.springboot.domain.InformCheck;
import com.springboot.domain.InformPerson;
import com.springboot.domain.InformReward;
import com.springboot.easyexcel.converter.*;
import com.springboot.enums.AssignmentEnum;
import com.springboot.enums.RewardStatusEnum;
import com.springboot.utils.ConvertUtils;
import com.springboot.utils.UserAuthInfoContext;
import com.springboot.utils.ServerCacheUtils;
import lombok.Data;

import java.time.ZoneId;
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
    @ExcelProperty(value = {"线索编号"},index = 0)
    private String clueNumber;

    /**
     * 是否实名举报，1为匿名，0为实名
     */
    @ExcelProperty(value = {"是否实名举报"},index = 1,converter = InformAnonymousConverter.class)
    private Boolean anonymous;
    /**
     * 举报人姓名
     */
    @ExcelProperty(value = {"举报人姓名"},index = 2)
    private String personName;
    /**
     * 举报人身份证号
     */
    @ExcelProperty(value = {"身份证号"},index = 3)
    private String identityCard;
    /**
     * 联系方式
     */
    @ExcelProperty(value = {"联系电话"},index = 4)
    private String contactWay;
    /**
     * 单位/住址
     */
    @ExcelProperty(value = {"单位住址"},index = 5,converter = InformSourceConverter.class)
    private String address;
    /**
     * 举报来源
     */
    @ExcelProperty(value = {"举报来源"},index = 6,converter = InformSourceConverter.class )
    private String source;
    /**
     * 举报时间
     */
    @ExcelProperty(value = {"举报时间"},index = 7)
    private Date informTimeStr;
    /**
     * 被举报对象名称
     */
    @ExcelProperty(value = {"被举报对象名称"},index = 8)
    private String informName;
    /**
     * 被举报类型
     */
    @ExcelProperty(value = {"被举报对象类型"},index = 9)
    private String informType;
    /**
     * 案发时间
     */
    @ExcelProperty(value = {"案发时间"},index = 10)
    private Date crimeTimeStr;
    /**
     * 案发地（区）
     */
    @ExcelProperty(value = {"案发地（区）"},index = 11)
    private String crimeRegion;
    /**
     * 案发详细地址
     */
    @ExcelProperty(value = {"案发地（街道或详细地址）"},index = 12)
    private String crimeAddress;
    /**
     * 涉及金额
     */
    @ExcelProperty(value = {"涉及金额（元）"},index = 13)
    private String involvedAmount;
    /**
     * 举报具体内容
     */
    @ExcelProperty(value = {"举报具体内容"},index = 14)
    private String informContent;
    /**
     * 举报附件
     */
    @ExcelProperty(value = {"附件"},index = 15)
    private String informAttachment;
    /**
     * 核查状态
     */
    @ExcelProperty(value = {"核查状态"},index = 16,converter = InformCheckStatusConverter.class)
    private String checkStatus;
    /**
     * 核查单位
     */
    @ExcelProperty(value = {"核查单位"},index = 17)
    private String checkUnitStr;
    /**
     * 核查时间
     */
    @ExcelProperty(value = {"核查时间"},index = 18)
    private Date checkTimeStr;
    /**
     * 是否逾期，1为逾期，0为未逾期
     */
    @ExcelProperty(value = {"逾期"},index = 19,converter = InformOverDueConverter.class)
    private Boolean overdue;
    /**
     * 企业全称
     */
    @ExcelProperty(value = {"企业全称"},index = 20)
    private String enterpriseName;
    /**
     * 核查地点
     */
    @ExcelProperty(value = {"核查地点"},index = 21)
    private String checkPlace;
    /**
     * 涉及行业
     */
    @ExcelProperty(value = {"涉及行业"},index = 22)
    private String industry;
    /**
     * 线索属实性审核
     */
    @ExcelProperty(value = {"线索属实性审核"},index = 23,converter = VerificationConverter.class)
    private String verification;
    /**
     * 核查对应企业全称
     */
    @ExcelProperty(value = {"核查对应企业全称"},index = 24)
    private String checkEnterpriseName;
    /**
     * 核查详情
     */
    @ExcelProperty(value = {"核查详情"},index = 25)
    private String checkDetails;
    /**
     * 处置措施
     */
    @ExcelProperty(value = {"处置措施"},index = 26,converter = DisposalMeasuresConverter.class)
    private String disposalMeasures;
    /**
     * 是否移交，1为移交，0为未移交
     */
    @ExcelProperty(value = {"是否移交"},index = 27,converter = InformTransferConverter.class)
    private Boolean transfer;
    /**
     * 移交单位
     */
    @ExcelProperty(value = {"移交单位"},index = 28)
    private String transferUnit;
    /**
     * 移交原因
     */
    @ExcelProperty(value = {"移交原因"},index = 29)
    private String transferReason;
    /**
     * 核查附件
     */
    @ExcelProperty(value = {"附件"},index = 30)
    private String informCheckAttachment;
    /**
     * 奖励情况
     */
    @ExcelProperty(value = {"奖励情况"},index = 31 ,converter = RewardStatusConverter.class)
    private String rewardStatus;
    /**
     * 奖励金额
     */
    @ExcelProperty(value = {"奖励金额"},index = 32,converter = InformRewardConverter.class)
    private Float rewardAmountFloat;
    /**
     * 奖励时间
     */
    @ExcelProperty(value = {"奖励时间"},index = 33)
    private Date rewardTimeStr;
    /**
     * 逾期时间
     */
    @ExcelProperty(value = {"逾期时间"},index = 34)
    private Date expireTimeStr;

    public Inform toInform() {
        Inform inform = ConvertUtils.sourceToTarget(this, Inform.class);
        inform.setAreaId(ServerCacheUtils.getAreaByName("江北区").getId());
        inform.setAssignment(AssignmentEnum.NOT_ASSIGNED.getCode());
        inform.setAttachment(informAttachment);
        inform.setInformTime(Objects.isNull(informTimeStr) ? null : informTimeStr.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        inform.setCrimeTime(Objects.isNull(crimeTimeStr) ? null : crimeTimeStr.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        inform.setExpireTime(Objects.isNull(expireTimeStr) ? null : expireTimeStr.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
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
