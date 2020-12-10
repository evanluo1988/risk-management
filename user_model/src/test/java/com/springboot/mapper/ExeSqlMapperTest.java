package com.springboot.mapper;

import com.springboot.ApplicationTest;
import lombok.Data;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExeSqlMapperTest extends ApplicationTest {
    @Autowired
    private ExeSqlMapper exeSqlMapper;

    public final static String SQL_NUMBER = "DECIMAL";


    @Data
    static class StdTable{
        private String tableName;
        Map<String, String> columnValueMap;
    }

    @Test
    public void testExeSql(){

        StdTable stdTable = new StdTable();
        stdTable.setTableName("std_ent_lrinv_list");
        Map<String, String> columnValueMap = new HashMap<>();

        columnValueMap.put("subconam", SQL_NUMBER);
        stdTable.setColumnValueMap(columnValueMap);

        String reqId = "adea9f6f-0e1e-4cce-96b5-bad564a094cd";
        String exesql = "select  \n" +
                "frname as lrname,  \n" +
                "entname  as inventname,\n" +
                "regno    as invregno  ,  \n" +
                "enttype  as inventtype,  \n" +
                "regcap   as invregcap , \n" +
                "regcapcur  as   regcapcur,  \n" +
                "entstatus  as   regstatus, \n" +
                "candate    as   candate  , \n" +
                "revdate    as   revdate  ,\n" +
                "regorg     as   regorg   ,  \n" +
                "subconam   as   subconam ,  \n" +
                "currency   as   currency ,  \n" +
                "fundedratio  as   fundedratio   ,\n" +
                "esdate       as   esdate    \n" +
                "from eds_gs_fddbrdwtzxx where req_id = ?";

        String selectExeSql = exesql.replace("?", "'"+reqId+"'");
        List<HashMap> listMap = exeSqlMapper.exeSelectSql(selectExeSql);
        String targetTable = "std_ent_lrinv_list";

        if(targetTable.equalsIgnoreCase(stdTable.getTableName())){

            for(HashMap row : listMap){
                StringBuilder sb = new StringBuilder();
                sb.append("insert into " + targetTable +" ( ");

                StringBuilder sbValues = new StringBuilder();
                sbValues.append("VALUES (");

                for(Object column : row.keySet()){
                    String columnName = column.toString();
                    String value = row.get(column).toString();
                    sb.append(columnName +", ");

                    //根据类型初始化value
                    String sqlType = stdTable.getColumnValueMap().get(columnName);
                    if("".equals(value)){
                        sbValues.append("null, ");
                    }else{
                        if(SQL_NUMBER.equals(sqlType)){
                            sbValues.append(value +", ");
                        } else{
                            //默认为字符串
                            sbValues.append(" '"+value + "', ");
                        }
                    }
                }
                sb.deleteCharAt(sb.lastIndexOf(","));
                sb.append(" )");
                sbValues.deleteCharAt(sbValues.lastIndexOf(","));
                sbValues.append(" )");

                sb.append(sbValues.toString());
                System.out.println(sb.toString());
            }

        }




    }
}
