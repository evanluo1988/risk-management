package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.StdLegalEntUnexecutedTemp;

import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/12/23 14:00
 * @Version 1.0
 */
public interface StdLegalEntUnexecutedTempService extends IService<StdLegalEntUnexecutedTemp> {
    List<StdLegalEntUnexecutedTemp> findByReqId(String reqId);
}
