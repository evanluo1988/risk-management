package com.springboot.service.remote;

import com.springboot.model.remote.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 微云接口
 * @Author 刘宏飞
 * @Date 2020/12/4 10:21
 * @Version 1.0
 */
@FeignClient(value = "wyRemoteService",url = "${wy.base.url}")
public interface WYRemoteService {



    @RequestMapping(value = "/exchange/entry/customer/datacollection",method = RequestMethod.POST)
    CustomerIndustrialAndJusticeResponse customerDataCollection(@RequestBody CustomerIndustrialAndJusticeRequest customerDataCollectionRequest);

    @RequestMapping(value = "/exchange/entry/customer/asip",method = RequestMethod.POST)
    CustomerIntellectualPropertyResponse customerBrandDataCollection(@RequestBody CustomerIntellectualPropertyRequest customerIntellectualPropertyRequest);


}
