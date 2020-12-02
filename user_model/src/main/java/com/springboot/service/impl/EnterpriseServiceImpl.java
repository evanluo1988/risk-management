package com.springboot.service.impl;

import com.springboot.domain.Enterprise;
import com.springboot.mapper.EnterpriseMapper;
import com.springboot.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class EnterpriseServiceImpl implements EnterpriseService {

    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Override
    @Transactional
    public void create(Enterprise enterprise) {
        enterpriseMapper.insert(enterprise);
    }
}
