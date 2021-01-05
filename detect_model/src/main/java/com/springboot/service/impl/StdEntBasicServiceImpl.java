package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.StdEntBasic;
import com.springboot.mapper.StdEntBasicMapper;
import com.springboot.service.StdEntBasicService;
import org.springframework.stereotype.Service;

@Service
public class StdEntBasicServiceImpl extends ServiceImpl<StdEntBasicMapper, StdEntBasic> implements StdEntBasicService {
    @Override
    public StdEntBasic getStdEntBasicByReqId(String reqId) {
        LambdaQueryWrapper<StdEntBasic> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StdEntBasic::getReqId, reqId);
        return getOne(queryWrapper);
    }
}
