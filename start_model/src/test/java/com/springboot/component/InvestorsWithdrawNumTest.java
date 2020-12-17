package com.springboot.component;

import com.springboot.ApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class InvestorsWithdrawNumTest extends ApplicationTest {
    @Autowired
    private InvestorsWithdrawNum investorsWithdrawNum;

    @Test
    public void testExecQuota() {
        String reqId = "fb8872e5-804f-4faa-adca-d040d6d9369a";
        investorsWithdrawNum.execQuota(reqId);

    }
}
