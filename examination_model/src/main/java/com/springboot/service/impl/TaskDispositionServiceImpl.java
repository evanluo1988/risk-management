package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.TaskDisposition;
import com.springboot.mapper.TaskDispositionMapper;
import com.springboot.service.TaskDispositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class TaskDispositionServiceImpl extends ServiceImpl<TaskDispositionMapper,TaskDisposition> implements TaskDispositionService {
    @Autowired
    private TaskDispositionMapper taskDispositionMapper;

    @Override
    @Transactional
    public void create(TaskDisposition taskDisposition) {
        taskDispositionMapper.insert(taskDisposition);
    }

    @Override
    public TaskDisposition getDispositionByTaskCheckId(Long taskCheckId) {
        if (Objects.isNull(taskCheckId)){
            return null;
        }

        LambdaQueryWrapper<TaskDisposition> queryWrapper = new LambdaQueryWrapper<TaskDisposition>()
                .eq(TaskDisposition::getTaskCheckId, taskCheckId)
                .orderByDesc(TaskDisposition::getCreateTime);
        return getOne(queryWrapper,false);
    }

    @Override
    public TaskDisposition getDispositionByTaskCheckIdAndProcessId(Long taskCheckId, Long processId) {
        if (Objects.isNull(taskCheckId)){
            return null;
        }

        LambdaQueryWrapper<TaskDisposition> queryWrapper = new LambdaQueryWrapper<TaskDisposition>()
                .eq(TaskDisposition::getTaskCheckId, taskCheckId)
                .eq(TaskDisposition::getProcessId,processId)
                .orderByDesc(TaskDisposition::getCreateTime);
        return getOne(queryWrapper,false);
    }
}
