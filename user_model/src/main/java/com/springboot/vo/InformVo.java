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
    public interface DispatcherGroup{}
    public interface CheckGroup{}
    public interface RefundGroup{}

    @NotNull(groups = DispatcherGroup.class,message = "区域不能为空")
    private Long areaId;
    /**
     * 举报来源
     */
    private String source;

    /**
     * 核查状态
     */
    private String checkStatus;
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
     * 奖励情况
     */
    private String rewardContent;
    /**
     * 被举报对象名称
     */
    private String informName;
    /**
     * 线索属实性审核
     */
    private String verification;
    /**
     * 是否预期
     */
    private Boolean overdue;
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

    /**
     * 核查单位
     */
    private String checkUnit;

    /**
     * 核查时间
     */
    @NotNull(groups = CheckGroup.class,message = "核查时间必填")
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
     * 涉及行业
     */
    private String industry;

    /**
     * 核查详情
     */
    @NotBlank(groups = CheckGroup.class,message = "核查详情必填")
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
     * 附件
     */
    private String attachment;
    /**
     * 退回原因
     */
    @NotBlank(message = "退回原因必填",groups = RefundGroup.class)
    private String refundReason;

}
