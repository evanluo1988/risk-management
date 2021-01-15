package com.springboot.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface ExeSqlMapper extends BaseMapper {
    public List<HashMap> exeSelectSql(String sql);
    public void exeInsertSql(String insertSql);
    public HashMap exeQuotaSql(String sql);

    void deleteByTableNameAndReqId(@Param("tableName") String tableName,@Param("reqId") String reqId);
}
