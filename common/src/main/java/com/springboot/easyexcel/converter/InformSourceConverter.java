package com.springboot.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.springboot.enums.InformSourceEnum;

import java.util.Objects;

/**
 * @Author 刘宏飞
 * @Date 2020/12/4 17:46
 * @Version 1.0
 */
public class InformSourceConverter implements Converter<String> {
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
        InformSourceEnum informSourceEnum = InformSourceEnum.descOf(stringValue);
        return Objects.isNull(informSourceEnum)?null:informSourceEnum.getCode();
    }

    @Override
    public CellData convertToExcelData(String s, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        InformSourceEnum informSourceEnum = InformSourceEnum.valueOf(s);
        return new CellData(Objects.isNull(informSourceEnum)?null:informSourceEnum.getDesc());
    }
}
