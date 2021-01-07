package com.springboot.service;

import com.springboot.ApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/11/27 14:39
 * @Version 1.0
 */
public class AreaServiceTest extends ApplicationTest {

    @Autowired
    private AreaService areaService;

    @Test
    public void testFindAreaIdsById(){
        List<Long> areaIdsById = areaService.findAreaIdsById(101L);
        System.out.println(areaIdsById);
    }
}
