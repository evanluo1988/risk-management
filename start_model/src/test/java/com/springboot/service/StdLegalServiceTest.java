package com.springboot.service;

import com.springboot.ApplicationTest;
import com.springboot.domain.risk.StdLegalDataStructured;
import com.springboot.service.impl.StdLegalServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StdLegalServiceTest extends ApplicationTest {
    @InjectMocks
    private StdLegalServiceImpl stdLegalService;

    @Mock
    private StdLegalDataStructuredService stdLegalDataStructuredService;

    @Test
    public void testCreateStdLegalMidTable() {
        List<StdLegalDataStructured> stdLegalDataStructuredList = Lists.newArrayList();

        StdLegalDataStructured s1 = new StdLegalDataStructured();
        stdLegalDataStructuredList.add(s1);

        Mockito.when(stdLegalDataStructuredService.findStdLegalDataStructuredByReqId("1b20d84f-3e71-41c6-8430-abd41af63016")).thenReturn(stdLegalDataStructuredList);

        stdLegalService.createStdLegalMidTable("1b20d84f-3e71-41c6-8430-abd41af63016");

    }
}
