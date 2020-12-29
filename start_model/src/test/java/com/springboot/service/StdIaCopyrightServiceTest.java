package com.springboot.service;

import com.springboot.ApplicationTest;
import com.springboot.domain.risk.StdIaBrand;
import com.springboot.domain.risk.StdIaCopyright;
import com.springboot.domain.risk.StdIaPartent;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/12/29 13:49
 * @Version 1.0
 */
public class StdIaCopyrightServiceTest extends ApplicationTest {

    @Autowired
    private StdIaCopyrightService stdIaCopyrightService;
    @Autowired
    private StdIaPartentService stdIaPartentService;
    @Autowired
    private StdIaBrandService stdIaBrandService;

    @Test
    public void test1(){
        final String reqId = "a083dadb-8f4c-47eb-b889-5233c901638c";
        List<StdIaCopyright> byReqId = stdIaCopyrightService.findByReqId(reqId);
        List<StdIaPartent> byReqId1 = stdIaPartentService.findByReqId(reqId);
        List<StdIaBrand> byReqId2 = stdIaBrandService.findByReqId(reqId);
        System.out.println(byReqId);
    }
}
