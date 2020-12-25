package com.springboot.service;

import com.springboot.ApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WYSourceDataServiceTest extends ApplicationTest {
    @Autowired
    private WYSourceDataService wySourceDataService;

    @Test
    public void testGetIndustrialAndJusticeData() {
        String res =  wySourceDataService.getIndustrialAndJusticeData("广西南宁卓信商贸有限公司");
        System.out.println(res);
    }

}
