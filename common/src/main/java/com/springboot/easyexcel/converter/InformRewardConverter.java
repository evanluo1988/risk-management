package com.springboot.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.springboot.enums.InformRewardEnum;

import java.util.Objects;

/**
 * @Author 刘宏飞
 * @Date 2020/12/4 18:02
 * @Version 1.0
 */
public class InformRewardConverter implements Converter<String> {
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
        InformRewardEnum informRewardEnum = InformRewardEnum.descOf(stringValue);
        return Objects.isNull(informRewardEnum) ? null : informRewardEnum.getCode();
    }

    @Override
    public CellData convertToExcelData(String s, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        InformRewardEnum informRewardEnum = InformRewardEnum.valueOf(s);
        return new CellData(Objects.isNull(informRewardEnum) ? null : informRewardEnum.getDesc());
    }
}
