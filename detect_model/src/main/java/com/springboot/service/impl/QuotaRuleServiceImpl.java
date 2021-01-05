package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.Quota;
import com.springboot.mapper.QuotaMapper;
import com.springboot.service.QuotaRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuotaRuleServiceImpl extends ServiceImpl<QuotaMapper, Quota> implements QuotaRuleService {

    @Autowired
    private QuotaMapper quotaRuleMapper;

    @Override
    public List<Quota> findEnableQuotaRules() {
        LambdaQueryWrapper<Quota> queryWrapper = new LambdaQueryWrapper<Quota>()
                .eq(Quota::getEnable, "Y");
        return list(queryWrapper);
    }
}
