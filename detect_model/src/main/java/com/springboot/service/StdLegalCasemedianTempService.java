package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.StdLegalCasemedianTemp;

import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/12/23 13:48
 * @Version 1.0
 */
public interface StdLegalCasemedianTempService extends IService<StdLegalCasemedianTemp> {
    List<StdLegalCasemedianTemp> findByReqAndSerialNos(String reqId, List<String> serialNos);
}
