package com.springboot.service;

import com.springboot.ApplicationTest;
import com.springboot.executor.QuotaSqlExector;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class QuotaSqlExectorTest extends ApplicationTest {
    @Autowired
    private QuotaSqlExector quotaSqlExector;

    @Test
    public void testReplaceRule() {
        String rule = quotaSqlExector.replaceRule("a083dadb-8f4c-47eb-b889-5233c901638c", "select sum(vu) from std_ia_partent where lssc = '1' and iasc like '%{entname}%' and req_id = ?");
        System.out.println(rule);
    }
}
