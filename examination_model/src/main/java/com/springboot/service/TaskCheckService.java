package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.TaskCheck;

public interface TaskCheckService extends IService<TaskCheck> {
    public void create(TaskCheck taskCheck);

    TaskCheck getTaskCheckById(Long id);

    void goBack(Long taskCheckId);

    void revoke(Long id);
}
