package com.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.domain.Task;
import com.springboot.model.TaskModel;

public interface TaskMapper extends BaseMapper<Task> {
    IPage<TaskModel> findPageTasks(Page page);
}
