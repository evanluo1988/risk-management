package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.risk.StdEntBasic;
import com.springboot.domain.risk.StdIaBrand;
import com.springboot.mapper.StdIaBrandMapper;
import com.springboot.service.StdEntBasicService;
import com.springboot.service.StdIaBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/12/29 11:09
 * @Version 1.0
 */
@Service
public class StdIaBrandServiceImpl extends ServiceImpl<StdIaBrandMapper, StdIaBrand> implements StdIaBrandService {
    @Autowired
    private StdEntBasicService stdEntBasicService;

    @Override
    public List<StdIaBrand> findByReqId(String reqId) {
        StdEntBasic stdEntBasicByReqId = stdEntBasicService.getStdEntBasicByReqId(reqId);

        LambdaQueryWrapper<StdIaBrand> queryWrapper = new LambdaQueryWrapper<StdIaBrand>()
                .eq(StdIaBrand::getReqId, reqId)
                .like(StdIaBrand::getApplicationName,stdEntBasicByReqId.getEntName());
        return list(queryWrapper);
    }
}
