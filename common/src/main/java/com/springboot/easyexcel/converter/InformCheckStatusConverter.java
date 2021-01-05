package com.springboot.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.springboot.enums.CheckStatusEnum;

import java.util.Objects;

/**
 * @Author 刘宏飞
 * @Date 2020/12/1 10:38
 * @Version 1.0
 */
public class InformCheckStatusConverter implements Converter<String> {
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
        CheckStatusEnum checkStatusEnum = CheckStatusEnum.descOf(stringValue);
        return Objects.isNull(checkStatusEnum)?null: checkStatusEnum.getCode();
    }

    @Override
    public CellData convertToExcelData(String s, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        CheckStatusEnum checkStatusEnum = CheckStatusEnum.codeOf(s);
        return new CellData(Objects.isNull(checkStatusEnum)?null: checkStatusEnum.getDesc());
    }
}
