package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.EntWyBasic;
import com.springboot.mapper.EntWyBasicMapper;
import com.springboot.service.EdsGsBasicService;
import org.springframework.stereotype.Service;

@Service
public class EdsGsBasicServiceImpl extends ServiceImpl<EntWyBasicMapper, EntWyBasic> implements EdsGsBasicService {
    @Override
    public EntWyBasic getEdsGsBasicByReqId(String reqId) {
        LambdaQueryWrapper<EntWyBasic> queryWrapper = new LambdaQueryWrapper<EntWyBasic>()
                .eq(EntWyBasic::getReqId, reqId);
        return getOne(queryWrapper,false);
    }
}
