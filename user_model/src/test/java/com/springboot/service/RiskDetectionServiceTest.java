package com.springboot.service;

import com.springboot.ApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RiskDetectionServiceTest extends ApplicationTest {
    @Autowired
    private RiskDetectionService riskDetectionService;

    @Test
    public void testCheckByEntName() {
        riskDetectionService.checkByEntName("广西南宁卓信商贸有限公司");
    }
}
