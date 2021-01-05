package com.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.domain.StdLegalDataStructuredTemp;
import com.springboot.model.LitigaInitiativeModel;
import com.springboot.model.VerdictResultModel;

import java.util.List;

public interface StdLegalDataStructuredTempMapper extends BaseMapper<StdLegalDataStructuredTemp> {
    public List<VerdictResultModel> getVerdictResult(String reqId);
    public List<LitigaInitiativeModel> getLitigaInitiativeResult(String reqId);
}
