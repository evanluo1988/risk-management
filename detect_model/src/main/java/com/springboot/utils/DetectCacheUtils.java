package com.springboot.utils;

import com.springboot.domain.risk.*;
import com.springboot.model.StdTable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DetectCacheUtils {

    /**
     * 转换规则（原始表到标准表规则）
     */
    private static List<EtlTranRule> etlTranRuleList;

    /**
     * 表类型转换
     * <tableName, StdTable>
     */
    private static Map<String, StdTable> stdTableMap;

    /**
     * 指标规则表
     */
    private static List<Quota> quotaList;

    /**
     * 指标分档表
     */
    private static Map<String, List<QuotaGrand>> quotaGrandMap;

    /**
     * 指标维度
     */
    private static List<QuotaDimension> quotaDimensionList;

    /**
     * 字典表数据
     */
    private static Map<String, List<DicTable>> dicTableMap;

    public static void setDicTable(List<DicTable> dicTableList) {
        dicTableMap = dicTableList.stream().collect(Collectors.groupingBy(DicTable::getDicType));
    }

    public static List<DicTable> getDicTableListByType(String type) {
        return dicTableMap.get(type);
    }

    public static void setQuotaDimensionList(List<QuotaDimension> quotaDimensionList) {
        DetectCacheUtils.quotaDimensionList = quotaDimensionList;
    }

    public static QuotaDimension getQuotaDimensionById(Long id) {
        if(id == null) {
            return null;
        }
        for(QuotaDimension quotaDimension : quotaDimensionList) {
            if(id.equals(quotaDimension.getId())) {
                return quotaDimension;
            }
        }
        return null;
    }

    /**
     *
     * @param quotaGrandList
     */
    public static void setQuotaGrandList(List<QuotaGrand> quotaGrandList){
        DetectCacheUtils.quotaGrandMap = quotaGrandList.stream().collect(Collectors.groupingBy(QuotaGrand::getGrandCode));
    }

    public static List<QuotaGrand> getQuotaGrandListByCode(String grandCode) {
        return quotaGrandMap.get(grandCode);
    }

    public static void setQuotaList(List<Quota> quotaList){
        DetectCacheUtils.quotaList = quotaList;
    }

    public static List<Quota> getQuotaList() {
        return quotaList;
    }

    public static void setStdTableMap(Map<String, StdTable> stdTableMap){
        DetectCacheUtils.stdTableMap = stdTableMap;
    }

    public static StdTable getStdTable(String tableName){
        return stdTableMap != null ? stdTableMap.get(tableName):null;
    }

    public static List<EtlTranRule> getEtlTranRuleListCache() {
        return DetectCacheUtils.etlTranRuleList;
    }

    public static void setEtlTranRuleListCache(List<EtlTranRule> etlTranRuleList) {
        DetectCacheUtils.etlTranRuleList = etlTranRuleList;
    }


}
