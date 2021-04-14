package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.springboot.domain.TaskProcess;
import com.springboot.enums.ProcessTypeEnum;
import com.springboot.mapper.TaskProcessMapper;
import com.springboot.service.TaskProcessService;
import com.springboot.utils.ConvertUtils;
import com.springboot.utils.UserAuthInfoContext;
import com.springboot.vo.TaskProcessVo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author lhf
 * @date 2021/4/14 2:50 下午
 **/
@Service
public class TaskProcessServiceImpl extends ServiceImpl<TaskProcessMapper, TaskProcess> implements TaskProcessService {

    @Override
    public TaskProcess addProcess(Long taskId, ProcessTypeEnum processTypeEnum, String opMessage, Long opUserId) {
        final TaskProcess taskProcess = new TaskProcess()
                .setTaskId(taskId)
                .setProcessType(processTypeEnum)
                .setProcessUserId(opUserId)
                .setProcessMessage(opMessage);

        saveTaskProcess(taskProcess);
        return taskProcess;
    }

    @Override
    public List<TaskProcessVo> listProcess(Long taskId) {
        if (Objects.isNull(taskId)){
            return Lists.newArrayListWithCapacity(0);
        }

        final LambdaQueryWrapper<TaskProcess> queryWrapper = new LambdaQueryWrapper<TaskProcess>()
                .eq(TaskProcess::getTaskId, taskId);
        final List<TaskProcess> taskProcesses = list(queryWrapper);
        return ConvertUtils.sourceToTarget(taskProcesses,TaskProcessVo.class);
    }

    private void saveTaskProcess(TaskProcess taskProcess) {
        taskProcess.setCreateTime(new Date());
        taskProcess.setCreateBy(UserAuthInfoContext.getUserName());
        save(taskProcess);
    }
}
