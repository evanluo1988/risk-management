package com.springboot.service;

import com.springboot.ApplicationTest;
import com.springboot.domain.EtlTranRule;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EtlTranRuleServiceTest extends ApplicationTest {
    @Autowired
    private EtlTranRuleService etlTranRuleService;

    @Test
    public void testFindEnableRules() {
        List<EtlTranRule> etlTranRuleList = etlTranRuleService.findEnableRules();
        Assert.assertEquals(etlTranRuleList.size(), 22);
    }
}
