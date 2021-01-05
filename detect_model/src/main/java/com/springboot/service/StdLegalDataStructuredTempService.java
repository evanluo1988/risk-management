package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.StdLegalDataStructuredTemp;

import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/12/23 13:52
 * @Version 1.0
 */
public interface StdLegalDataStructuredTempService extends IService<StdLegalDataStructuredTemp> {
    List<StdLegalDataStructuredTemp> findByReqId(String reqId);
}
