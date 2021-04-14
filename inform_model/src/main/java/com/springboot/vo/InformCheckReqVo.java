package com.springboot.vo;

import com.springboot.config.DateFormatConfig;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class InformCheckReqVo {
    public interface CheckGroup{}

    private Long id;

    /**
     * 线索属实性审核
     */
    private String verification;
    /**
     * 企业全称
     */
    private String enterpriseName;
    /**
     * 核查企业全称
     */
    private String checkEnterpriseName;
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
    @NotBlank(groups = InformCheckVo.CheckGroup.class,message = "核查详情必填")
    private String checkDetails;
    /**
     * 奖励情况
     */
    private String rewardStatus;
    /**
     * 奖励金额
     */
    private Long rewardAmount;
    /**
     * 奖励时间
     */
    private LocalDateTime rewardTime;
    /**
     * 核查时间
     */
    @NotNull(groups = InformCheckVo.CheckGroup.class,message = "核查时间必填")
    @DateTimeFormat(pattern = DateFormatConfig.DATE_FORMAT)
    private LocalDate checkTime;
    /**
     * 核查状态：状态基自动切换
     */
    //@NotNull(groups = InformCheckVo.CheckGroup.class,message = "核查状态必填")
    //private String checkStatus;
}
