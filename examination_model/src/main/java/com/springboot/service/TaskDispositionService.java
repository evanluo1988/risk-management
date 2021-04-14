package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.TaskDisposition;

public interface TaskDispositionService extends IService<TaskDisposition> {
    public void create(TaskDisposition taskDisposition);

    TaskDisposition getDispositionByTaskCheckId(Long taskCheckId);

    TaskDisposition getDispositionByTaskCheckIdAndProcessId(Long taskCheckId, Long processId);
}
