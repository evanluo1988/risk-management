package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.StdLegalCasemedian;
import com.springboot.mapper.StdLegalCasemedianMapper;
import com.springboot.service.StdLegalCasemedianService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class StdLegalCasemedianServiceImpl extends ServiceImpl<StdLegalCasemedianMapper, StdLegalCasemedian> implements StdLegalCasemedianService {

    @Override
    public List<StdLegalCasemedian> findStdLegalCasemedianBySerialNos(String reqId,List<String> serialNos) {
        if (CollectionUtils.isEmpty(serialNos)){
            return Collections.emptyList();
        }

        LambdaQueryWrapper<StdLegalCasemedian> queryWrapper = new LambdaQueryWrapper<StdLegalCasemedian>()
                .eq(StdLegalCasemedian::getReqId, reqId)
                .in(StdLegalCasemedian::getSerialNo, serialNos);
        return list(queryWrapper);
    }
}
