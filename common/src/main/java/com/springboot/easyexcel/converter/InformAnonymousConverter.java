package com.springboot.vo;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.springboot.enums.InformAnonymousEnum;

import java.util.Objects;

/**
 * @Author 刘宏飞
 * @Date 2020/12/1 9:35
 * @Version 1.0
 */
public class InformAnonymousConverter implements Converter<Integer> {
    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String stringValue = cellData.getStringValue();
        InformAnonymousEnum informAnonymousEnum = InformAnonymousEnum.descOf(stringValue);
        return Objects.isNull(informAnonymousEnum)?null:informAnonymousEnum.getCode();
    }

    @Override
    public CellData convertToExcelData(Integer s, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData(s);
    }
}
