package com.springboot.utils;

import com.springboot.model.StdTable;

import java.util.HashMap;

/**
 * @author evan
 * sql拼接
 */
public class SqlSplicingUtils {
    public static String getInsertSql(String targetTable, HashMap row){
        StdTable stdTable = DetectCacheUtils.getStdTable(targetTable);
        //通过targetTable找stdTable，如果没找到说明都按字符串规则拼接

        StringBuilder sb = new StringBuilder();
        sb.append("insert into " + targetTable +" ( ");

        StringBuilder sbValues = new StringBuilder();
        sbValues.append("VALUES (");

        for(Object column : row.keySet()){
            String columnName = column.toString();
            String value = row.get(column).toString();
            sb.append(columnName +", ");
            //根据类型初始化value
            if("".equals(value)){
                sbValues.append("null, ");
            }else{
                String sqlType = MySqlTypeUtils.MYSQL_TYPE_VARCHAR;
                if(stdTable != null){
                     sqlType = stdTable.getColumnTypeMap().get(columnName);
                }
                if(MySqlTypeUtils.MYSQL_TYPE_DECIMAL.equals(sqlType)){
                    sbValues.append(value +", ");
                } else{
                    sbValues.append(" '"+value + "', ");
                }
            }
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(" )");
        sbValues.deleteCharAt(sbValues.lastIndexOf(","));
        sbValues.append(" )");
        sb.append(sbValues.toString());

        return sb.toString();
    }
}
