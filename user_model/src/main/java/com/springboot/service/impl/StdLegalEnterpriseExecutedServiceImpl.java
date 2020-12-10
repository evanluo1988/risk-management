package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.risk.StdLegalEnterpriseExecuted;
import com.springboot.mapper.StdLegalEnterpriseExecutedMapper;
import com.springboot.service.StdLegalEnterpriseExecutedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StdLegalEnterpriseExecutedServiceImpl extends ServiceImpl<StdLegalEnterpriseExecutedMapper, StdLegalEnterpriseExecuted>  implements StdLegalEnterpriseExecutedService {
    @Autowired
    private StdLegalEnterpriseExecutedMapper stdLegalEnterpriseExecutedMapper;
    @Override
    public List<StdLegalEnterpriseExecuted> findStdLegalEnterpriseExecutedByReqId(String reqId) {
        LambdaQueryWrapper<StdLegalEnterpriseExecuted> queryWrapper = new LambdaQueryWrapper<StdLegalEnterpriseExecuted>()
                .eq(StdLegalEnterpriseExecuted::getReqId, reqId);
        return list(queryWrapper);
    }

    @Override
    public void delete(StdLegalEnterpriseExecuted stdLegalEnterpriseExecuted) {
        removeById(stdLegalEnterpriseExecuted.getId());
    }
}
