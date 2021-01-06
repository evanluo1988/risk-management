package com.springboot.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.springboot.enums.DisposalStageEnum;

import java.util.Objects;

/**
 * @Author 刘宏飞
 * @Date 2020/12/7 10:55
 * @Version 1.0
 */
public class DisposalStageConverter implements Converter<String> {
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
        DisposalStageEnum disposalStageEnum = DisposalStageEnum.descLikeOf(stringValue);
        return Objects.isNull(disposalStageEnum)?null:disposalStageEnum.getCode();
    }

    @Override
    public CellData convertToExcelData(String s, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        DisposalStageEnum disposalStageEnum = DisposalStageEnum.codeOf(s);
        return new CellData(Objects.isNull(disposalStageEnum)?null:disposalStageEnum.getDesc());
    }
}
