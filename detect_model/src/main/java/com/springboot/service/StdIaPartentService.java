package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.StdIaPartent;

import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/12/29 11:09
 * @Version 1.0
 */
public interface StdIaPartentService extends IService<StdIaPartent> {

    /**
     * 根据ReqId查询
     * @param reqId
     * @return
     */
    List<StdIaPartent> findByReqId(String reqId);
}
