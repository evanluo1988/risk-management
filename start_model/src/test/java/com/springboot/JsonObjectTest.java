package com.springboot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.springboot.domain.IaAsBrand;
import com.springboot.model.IaAsPartentModel;

import java.util.List;

public class JsonObjectTest {
    public static void main(String[] args) {
        String data = "{\"code\":0,\"data\":{}}";

       // String[] datas = {data, data};
        List<String> datas = Lists.newArrayList();
        datas.add(data);
        datas.add(data);

       // System.out.println(datas);

        String jsonListStr1 = JSON.toJSONString(datas);

        System.out.println(jsonListStr1);



    }
}
