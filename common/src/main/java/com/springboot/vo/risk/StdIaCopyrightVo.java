package com.springboot.vo.risk;

import com.springboot.util.StrUtils;
import lombok.Data;

import java.time.LocalDate;

/**
 * 软件著作权明细
 */
@Data
public class StdIaCopyrightVo {
    /**
     * 登记日期
     */
    private LocalDate registerDate;
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
    private String copyrightOwner;
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

    public String getRegisterDate() {
        return StrUtils.getDataStr(registerDate);
    }
}
