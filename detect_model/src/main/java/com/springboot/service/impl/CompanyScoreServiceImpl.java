package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.CompanyScore;
import com.springboot.mapper.CompanyScoreMapper;
import com.springboot.service.CompanyScoreService;
import org.springframework.stereotype.Service;

/**
 * @author lhf
 * @date 2021/3/3 4:48 下午
 **/
@Service
public class CompanyScoreServiceImpl extends ServiceImpl<CompanyScoreMapper, CompanyScore> implements CompanyScoreService {
    @Override
    public CompanyScore getByReqId(String reqId) {
        final LambdaQueryWrapper<CompanyScore> queryWrapper = new LambdaQueryWrapper<CompanyScore>()
                .eq(CompanyScore::getReqId, reqId);
        return getOne(queryWrapper);
    }
}
