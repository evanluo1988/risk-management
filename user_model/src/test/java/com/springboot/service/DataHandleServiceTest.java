package com.springboot.service;

import com.springboot.ApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DataHandleServiceTest extends ApplicationTest {
    @Autowired
    private DataHandleService dataHandleService;

    @Test
    public void testHandelData(){
        dataHandleService.handelData("广西南宁卓信商贸有限公司");
    }

    @Test
    public void testCulQuotas() {
        String reqId = "345aa469-1574-45c2-84cc-9e80b1346fd6";
        dataHandleService.culQuotas(reqId);
    }

}
