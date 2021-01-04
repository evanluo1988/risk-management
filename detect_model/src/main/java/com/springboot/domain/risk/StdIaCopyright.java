package com.springboot.domain.risk;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @Author 刘宏飞
 * @Date 2020/12/29 10:45
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("std_ia_copyright")
public class StdIaCopyright extends BaseDomain {
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
     * 版权软著登记主键ID
     */
    @TableField(value = "copyrightid")
    private String copyrightId;
    /**
     * 国别
     */
    @TableField(value = "nationality")
    private String nationality;
    /**
     * 注册号
     */
    @TableField(value = "registrationnumber")
    private String registrationNumber;
    /**
     * 分类号
     */
    @TableField(value = "classifynumber")
    private String classifyNumber;
    /**
     * 软件全称
     */
    @TableField(value = "softwarefullname")
    private String softwareFullName;
    /**
     * 软件简称
     */
    @TableField(value = "softwareabbreviation")
    private String softwareAbbreviation;
    /**
     * 版本号
     */
    @TableField(value = "versionsnumber")
    private String versionSnNumber;
    /**
     * 著作权人
     */
    @TableField(value = "copyrightowner")
    private String copyrightOwner;
    /**
     * 首次发表日期
     */
    @TableField(value = "firstpublicationdate")
    private LocalDate firstPublicationDate;
    /**
     * 首次发表年
     */
    @TableField(value = "firstpublicationyear")
    private String firstPublicationYear;
    /**
     * 登记日期
     */
    @TableField(value = "registerdate")
    private LocalDate registerDate;
    /**
     * 登记年
     */
    @TableField(value = "registeryear")
    private String registerYear;

}
