package com.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.risk.QuotaValue;
import com.springboot.mapper.QuotaValueMapper;
import com.springboot.service.QuotaValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class QuotaValueServiceImpl extends ServiceImpl<QuotaValueMapper, QuotaValue> implements QuotaValueService {
    @Autowired
    private QuotaValueMapper quotaValueMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveQuotaValues(List<QuotaValue> quotaValueList) {
        if(CollectionUtils.isEmpty(quotaValueList)){
            return;
        }
        saveBatch(quotaValueList);
    }
}