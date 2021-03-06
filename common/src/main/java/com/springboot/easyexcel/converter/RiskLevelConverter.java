package com.springboot.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.springboot.enums.RiskLevelEnum;

import java.util.Objects;

/**
 * @Author 刘宏飞
 * @Date 2020/12/7 10:41
 * @Version 1.0
 */
public class RiskLevelConverter implements Converter<String> {
    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String stringValue = cellData.getStringValue();
        RiskLevelEnum riskLevelEnum = RiskLevelEnum.descOf(stringValue);
        return Objects.isNull(riskLevelEnum)?null:riskLevelEnum.getCode();
    }

    @Override
    public CellData convertToExcelData(String s, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        RiskLevelEnum riskLevelEnum = RiskLevelEnum.codeOf(s);
        return new CellData(Objects.isNull(riskLevelEnum)?null:riskLevelEnum.getDesc());
    }
}
