package com.springboot.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.springboot.enums.RegionalScaleEnum;

import java.util.Objects;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/15 17:06
 */
public class RegionalScaleConverter implements Converter<String> {
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
        RegionalScaleEnum regionalScaleEnum = RegionalScaleEnum.descOf(stringValue);
        return Objects.isNull(regionalScaleEnum) ? null : regionalScaleEnum.getCode();
    }

    @Override
    public CellData convertToExcelData(String s, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        RegionalScaleEnum regionalScaleEnum = RegionalScaleEnum.codeOf(s);
        return new CellData(Objects.isNull(regionalScaleEnum) ? null : regionalScaleEnum.getDesc());
    }
}
