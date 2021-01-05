package com.springboot.service;

import com.springboot.domain.Quota;

import java.util.List;

public interface QuotaRuleService {
    public List<Quota> findEnableQuotaRules();
}
