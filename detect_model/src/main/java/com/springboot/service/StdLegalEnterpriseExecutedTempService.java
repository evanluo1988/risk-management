package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.StdLegalEnterpriseExecutedTemp;

import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/12/23 13:57
 * @Version 1.0
 */
public interface StdLegalEnterpriseExecutedTempService extends IService<StdLegalEnterpriseExecutedTemp> {
    List<StdLegalEnterpriseExecutedTemp> findByReqId(String reqId);
}
