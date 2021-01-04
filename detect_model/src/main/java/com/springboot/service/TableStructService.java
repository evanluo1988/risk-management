package com.springboot.service;

import com.springboot.model.StdTable;

import java.util.List;
import java.util.Map;

public interface TableStructService {
    Map<String, StdTable> getStdTableStruct();
}
