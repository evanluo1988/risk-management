package com.springboot.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.springboot.enums.InformOverDueEnum;

import java.util.Objects;

/**
 * @Author 刘宏飞
 * @Date 2020/12/1 11:18
 * @Version 1.0
 */
public class InformOverDueConverter implements Converter<Boolean> {
    @Override
    public Class supportJavaTypeKey() {
        return Boolean.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Boolean convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String stringValue = cellData.getStringValue();
        InformOverDueEnum informOverDueEnum = InformOverDueEnum.descOf(stringValue);
        return Objects.isNull(informOverDueEnum)?null:informOverDueEnum.getCode();
    }

    @Override
    public CellData convertToExcelData(Boolean aBoolean, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData(aBoolean);
    }
}
