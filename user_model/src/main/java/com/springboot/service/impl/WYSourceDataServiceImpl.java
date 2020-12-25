package com.springboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springboot.model.remote.CustomerIndustrialAndJusticeRequest;
import com.springboot.model.remote.CustomerIndustrialAndJusticeResponse;
import com.springboot.model.remote.CustomerIntellectualPropertyRequest;
import com.springboot.model.remote.CustomerIntellectualPropertyResponse;
import com.springboot.service.WYSourceDataService;
import com.springboot.service.remote.WYRemoteService;
import com.springboot.util.StrUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WYSourceDataServiceImpl implements WYSourceDataService {
    @Value("${wy.appid}")
    private String appId;

    @Value("${wy.appkey}")
    private String appKey;
    @Value("${wy.aeskey}")
    private String aesKey;

    @Autowired
    private WYRemoteService wyRemoteService;

    private String calcSign(String businessId, String timeStamp, String appKey) {
        String originStr = businessId+timeStamp+appKey;
        String md5 = DigestUtils.md5Hex(originStr);
        return md5;
    }

    @Override
    public String getIndustrialAndJusticeData(String entName) {

        CustomerIndustrialAndJusticeRequest customerDataCollectionRequest = new CustomerIndustrialAndJusticeRequest();
        String businessId = StrUtils.randomStr(20);
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String sign = calcSign(businessId,timeStamp,appKey);
        customerDataCollectionRequest
                .setBusinessID(businessId)
                .setEntName(entName)
                .setEntCreditID("911112345671234567")
                .setIndName("黄日林")
                .setIndCertID("uTln8yoWNBcFDHgUv24IXvQTVoGyDvrYzKvAcHzbyhM=")
                //.setProductCode(productCode)
                .setAppID(appId)
                .setTimestamp(timeStamp)
                .setSignature(sign);

        System.out.println("请求报文："+ JSON.toJSONString(customerDataCollectionRequest));
        CustomerIndustrialAndJusticeResponse customerDataCollectionResponse = wyRemoteService.customerDataCollection(customerDataCollectionRequest);

        return JSON.toJSONString(customerDataCollectionResponse);
    }

    @Override
    public String getIntellectualPropertyData(String entName) {
        CustomerIntellectualPropertyRequest intellectualPropertyRequest = new CustomerIntellectualPropertyRequest();
        String businessId = StrUtils.randomStr(20);
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String sign = calcSign(businessId,timeStamp,appKey);
        intellectualPropertyRequest
                .setBusinessID(businessId)
                .setName(entName)
                .setNameType("")
                .setPageId("1")
                .setProductCode("")
                .setAppID(appId)
                .setTimestamp(timeStamp)
                .setSignature(sign);
        System.out.println("请求报文："+ JSON.toJSONString(intellectualPropertyRequest));

        CustomerIntellectualPropertyResponse customerIntellectualPropertyResponse = wyRemoteService.customerDataCollection(intellectualPropertyRequest);
        return JSON.toJSONString(customerIntellectualPropertyResponse);
    }

}
