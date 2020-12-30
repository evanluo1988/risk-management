package com.springboot.service;

import com.springboot.ApplicationTest;
import com.springboot.domain.risk.IaAsBrand;
import com.springboot.domain.risk.IaAsCopyright;
import com.springboot.model.IaAsPartentModel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class WYSourceDataServiceTest extends ApplicationTest {
    @Autowired
    private WYSourceDataService wySourceDataService;

    @Test
    public void testGetIndustrialAndJusticeData() {
        String res =  wySourceDataService.getIndustrialAndJusticeData("广西南宁卓信商贸有限公司");
        System.out.println(res);
    }

    @Test
    public void testGetPatentData() {
        List<IaAsPartentModel> res =  wySourceDataService.getPatentData("北大方正集团有限公司");
        System.out.println(res);
    }

    @Test
    public void testGetBrandData() {
        List<IaAsBrand> iaAsBrandList = wySourceDataService.getBrandData("广西南宁卓信商贸有限公司");
        System.out.println(iaAsBrandList);
    }

    @Test
    public void testGetCopyrightData() {
        List<IaAsCopyright> res =  wySourceDataService.getCopyrightData("北大方正集团有限公司");
        System.out.println(res);

    }


}
