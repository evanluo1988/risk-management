package com.springboot.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.springboot.domain.Company;
import com.springboot.utils.ConvertUtils;
import lombok.Data;

/**
 * @author lhf
 * @date 2021/3/1 3:49 下午
 **/
@Data
public class CompanyImportDto {
    /**
     * 编号
     */
    @ExcelProperty(value = {"No."},index = 0)
    private String no;
    /**
     * 企业名称
     */
    @ExcelProperty(value = {"企业名称"},index = 1)
    private String entName;
    /**
     * 信用代码
     */
    @ExcelProperty(value = {"统一社会信用代码"},index = 2)
    private String creditCode;
    /**
     * 注册年月
     */
    @ExcelProperty(value = {"注册年月"},index = 3)
    private String regDate;
    /**
     * 高新区
     */
    @ExcelProperty(value = {"高新区"},index = 4)
    private String highTechZone;
    /**
     * 区县
     */
    @ExcelProperty(value = {"区县"},index = 5)
    private String zone;
    /**
     * 注册地址
     */
    @ExcelProperty(value = {"注册地址"},index = 6)
    private String regAddress;

    public Company toCompany(){
        Company company = ConvertUtils.sourceToTarget(this, Company.class);
        return company;
    }
}
