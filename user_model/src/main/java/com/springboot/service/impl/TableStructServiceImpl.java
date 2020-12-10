package com.springboot.service.impl;

import com.google.common.collect.Maps;
import com.springboot.domain.risk.EtlTranRule;
import com.springboot.mapper.ExeSqlMapper;
import com.springboot.model.StdTable;
import com.springboot.service.TableStructService;
import com.springboot.utils.ServerCacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TableStructServiceImpl implements TableStructService {
    @Autowired
    private ExeSqlMapper exeSqlMapper;

    @Override
    public Map<String, StdTable> getStdTableStruct() {
        List<EtlTranRule> etlTranRuleList = ServerCacheUtils.getEtlTranRuleListCache();
        Map<String, StdTable> stdTableMap = Maps.newHashMap();
        String exeSql = "select column_name as COLUMN_NAME ,data_type as DATA_TYPE from information_schema.columns where table_name= ?";
        for(EtlTranRule etlTranRule : etlTranRuleList){
            String targetTableName = etlTranRule.getTargetTable();
            if(stdTableMap.containsKey(targetTableName)){
                continue;
            }
            List<HashMap> resultList = exeSqlMapper.exeSelectSql(exeSql.replace("?", "'" + targetTableName + "'"));
            if(!CollectionUtils.isEmpty(resultList)){
                StdTable stdTable = new StdTable();
                stdTable.setTableName(targetTableName);
                Map<String, String> columnTypeMap = Maps.newHashMap();
                stdTable.setColumnTypeMap(columnTypeMap);
                for(HashMap colTypePair : resultList){
                    for(Object key : colTypePair.keySet()){
                        columnTypeMap.put(colTypePair.get("COLUMN_NAME").toString(), colTypePair.get("DATA_TYPE").toString());
                    }
                }
                stdTableMap.put(targetTableName, stdTable);
            }
        }
        return stdTableMap;
    }
}
