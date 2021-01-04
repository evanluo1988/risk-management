package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.risk.StdLegalEnterpriseExecutedTemp;
import com.springboot.mapper.StdLegalEnterpriseExecutedTempMapper;
import com.springboot.service.StdLegalEnterpriseExecutedTempService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/12/23 13:58
 * @Version 1.0
 */
@Service
public class StdLegalEnterpriseExecutedTempServiceImpl extends ServiceImpl<StdLegalEnterpriseExecutedTempMapper, StdLegalEnterpriseExecutedTemp> implements StdLegalEnterpriseExecutedTempService {
    @Override
    public List<StdLegalEnterpriseExecutedTemp> findByReqId(String reqId) {
        LambdaQueryWrapper<StdLegalEnterpriseExecutedTemp> queryWrapper = new LambdaQueryWrapper<StdLegalEnterpriseExecutedTemp>()
                .eq(StdLegalEnterpriseExecutedTemp::getReqId, reqId);
        return list(queryWrapper);
    }
}
