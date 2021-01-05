package com.springboot.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.springboot.enums.InformTransferEnum;

import java.util.Objects;

/**
 * @Author 刘宏飞
 * @Date 2020/12/1 11:28
 * @Version 1.0
 */
public class InformTransferConverter implements Converter<Boolean> {
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
        InformTransferEnum informTransferEnum = InformTransferEnum.descOf(stringValue);
        return Objects.isNull(informTransferEnum)?null:informTransferEnum.getCode();
    }

    @Override
    public CellData convertToExcelData(Boolean aBoolean, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        InformTransferEnum informTransferEnum = InformTransferEnum.codeOf(aBoolean);
        return new CellData(Objects.isNull(informTransferEnum)?null:informTransferEnum.getDesc());
    }
}
