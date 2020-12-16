package com.springboot.service;

import com.springboot.ApplicationTest;
import com.springboot.model.StdGsEntInfoModel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author 刘宏飞
 * @Date 2020/12/16 16:06
 * @Version 1.0
 */
public class StdDataServiceTest extends ApplicationTest {
    @Autowired
    private StdDataService stdDataService;

    @Test
    public void test(){
        String reqId = "8ef1351a-62b8-4213-810e-3ac0461cd68d";
        StdGsEntInfoModel stdGsEntInfo = stdDataService.getStdGsEntInfo(reqId);
        System.out.println(stdGsEntInfo);
    }
}
