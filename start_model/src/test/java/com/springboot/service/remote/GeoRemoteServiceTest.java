package com.springboot.service.remote;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.springboot.ApplicationTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.AssertTrue;
import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/12/3 19:03
 * @Version 1.0
 */
public class GeoRemoteServiceTest extends ApplicationTest {
    @Autowired
    private GeoRemoteService geoRemoteService;
    @Value("${map.key}")
    private String key;

    @Test
    public void testGeo(){
        String location = "北京市朝阳区阜通东大街6号";
        GeoRemoteService.GeoResponse geo = geoRemoteService.geo(key, location);
        System.out.println(JSON.toJSONString(geo));
        Assert.assertTrue(geo.succ());
    }

    @Test
    public void testRegeo(){
        String location = "116.483038,39.990633";
        GeoRemoteService.ReGeoResponse regeo = geoRemoteService.regeo(key, location,"base", "false", "1");
        System.out.println(JSON.toJSONString(regeo));
        Assert.assertTrue(regeo.succ());
    }
}
