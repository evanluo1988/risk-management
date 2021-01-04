package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.risk.StdLegalEntUnexecutedTemp;
import com.springboot.domain.risk.StdLegalEnterpriseExecutedTemp;
import com.springboot.mapper.StdLegalEntUnexecutedTempMapper;
import com.springboot.service.StdLegalEntUnexecutedTempService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/12/23 14:00
 * @Version 1.0
 */
@Service
public class StdLegalEntUnexecutedTempServiceImpl extends ServiceImpl<StdLegalEntUnexecutedTempMapper, StdLegalEntUnexecutedTemp> implements StdLegalEntUnexecutedTempService {
    @Override
    public List<StdLegalEntUnexecutedTemp> findByReqId(String reqId) {
        LambdaQueryWrapper<StdLegalEntUnexecutedTemp> queryWrapper = new LambdaQueryWrapper<StdLegalEntUnexecutedTemp>()
                .eq(StdLegalEntUnexecutedTemp::getReqId, reqId);
        return list(queryWrapper);
    }
}
