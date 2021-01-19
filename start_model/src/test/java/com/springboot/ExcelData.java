package com.springboot;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelData {
    /**
     * 标准表
     */
    @ExcelProperty(value = {"标准表"},index = 2)
    private String stdTableName;
    /**
     * 标准表字段
     */
    @ExcelProperty(value = {"标准表字段"},index = 4)
    private String stdTableField;
    /**
     * 标准表字段类型
     */
    @ExcelProperty(value = {"标准表字段类型"},index = 6)
    private String stdTableFieldType;
    /**
     * 原始表名
     */
    @ExcelProperty(value = {"原始表名"},index = 8)
    private String orgTableName;
    /**
     * 原始表字段
     */
    @ExcelProperty(value = {"原始表字段"},index = 10)
    private String orgTableField;
    /**
     * 原始表字段类型
     */
    @ExcelProperty(value = {"原始表字段"},index = 12)
    private String orgTableFieldType;
}
