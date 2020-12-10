package com.springboot.mapper;

import java.util.HashMap;
import java.util.List;

public interface ExeSqlMapper {
    public List<HashMap> exeSelectSql(String sql);
    public void exeInsertSql(String insertSql);
    public HashMap exeQuotaSql(String sql);
}
