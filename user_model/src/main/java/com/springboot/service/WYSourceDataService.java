package com.springboot.service;

import com.alibaba.fastjson.JSONObject;
import com.springboot.model.remote.CustomerIndustrialAndJusticeResponse;

public interface WYSourceDataService {
    /**
     * 通过企业名称获取工商司法数据
     * @param entName
     * @return
     */
    public String getIndustrialAndJusticeData(String entName);

    public String getIntellectualPropertyData(String entName);

}
