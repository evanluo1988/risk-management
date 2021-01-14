package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springboot.domain.CloudQueryLog;
import com.springboot.mapper.CloudQueryLogMapper;
import com.springboot.service.CloudQueryLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CloudQueryLogServiceImpl implements CloudQueryLogService {
    @Autowired
    private CloudQueryLogMapper cloudQueryLogMapper;
    @Override
    public void create(CloudQueryLog cloudQueryLog) {
        cloudQueryLogMapper.insert(cloudQueryLog);
    }

    @Override
    public CloudQueryLog getByReqId(String reqId) {
        QueryWrapper<CloudQueryLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("req_id", reqId);
        return cloudQueryLogMapper.selectOne(queryWrapper);
    }

    @Override
    public void update(CloudQueryLog cloudQueryLog) {
        cloudQueryLogMapper.updateById(cloudQueryLog);
    }
}
