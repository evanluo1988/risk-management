package com.springboot.service.impl;

import com.springboot.domain.TaskDisposition;
import com.springboot.mapper.TaskDispositionMapper;
import com.springboot.service.TaskDispositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class TaskDispositionServiceImpl implements TaskDispositionService {
    @Autowired
    private TaskDispositionMapper taskDispositionMapper;

    @Override
    @Transactional
    public void create(TaskDisposition taskDisposition) {
        taskDispositionMapper.insert(taskDisposition);
    }
}
