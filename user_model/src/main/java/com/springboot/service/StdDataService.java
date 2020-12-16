package com.springboot.service;

import com.springboot.model.StdGsEntInfoModel;

/**
 * @Author 刘宏飞
 * @Date 2020/12/16 14:23
 * @Version 1.0
 */
public interface StdDataService {
    /**
     * 获取
     * @param reqId
     */
    StdGsEntInfoModel getStdGsEntInfo(String reqId);
}
