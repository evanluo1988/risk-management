package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.StdLegalEntUnexecuted;
import com.springboot.mapper.StdLegalEntUnexecutedMapper;
import com.springboot.service.StdLegalEntUnexecutedService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StdLegalEntUnexecutedServiceImpl extends ServiceImpl<StdLegalEntUnexecutedMapper, StdLegalEntUnexecuted> implements StdLegalEntUnexecutedService {
    @Override
    public List<StdLegalEntUnexecuted> findStdLegalEntUnexecutedByReqId(String reqId) {
        LambdaQueryWrapper<StdLegalEntUnexecuted> queryWrapper = new LambdaQueryWrapper<StdLegalEntUnexecuted>()
                .eq(StdLegalEntUnexecuted::getReqId, reqId);
        return list(queryWrapper);
    }
}
