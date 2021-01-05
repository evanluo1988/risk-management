package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.StdIaCopyright;

import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/12/29 11:09
 * @Version 1.0
 */
public interface StdIaCopyrightService extends IService<StdIaCopyright> {

    /**
     * 根据ReqId查询记录
     * @param reqId
     * @return
     */
    List<StdIaCopyright> findByReqId(String reqId);
}
