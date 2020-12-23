package com.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.domain.risk.QuotaValue;
import com.springboot.model.QuotaModel;

import java.util.List;

public interface QuotaValueMapper extends BaseMapper<QuotaValue> {
    public List<QuotaModel> getQuotaModels(String reqId);
}
