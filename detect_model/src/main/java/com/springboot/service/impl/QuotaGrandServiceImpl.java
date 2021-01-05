package com.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.QuotaGrand;
import com.springboot.mapper.QuotaGrandMapper;
import com.springboot.service.QuotaGrandService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuotaGrandServiceImpl extends ServiceImpl<QuotaGrandMapper, QuotaGrand> implements QuotaGrandService {
    @Override
    public List<QuotaGrand> findQuotaGrandList() {
        return list();
    }
}
