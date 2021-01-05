package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.StdLegalDataStructuredTemp;
import com.springboot.mapper.StdLegalDataStructuredTempMapper;
import com.springboot.service.StdLegalDataStructuredTempService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/12/23 13:53
 * @Version 1.0
 */
@Service
public class StdLegalDataStructuredTempServiceImpl extends ServiceImpl<StdLegalDataStructuredTempMapper, StdLegalDataStructuredTemp> implements StdLegalDataStructuredTempService {
    @Override
    public List<StdLegalDataStructuredTemp> findByReqId(String reqId) {
        LambdaQueryWrapper<StdLegalDataStructuredTemp> queryWrapper = new LambdaQueryWrapper<StdLegalDataStructuredTemp>()
                .eq(StdLegalDataStructuredTemp::getReqId, reqId);
        return list(queryWrapper);
    }
}
