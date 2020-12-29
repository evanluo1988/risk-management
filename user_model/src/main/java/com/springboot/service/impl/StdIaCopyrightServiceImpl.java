package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.risk.StdEntBasic;
import com.springboot.domain.risk.StdIaCopyright;
import com.springboot.mapper.StdIaCopyrightMapper;
import com.springboot.service.StdEntBasicService;
import com.springboot.service.StdIaCopyrightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/12/29 11:10
 * @Version 1.0
 */
@Service
public class StdIaCopyrightServiceImpl extends ServiceImpl<StdIaCopyrightMapper, StdIaCopyright> implements StdIaCopyrightService {
    @Autowired
    private StdEntBasicService stdEntBasicService;

    @Override
    public List<StdIaCopyright> findByReqId(String reqId) {
        StdEntBasic stdEntBasicByReqId = stdEntBasicService.getStdEntBasicByReqId(reqId);
        LambdaQueryWrapper<StdIaCopyright> queryWrapper = new LambdaQueryWrapper<StdIaCopyright>()
                .eq(StdIaCopyright::getReqId, reqId)
                .like(StdIaCopyright::getCopyrightOwner,stdEntBasicByReqId.getEntName());
        return list(queryWrapper);
    }
}
