package com.springboot.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.springboot.enums.RewardStatusEnum;

import java.util.Objects;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/20 19:17
 */
public class RewardStatusConverter implements Converter<String> {
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
        RewardStatusEnum rewardStatusEnum = RewardStatusEnum.descOf(stringValue);
        return Objects.isNull(rewardStatusEnum) ? null : rewardStatusEnum.getCode();
    }

    @Override
    public CellData convertToExcelData(String s, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        RewardStatusEnum rewardStatusEnum = RewardStatusEnum.codeOf(s);
        return new CellData(Objects.isNull(rewardStatusEnum) ? null : rewardStatusEnum.getDesc());
    }
}
