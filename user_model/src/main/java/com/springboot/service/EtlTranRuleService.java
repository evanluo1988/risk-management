package com.springboot.service;

import com.springboot.domain.risk.EtlTranRule;

import java.util.List;

public interface EtlTranRuleService {
    public List<EtlTranRule> findEnableRules();
}
