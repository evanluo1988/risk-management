package com.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.domain.risk.StdLegalDataStructuredTemp;
import com.springboot.model.VerdictResultModel;

import java.util.List;

public interface StdLegalDataStructuredTempMapper extends BaseMapper<StdLegalDataStructuredTemp> {
    public List<VerdictResultModel> getVerdictResult(String reqId);
}
