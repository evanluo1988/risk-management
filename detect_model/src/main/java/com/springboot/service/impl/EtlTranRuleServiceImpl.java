package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.EtlTranRule;
import com.springboot.mapper.EtlTranRuleMapper;
import com.springboot.service.EtlTranRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtlTranRuleServiceImpl extends ServiceImpl<EtlTranRuleMapper, EtlTranRule> implements EtlTranRuleService {
    @Override
    public List<EtlTranRule> findEnableRules() {
        LambdaQueryWrapper<EtlTranRule> queryWrapper = new LambdaQueryWrapper<EtlTranRule>()
                .eq(EtlTranRule::getStatus, "Y");
        return list(queryWrapper);
    }
}
