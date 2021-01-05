package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.StdLegalCasemedianTemp;
import com.springboot.mapper.StdLegalCasemedianTempMapper;
import com.springboot.service.StdLegalCasemedianTempService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @Author 刘宏飞
 * @Date 2020/12/23 13:48
 * @Version 1.0
 */
@Service
public class StdLegalCasemedianTempServiceImpl extends ServiceImpl<StdLegalCasemedianTempMapper,StdLegalCasemedianTemp> implements StdLegalCasemedianTempService {
    @Override
    public List<StdLegalCasemedianTemp> findByReqAndSerialNos(String reqId, List<String> serialNos) {
        if (CollectionUtils.isEmpty(serialNos)){
            return Collections.emptyList();
        }

        LambdaQueryWrapper<StdLegalCasemedianTemp> queryWrapper = new LambdaQueryWrapper<StdLegalCasemedianTemp>()
                .eq(StdLegalCasemedianTemp::getReqId, reqId)
                .in(StdLegalCasemedianTemp::getSerialNo, serialNos);
        return list(queryWrapper);
    }
}
