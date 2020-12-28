package com.springboot.domain.risk;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ia_as_copyright")
public class IaAsCopyright extends BaseDomain {
    /**
     * 业务申请序号
     */
    @TableField(value = "req_id")
    private String reqId;
    /**
     * 版权软著登记主键ID
     */
    @JSONField(name = "copyrightId")
    @TableField(value = "copyrightid")
    private String copyrightId;
    /**
     * 国别
     */
    @JSONField(name = "nationality")
    @TableField(value = "nationality")
    private String nationality;
    /**
     * 注册号
     */
    @JSONField(name = "registrationNumber")
    @TableField(value = "registrationnumber")
    private String registrationNumber;
    /**
     * 分类号
     */
    @JSONField(name = "classifyNumber")
    @TableField(value = "classifynumber")
    private String classifyNumber;
    /**
     * 软件全称
     */
    @JSONField(name = "softwareFullName")
    @TableField(value = "softwarefullname")
    private String softwareFullName;
    /**
     * 软件简称
     */
    @JSONField(name = "softwareAbbreviation")
    @TableField(value = "softwareabbreviation")
    private String softwareAbbreviation;
    /**
     * 版本号
     */
    @JSONField(name = "versionsNumber")
    @TableField(value = "versionsnumber")
    private String versionsNumber;
    /**
     * 著作权人
     */
    @JSONField(name = "copyrightOwner")
    @TableField(value = "copyrightowner")
    private String copyrightowner;
    /**
     * 首次发表日期
     */
    @JSONField(name = "firstPublicationDate")
    @TableField(value = "firstpublicationdate")
    private String firstPublicationDate;
    /**
     * 首次发表年
     */
    @JSONField(name = "firstPublicationYear")
    @TableField(value = "firstpublicationyear")
    private String firstPublicationYear;
    /**
     * 登记日期
     */
    @JSONField(name = "registerDate")
    @TableField(value = "registerdate")
    private String registerDate;
    /**
     * 登记年
     */
    @JSONField(name = "registerYear")
    @TableField(value = "registeryear")
    private String registerYear;
}
