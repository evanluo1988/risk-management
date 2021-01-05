package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.StdLegalDataStructured;
import com.springboot.mapper.StdLegalDataStructuredMapper;
import com.springboot.service.StdLegalDataStructuredService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StdLegalDataStructuredServiceImpl extends ServiceImpl<StdLegalDataStructuredMapper, StdLegalDataStructured> implements StdLegalDataStructuredService {
    @Override
    public List<StdLegalDataStructured> findStdLegalDataStructuredByReqId(String reqId) {
        LambdaQueryWrapper<StdLegalDataStructured> queryWrapper = new LambdaQueryWrapper<StdLegalDataStructured>()
                .eq(StdLegalDataStructured::getReqId, reqId);
        return list(queryWrapper);
    }

    @Override
    public void delete(StdLegalDataStructured stdLegalDataStructured) {
        removeById(stdLegalDataStructured.getId());
    }
}
