package com.springboot.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.springboot.enums.InvolveInternetEnum;

import java.util.Objects;

/**
 * @Author 刘宏飞
 * @Date 2020/12/7 11:13
 * @Version 1.0
 */
public class InvolveInternetConverter implements Converter<String> {
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
        InvolveInternetEnum involveInternetEnum = InvolveInternetEnum.descOf(stringValue);
        return Objects.isNull(involveInternetEnum) ? null : involveInternetEnum.getCode();
    }

    @Override
    public CellData convertToExcelData(String s, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        InvolveInternetEnum involveInternetEnum = InvolveInternetEnum.codeOf(s);
        return new CellData(Objects.isNull(involveInternetEnum) ? null : involveInternetEnum.getDesc());
    }
}
