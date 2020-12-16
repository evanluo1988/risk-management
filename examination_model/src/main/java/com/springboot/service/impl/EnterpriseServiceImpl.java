package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.Enterprise;
import com.springboot.mapper.EnterpriseMapper;
import com.springboot.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class EnterpriseServiceImpl extends ServiceImpl<EnterpriseMapper,Enterprise> implements EnterpriseService {

    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Override
    @Transactional
    public void create(Enterprise enterprise) {
        enterpriseMapper.insert(enterprise);
    }

    @Override
    public Enterprise getEnterpriseById(Long id) {
        if (Objects.isNull(id)){
            return null;
        }
        LambdaQueryWrapper<Enterprise> queryWrapper = new LambdaQueryWrapper<Enterprise>()
                .eq(Enterprise::getId, id);
        return getOne(queryWrapper,false);
    }
}
