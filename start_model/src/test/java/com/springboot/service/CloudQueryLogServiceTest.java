package com.springboot.service;

import com.springboot.ApplicationTest;
import com.springboot.domain.CloudQueryLog;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CloudQueryLogServiceTest extends ApplicationTest {
    @Autowired
    private CloudQueryLogService cloudQueryLogService;

    @Test
    public void testGetByReqId() {
        CloudQueryLog cloudQueryLog = cloudQueryLogService.getByReqId("f3914531-6e76-4915-a9fa-dc694c3aa40e");
        System.out.println(cloudQueryLog);

    }
}
