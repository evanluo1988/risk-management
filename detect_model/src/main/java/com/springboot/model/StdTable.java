package com.springboot.model;

import lombok.Data;

import java.util.Map;

@Data
public class StdTable {
    private String tableName;
    Map<String, String> columnTypeMap;
}
