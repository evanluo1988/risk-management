package com.springboot;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.collect.Lists;
import com.springboot.mapper.ExeSqlMapper;
import lombok.Data;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ComparisonData extends ApplicationTest {
    @Autowired
    private ExeSqlMapper exeSqlMapper;

    @Test
    public void importData() {
        EasyExcel.read("C:\\Users\\86152\\Desktop\\金融打非\\取数\\映射数据\\工商数据.xlsx", ExcelData.class, new DataUploadDataListener(exeSqlMapper)).sheet("映射关系-字段").headRowNumber(1).doRead();
        EasyExcel.read("C:\\Users\\86152\\Desktop\\金融打非\\取数\\映射数据\\司法数据.xlsx", ExcelData.class, new DataUploadDataListener(exeSqlMapper)).sheet("映射关系-字段").headRowNumber(1).doRead();
        EasyExcel.read("C:\\Users\\86152\\Desktop\\金融打非\\取数\\映射数据\\无形资产.xlsx", ExcelData.class, new DataUploadDataListener(exeSqlMapper)).sheet("映射关系-字段").headRowNumber(1).doRead();
    }

    private static class DataUploadDataListener extends AnalysisEventListener<ExcelData> {
        private ExeSqlMapper exeSqlMapper;
        public DataUploadDataListener(ExeSqlMapper exeSqlMapper) {
            this.exeSqlMapper = exeSqlMapper;
        }

        private Collection<ExcelData> datas = Lists.newArrayList();

        @Override
        public void invoke(ExcelData excelData, AnalysisContext analysisContext) {
            datas.add(excelData);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
            Map<String, List<ExcelData>> dataMap = datas.stream().collect(Collectors.groupingBy(item -> item.getOrgTableName() + "#" + item.getStdTableName()));
            for(String tableName : dataMap.keySet()) {
                String orgTableName = tableName.split("#")[0];
                String stdTableName = tableName.split("#")[1];

                List<TableData> orgTableData = initTableData(orgTableName);
                List<TableData> stdTableData = initTableData(stdTableName);
                //校验标准表
                List<ExcelData> excelDataList = dataMap.get(tableName);
                boolean valid1 = validStdTableColumns(stdTableData, excelDataList);

                //校验转换规则
                boolean valid2 = validTransferOrgToStd(orgTableName, stdTableName, excelDataList);
                if(valid1 && valid2){
                    System.out.println("表" + tableName + "校验正确！");
                }
            }
        }

        private boolean validTransferOrgToStd(String orgTableName, String stdTableName, List<ExcelData> excelDataList) {
            String tranRule = getTransferRule(orgTableName, stdTableName);
            List<String> columns = getColumnsAfterAs(tranRule);
            boolean valid = true;
            for(ExcelData excelData : excelDataList) {
                if(!columns.contains(excelData.getStdTableField().toLowerCase())) {
                    valid = false;
                    System.out.println("validTransferOrgToStd校验标准表"+excelData.getStdTableName()+" 字段 （" + excelData.getStdTableField() + "）有问题");
                }
            }
            return valid;
        }

        private List<String> getColumnsAfterAs(String tranRule) {
            List<String> columns = Lists.newArrayList();
            Pattern pattern = Pattern.compile("as (.*?)[,| ]");
            Matcher matcher = pattern.matcher(tranRule);
            while(matcher.find()){
                columns.add(matcher.group(1));
            }
            return columns;
        }

        private String getTransferRule(String orgTableName, String stdTableName) {
            String sql = "select tran_rule from etl_tran_rules where source_table = '"+orgTableName+"' and target_table = '"+stdTableName+"'";
            List<HashMap> hashMapList = exeSqlMapper.exeSelectSql(sql);
            return hashMapList.get(0).get("tran_rule").toString();
        }

        private boolean validStdTableColumns(List<TableData> tableDataList, List<ExcelData> excelDataList) {
            List<String> stdTableColumns = tableDataList.stream().map(TableData::getColumnName).collect(Collectors.toList());
            boolean valid = true;
            if(tableDataList.size() != excelDataList.size()){
                valid = false;
                System.out.println("validTableColumns校验标准表"+excelDataList.get(0).getStdTableName()+" 字段列数不一样 （数据库中的大小为:"+tableDataList.size()+" Excel中列数为："+excelDataList.size()+"）");
            }
            for(ExcelData excelData : excelDataList) {
                if(!stdTableColumns.contains(excelData.getStdTableField().toLowerCase())){
                    valid = false;
                    System.out.println("validTableColumns校验标准表"+excelData.getStdTableName()+" 字段 （" + excelData.getStdTableField() + "）有问题");
                }
            }
            return valid;
        }

        private List<TableData> initTableData(String tableName) {
            String sql = "SELECT\n" +
                    "    TABLE_NAME AS 'tableName',\n" +
                    "    COLUMN_NAME AS 'columnName',\n" +
                    "    DATA_TYPE AS 'dataType'\n" +
                    "FROM\n" +
                    "    information_schema.`COLUMNS`\n" +
                    "where table_name = ?";

            List<HashMap> hashMapList = exeSqlMapper.exeSelectSql(sql.replace("?", "'"+tableName+"'"));
            return initTableData(hashMapList);
        }

        private List<TableData> initTableData(List<HashMap> hashMapList) {
            List<TableData> tableDataList = Lists.newArrayList();
            List<String> exceptColumns = Lists.newArrayList(new String[]{"id","req_id","businessid","create_time","update_time","create_by","update_by"});
            for(HashMap map : hashMapList) {
                String columnName = map.get("columnName").toString();
                if(!exceptColumns.contains(columnName)) {
                    TableData tableData = new TableData();
                    tableData.setTableName(map.get("tableName").toString());
                    tableData.setColumnName(map.get("columnName").toString());
                    tableData.setDataType(map.get("dataType").toString());
                    tableDataList.add(tableData);
                }
            }
            return tableDataList;
        }
    }

    @Data
    private static class TableData{
        private String tableName;
        private String columnName;
        private String dataType;
    }
}
