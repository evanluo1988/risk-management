package com.springboot.service.impl;

import com.springboot.domain.TaskCheck;
import com.springboot.mapper.TaskCheckMapper;
import com.springboot.service.TaskCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class TaskCheckServiceImpl implements TaskCheckService {
    @Autowired
    private TaskCheckMapper taskCheckMapper;

    @Override
    @Transactional
    public void create(TaskCheck taskCheck) {
        taskCheckMapper.insert(taskCheck);
    }
}
