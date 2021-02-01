package com.springboot.service;

import com.springboot.ApplicationTest;
import com.springboot.component.ReduRatioRegcap;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lhf
 * @version 1.0
 * @date 2021/1/29 14:50
 */
public class ReduRatioRegcapTest extends ApplicationTest {

    @Autowired
    private ReduRatioRegcap reduRatioRegcap;

    @Test
    public void test(){
        String reqId =  "CPZRCQENT068012";
        reduRatioRegcap.execQuota(reqId);
    }
}
