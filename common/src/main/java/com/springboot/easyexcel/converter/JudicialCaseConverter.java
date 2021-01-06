package com.springboot.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.springboot.enums.JudicialCaseEnum;

import java.util.Objects;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/6 14:00
 */
public class JudicialCaseConverter implements Converter<Integer> {
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
        JudicialCaseEnum judicialCaseEnum = JudicialCaseEnum.descOf(stringValue);
        return Objects.isNull(judicialCaseEnum) ? null : judicialCaseEnum.getCode();
    }

    @Override
    public CellData convertToExcelData(Integer integer, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        JudicialCaseEnum judicialCaseEnum = JudicialCaseEnum.codeOf(integer);
        return new CellData(Objects.isNull(judicialCaseEnum) ? null : judicialCaseEnum.getDesc());
    }
}
