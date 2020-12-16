package com.springboot.service;

import com.springboot.ApplicationTest;
import com.springboot.model.StdTable;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class TableStructServiceTest extends ApplicationTest {
    @Autowired
    private TableStructService tableStructService;

    @Test
    public void testGetStdTableStruct(){
        Map<String, StdTable> stdTableMap = tableStructService.getStdTableStruct();
    }
}
