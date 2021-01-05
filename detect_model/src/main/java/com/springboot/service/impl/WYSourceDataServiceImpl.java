package com.springboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.springboot.domain.IaAsBrand;
import com.springboot.domain.IaAsCopyright;
import com.springboot.model.IaAsPartentModel;
import com.springboot.model.remote.*;
import com.springboot.service.WYSourceDataService;
import com.springboot.service.remote.WYRemoteService;
import com.springboot.util.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class WYSourceDataServiceImpl implements WYSourceDataService {
    @Value("${wy.appid}")
    private String appId;

    @Value("${wy.appkey}")
    private String appKey;
    @Value("${wy.aeskey}")
    private String aesKey;
    @Value("${wy.max.page}")
    private Integer maxPage;

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
    public List<IaAsPartentModel> getPatentData(String entName) {
        LocalDateTime beginTime = LocalDateTime.now();
        int pageId = 1;
        String data = getIntellectualProperty(entName, pageId, "product_eds_patent");
        JSONObject jsonObject = JSONObject.parseObject(data);

        //解析页码
        String pageMsg = jsonObject.getJSONObject("R11A73").getString("msg");
        Integer totalPage = getTotalPage(pageMsg);
        JSONArray partentDataJsonArray = (JSONArray)jsonObject.getJSONObject("R11A73").get("data");
        List<IaAsPartentModel> iaAsPartentModelList = Lists.newArrayList();

        //得到第一页数据
        iaAsPartentModelList.addAll(JSON.parseArray(partentDataJsonArray.toString(), IaAsPartentModel.class));

        pageId++;
        if(totalPage != null) {
            while(pageId <= totalPage){
                data = getIntellectualProperty(entName, pageId, "product_eds_patent");
                partentDataJsonArray = (JSONArray)jsonObject.getJSONObject("R11A73").get("data");
                iaAsPartentModelList.addAll(JSON.parseArray(partentDataJsonArray.toString(), IaAsPartentModel.class));
                pageId++;
            }
        }

        Long opetime = Duration.between(beginTime,LocalDateTime.now()).toMillis();
        log.info("###########################"+opetime+"##############");
        return iaAsPartentModelList;
    }

    @Override
    public List<IaAsBrand> getBrandData(String entName) {
        LocalDateTime beginTime = LocalDateTime.now();
        int pageId = 1;
        String data = getIntellectualProperty(entName, pageId, "product_eds_trademark");
        if(data == null){
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(data);
        //解析页码
        String pageMsg = jsonObject.getJSONObject("R11A74").getString("msg");
        Integer totalPage = getTotalPage(pageMsg);
        JSONArray brandDataJsonArray = (JSONArray)jsonObject.getJSONObject("R11A74").get("data");
        List<IaAsBrand> iaAsBrandList = Lists.newArrayList();
        //得到第一页数据
        iaAsBrandList.addAll(JSON.parseArray(brandDataJsonArray.toString(), IaAsBrand.class));

        pageId++;
        if(totalPage != null) {
            while(pageId <= totalPage){
                data = getIntellectualProperty(entName, pageId, "product_eds_trademark");
                brandDataJsonArray = (JSONArray)jsonObject.getJSONObject("R11A74").get("data");
                iaAsBrandList.addAll(JSON.parseArray(brandDataJsonArray.toString(), IaAsBrand.class));
                pageId++;
            }
        }

        Long opetime = Duration.between(beginTime,LocalDateTime.now()).toMillis();
        log.info("###########################"+opetime+"##############");
        return iaAsBrandList;
    }


    @Override
    public List<IaAsCopyright> getCopyrightData(String entName) {
        LocalDateTime beginTime = LocalDateTime.now();
        int pageId = 1;
        String data = getIntellectualProperty(entName, pageId, "product_eds_copyright");
        if(data == null){
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(data);

        //解析页码
        String pageMsg = jsonObject.getJSONObject("R11A83").getString("msg");
        Integer totalPage = getTotalPage(pageMsg);
        JSONArray copyrightDataJsonArray = (JSONArray)jsonObject.getJSONObject("R11A83").get("data");
        List<IaAsCopyright> iaAsCopyrightList = Lists.newArrayList();

        //得到第一页数据
        iaAsCopyrightList.addAll(JSON.parseArray(copyrightDataJsonArray.toString(), IaAsCopyright.class));

        pageId++;
        if(totalPage != null) {
            while(pageId <= totalPage){
                data = getIntellectualProperty(entName, pageId, "product_eds_copyright");
                copyrightDataJsonArray = (JSONArray)jsonObject.getJSONObject("R11A83").get("data");
                iaAsCopyrightList.addAll(JSON.parseArray(copyrightDataJsonArray.toString(), IaAsCopyright.class));
                pageId++;
            }
        }

        Long opetime = Duration.between(beginTime,LocalDateTime.now()).toMillis();
        log.info("###########################"+opetime+"##############");

        return iaAsCopyrightList;
    }

    private Integer getTotalPage(String pageMsg) {
        String regEx = "[共]\\d+[页]";
        Pattern c = Pattern.compile(regEx);
        Matcher mc=c.matcher(pageMsg);
        if(mc.find()){
            String s = mc.group(0);
            int totalPage = Integer.valueOf(s.substring(1,s.length()-1));
            totalPage = (maxPage != null && maxPage < totalPage ? maxPage : totalPage);
            return totalPage;
        }
        return null;
    }

    private String getIntellectualProperty(String entName, int pageId, String productCode) {
        CustomerIntellectualPropertyRequest intellectualPropertyRequest = new CustomerIntellectualPropertyRequest();
        String businessId = StrUtils.randomStr(20);
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String sign = calcSign(businessId,timeStamp,appKey);
        intellectualPropertyRequest
                .setBusinessID(businessId)
                .setName(entName)
                .setNameType("1")
                .setPageId(pageId+"")
                .setProductCode(productCode)
                .setAppID(appId)
                .setTimestamp(timeStamp)
                .setSignature(sign);
        System.out.println("请求报文："+ JSON.toJSONString(intellectualPropertyRequest));

        CustomerIntellectualPropertyResponse customerBrandResponse = wyRemoteService.customerBrandDataCollection(intellectualPropertyRequest);
        return customerBrandResponse.getData();
    }
}
