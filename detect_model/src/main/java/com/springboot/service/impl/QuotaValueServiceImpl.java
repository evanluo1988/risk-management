package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.QuotaValue;
import com.springboot.mapper.QuotaValueMapper;
import com.springboot.model.QuotaModel;
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

    @Override
    public List<QuotaModel> getQuotaList(String reqId) {
        return quotaValueMapper.getQuotaModels(reqId);
    }

    @Override
    public int countQuotaValues(String reqId) {
        LambdaQueryWrapper<QuotaValue> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(QuotaValue::getReqId, reqId);
        return count(queryWrapper);
    }
}
