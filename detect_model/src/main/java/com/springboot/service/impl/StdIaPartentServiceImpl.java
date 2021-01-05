package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.StdEntBasic;
import com.springboot.domain.StdIaPartent;
import com.springboot.mapper.StdIaPartentMapper;
import com.springboot.service.StdEntBasicService;
import com.springboot.service.StdIaPartentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/12/29 11:11
 * @Version 1.0
 */
@Service
public class StdIaPartentServiceImpl extends ServiceImpl<StdIaPartentMapper, StdIaPartent> implements StdIaPartentService {
    @Autowired
    private StdEntBasicService stdEntBasicService;

    @Override
    public List<StdIaPartent> findByReqId(String reqId) {
        StdEntBasic stdEntBasicByReqId = stdEntBasicService.getStdEntBasicByReqId(reqId);
        LambdaQueryWrapper<StdIaPartent> queryWrapper = new LambdaQueryWrapper<StdIaPartent>()
                .eq(StdIaPartent::getReqId, reqId)
                .like(StdIaPartent::getIasc,stdEntBasicByReqId.getEntName());
        return list(queryWrapper);
    }
}
