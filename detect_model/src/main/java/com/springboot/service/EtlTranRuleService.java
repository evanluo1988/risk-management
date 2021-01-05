package com.springboot.service;

import com.springboot.domain.EtlTranRule;

import java.util.List;

public interface EtlTranRuleService {
    public List<EtlTranRule> findEnableRules();
}
