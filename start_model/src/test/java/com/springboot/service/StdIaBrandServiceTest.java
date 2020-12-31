package com.springboot.service;

import com.springboot.ApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StdIaBrandServiceTest  extends ApplicationTest {
    @Autowired
    private StdIaBrandService stdIaBrandService;

    @Test
    public void testGetBrandVariety() {
        stdIaBrandService.getBrandVariety("69da0f11-e814-482f-b35b-df191f844079", false);
    }
}
