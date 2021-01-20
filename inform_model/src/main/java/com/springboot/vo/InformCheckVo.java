package com.springboot.vo;

import com.springboot.config.DateFormatConfig;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/20 16:23
 */
@Data
public class InformCheckVo {
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
    private String rewardContent;
    /**
     * 核查时间
     */
    @NotNull(groups = InformCheckVo.CheckGroup.class,message = "核查时间必填")
    @DateTimeFormat(pattern = DateFormatConfig.DATE_FORMAT)
    private LocalDate checkTime;
    /**
     * 核查状态
     */
    @NotNull(groups = InformCheckVo.CheckGroup.class,message = "核查状态必填")
    private String checkStatus;
}
