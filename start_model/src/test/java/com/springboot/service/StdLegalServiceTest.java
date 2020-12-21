package com.springboot.service;

import com.springboot.ApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StdLegalServiceTest extends ApplicationTest {
    @Autowired
    private StdLegalService stdLegalService;

    @Test
    public void testCreateStdLegalMidTable() {
        stdLegalService.createStdLegalMidTable("1b20d84f-3e71-41c6-8430-abd41af63016");
    }
}
