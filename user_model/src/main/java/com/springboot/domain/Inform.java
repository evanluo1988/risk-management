package com.springboot.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import BaseDomain;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("inform")
public class Inform extends BaseDomain<Inform> {

    private static final long serialVersionUID = 1L;

    /**
     * 线索编号
     */
    private String clueNumber;

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
     * 是否分派
     */
    private Boolean assignment;

    /**
     * 核查状态
     */
    private String checkStatus;

    /**
     * 举报人ID
     */
    private Long informPersonId;

    /**
     * 是否禁用：Y为正常，N为禁用
     */
    private String enable;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新者
     */
    private String updateBy;


    @Override
    protected Serializable pkVal() {
        return null;
    }

}
