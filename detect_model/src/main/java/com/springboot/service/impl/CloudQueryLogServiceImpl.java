package com.springboot.service.impl;

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
}
