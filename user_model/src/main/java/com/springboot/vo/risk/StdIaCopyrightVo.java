package com.springboot.vo.risk;

import lombok.Data;

/**
 * 软件著作权明细
 */
@Data
public class StdIaCopyrightVo {
    /**
     * 登记日期
     */
    private String registerDate;
    /**
     * 软件全称
     */
    private String softwareFullName;
    /**
     * 软件简称
     */
    private String softwareAbbreviation;
    /**
     * 著作权人
     */
    private String copyrightowner;
    /**
     * 注册号
     */
    private String registrationNumber;
    /**
     * 分类号
     */
    private String classifyNumber;
    /**
     * 版本号
     */
    private String versionsNumber;

}