package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-11-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("informs")
public class Inform extends BaseDomain {

    private static final long serialVersionUID = 1L;

    /**
     * 线索编号
     */
    private String clueNumber;
    /**
     * 举报来源
     */
    private String source;
    /**
     * 是否实名举报，1为匿名，0为实名
     */
    private Boolean anonymous;

    /**
     * 举报时间
     */
    private LocalDateTime informTime;

    /**
     * 被举报对象名称
     */
    private String informName;

    /**
     * 被举报类型
     */
    private String informType;

    /**
     * 分派区域ID
     */
    private Long areaId;

    /**
     * 案发时间
     */
    private LocalDateTime crimeTime;

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
     * 分派状态：NOT_ASSIGNED未分派，ASSIGNED已分派，RETURNED已撤回
     */
    private String assignment;

    /**
     * 核查状态
     */
    private String checkStatus;

    /**
     * 举报人ID
     */
    private Long informPersonId;
    /**
     * 附件
     */
    private String attachment;

    /**
     * 是否禁用：Y为正常，N为禁用
     */
    private String enable;
    /**
     * 逾期时间
     */
    private LocalDate expireTime;
}
