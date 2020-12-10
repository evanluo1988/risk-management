package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.risk.EdsGsBasic;
import com.springboot.mapper.EdsGsBasicMapper;
import com.springboot.service.EdsGsBasicService;
import org.springframework.stereotype.Service;

@Service
public class EdsGsBasicServiceImpl extends ServiceImpl<EdsGsBasicMapper, EdsGsBasic> implements EdsGsBasicService {
    @Override
    public EdsGsBasic getEdsGsBasicByReqId(String reqId) {
        LambdaQueryWrapper<EdsGsBasic> queryWrapper = new LambdaQueryWrapper<EdsGsBasic>()
                .eq(EdsGsBasic::getReqId, reqId);
        return getOne(queryWrapper,false);
    }
}
