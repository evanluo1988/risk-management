package com.springboot.service.remote;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.ApplicationTest;
import com.springboot.model.RemoteDataModel;
import com.springboot.util.StrUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @Author 刘宏飞
 * @Date 2020/12/4 10:28
 * @Version 1.0
 */
public class WYRemoteServiceTest extends ApplicationTest {
    @Value("${wy.product.code}")
    private String productCode;

    @Value("${wy.appid}")
    private String appId;

    @Value("${wy.appkey}")
    private String appKey;
    @Value("${wy.aeskey}")
    private String aesKey;

    @Autowired
    private WYRemoteService wyRemoteService;

    @Test
    public void testCustomerDataCollection() {
        WYRemoteService.CustomerDataCollectionRequest customerDataCollectionRequest = new WYRemoteService.CustomerDataCollectionRequest();
        String businessId = StrUtils.randomStr(20);
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String sign = WYRemoteService.calcSign(businessId,timeStamp,appKey);
        customerDataCollectionRequest
                .setBusinessID(businessId)
                .setEntName("广西南宁卓信商贸有限公司")
                .setEntCreditID("911112345671234567")
                .setIndName("黄日林")
                .setIndCertID("uTln8yoWNBcFDHgUv24IXvQTVoGyDvrYzKvAcHzbyhM=")
                .setProductCode(productCode)
                .setAppID(appId)
                .setTimestamp(timeStamp)
                .setSignature(sign);

        System.out.println("请求报文："+JSON.toJSONString(customerDataCollectionRequest));
        WYRemoteService.CustomerDataCollectionResponse customerDataCollectionResponse = wyRemoteService.customerDataCollection(customerDataCollectionRequest);
        System.out.println("响应报文："+JSON.toJSONString(customerDataCollectionResponse));

        JSONObject jsonObject = JSONObject.parseObject(customerDataCollectionResponse.getData());
        JSONArray dataJsonArr = (JSONArray)jsonObject.getJSONObject("R11C53").get("data");
       // RemoteDataModel data = JSON.parseObject(dataJsonArr.get(0).toString(), RemoteDataModel.class);
    }
}
