package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @Author 刘宏飞
 * @Date 2020/12/29 10:39
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("std_ia_brand")
public class StdIaBrand extends BaseDomain {
    /**
     * 申请编号ID 由我方生成
     */
    @TableField(value = "req_id")
    private String reqId;
    /**
     * 业务申请编号
     */
    @TableField(value = "businessid")
    private String businessId;
    /**
     * 商标类型
     */
    @TableField(value = "brandtype")
    private String brandType;
    /**
     * 图形
     */
    @TableField(value = "graph")
    private String graph;
    /**
     * 注册号
     */
    @TableField(value = "registrationnumber")
    private String registrationNumber;
    /**
     * 图形缩略图
     */
    @TableField(value = "graphthumbnail")
    private String graphThumbnail;
    /**
     * 注册日期
     */
    @TableField(value = "registrationdate")
    private LocalDate registrationDate;
    /**
     * 申请号
     */
    @TableField(value = "applicationnumber")
    private String applicationNumber;
    /**
     * 申请日期
     */
    @TableField(value = "applicationdate")
    private LocalDate applicationDate;
    /**
     * 申请人名称(原文)
     */
    @TableField(value = "applicationname")
    private String applicationName;
    /**
     * 代理人名称(原文)
     */
    @TableField(value = "agentname")
    private String agentName;
    /**
     * 商标主键ID
     */
    @TableField(value = "brandid")
    private String brandId;
    /**
     * 商标名称
     */
    @TableField(value = "brandname")
    private String brandName;
    /**
     * 尼斯分类
     */
    @TableField(value = "niceclassify")
    private String niceClassify;
    /**
     * 权利状态
     */
    @TableField(value = "authoritystatus")
    private String authorityStatus;

}
