package com.springboot.mapper;

import com.springboot.ApplicationTest;
import com.springboot.domain.StdEntAlter;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StdEntAlterMapperTest extends ApplicationTest {
    @Autowired
    private StdEntAlterMapper stdEntAlterMapper;

    @Test
    public void testFindInvestorWithdrawList() {
        List<StdEntAlter> stdEntAlterList = stdEntAlterMapper.findInvestorWithdrawList("fb8872e5-804f-4faa-adca-d040d6d9369a");
        for(StdEntAlter stdEntAlter : stdEntAlterList) {

        }

    }
}
