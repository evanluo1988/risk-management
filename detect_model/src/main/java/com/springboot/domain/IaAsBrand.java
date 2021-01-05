package com.springboot.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("ia_as_brand")
@EqualsAndHashCode(callSuper = true)
public class IaAsBrand extends BaseDomain {
    /**
     * 业务申请序号
     */
    @TableField(value = "req_id")
    private String reqId;
    /**
     * 商标类型
     */
    @JSONField(name = "brandType")
    @TableField(value = "brandtype")
    private String brandType;
    /**
     * 图形
     */
    @JSONField(name = "graph")
    @TableField(value = "graph")
    private String graph;
    /**
     * 注册号
     */
    @JSONField(name = "registrationNumber")
    @TableField(value = "registrationnumber")
    private String registrationNumber;
    /**
     * 图形缩略图
     */
    @JSONField(name = "graphThumbnail")
    @TableField(value = "graphthumbnail")
    private String graphthumbnail;
    /**
     * 注册日期
     */
    @JSONField(name = "registrationDate")
    @TableField(value = "registrationdate")
    private String registrationDate;
    /**
     * 申请号
     */
    @JSONField(name = "applicationNumber")
    @TableField(value = "applicationnumber")
    private String applicationNumber;
    /**
     * 申请日期
     */
    @JSONField(name = "applicationDate")
    @TableField(value = "applicationdate")
    private String applicationDate;
    /**
     * 申请人名称(原文)
     */
    @JSONField(name = "applicationName")
    @TableField(value = "applicationname")
    private String applicationName;
    /**
     * 代理人名称(原文)
     */
    @JSONField(name = "agentName")
    @TableField(value = "agentname")
    private String agentName;
    /**
     * 商标主键ID
     */
    @JSONField(name = "brandId")
    @TableField(value = "brandid")
    private String brandId;
    /**
     * 商标名称
     */
    @JSONField(name = "brandName")
    @TableField(value = "brandname")
    private String brandName;
    /**
     * 尼斯分类
     */
    @JSONField(name = "niceClassify")
    @TableField(value = "niceclassify")
    private String niceClassify;
    /**
     * 权利状态
     */
    @JSONField(name = "authorityStatus")
    @TableField(value = "authoritystatus")
    private String authorityStatus;
}
