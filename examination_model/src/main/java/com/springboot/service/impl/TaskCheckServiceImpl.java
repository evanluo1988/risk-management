package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.Area;
import com.springboot.domain.TaskCheck;
import com.springboot.enums.AssignmentEnum;
import com.springboot.enums.EnableEnum;
import com.springboot.exception.ServiceException;
import com.springboot.mapper.TaskCheckMapper;
import com.springboot.service.TaskCheckService;
import com.springboot.utils.ServerCacheUtils;
import com.springboot.utils.UserAuthInfoContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@Service
public class TaskCheckServiceImpl extends ServiceImpl<TaskCheckMapper,TaskCheck> implements TaskCheckService {
    @Autowired
    private TaskCheckMapper taskCheckMapper;

    @Override
    @Transactional
    public void create(TaskCheck taskCheck) {
        taskCheckMapper.insert(taskCheck);
    }

    @Override
    public TaskCheck getTaskCheckById(Long id) {
        if (Objects.isNull(id)){
            return null;
        }
        LambdaQueryWrapper<TaskCheck> queryWrapper = new LambdaQueryWrapper<TaskCheck>()
                .eq(TaskCheck::getId, id)
                .eq(TaskCheck::getEnable, EnableEnum.Y.getCode());
        return getOne(queryWrapper,false);
    }

    @Override
    public void goBack(Long taskCheckId) {
        if (Objects.isNull(taskCheckId)){
            return ;
        }

        TaskCheck taskCheckById = getTaskCheckById(taskCheckId);
        Area parentArea = ServerCacheUtils.getAreaById(ServerCacheUtils.getAreaById(taskCheckById.getAreaId()).getParentId());
        if (Objects.isNull(parentArea)){
            throw new ServiceException("找不到父级区域，无法撤回");
        }

        LambdaUpdateWrapper<TaskCheck> updateWrapper = new LambdaUpdateWrapper<TaskCheck>()
                .set(TaskCheck::getAssignment, AssignmentEnum.RETURNED.getCode())
                .set(TaskCheck::getAreaId, parentArea.getId())
                .set(TaskCheck::getCheckRegion,parentArea.getAreaName())
                .set(TaskCheck::getUpdateBy, UserAuthInfoContext.getUserName())
                .set(TaskCheck::getUpdateTime, new Date())
                .eq(TaskCheck::getId,taskCheckId);
        this.update(updateWrapper);
    }

    @Override
    public void revoke(Long id) {
        if (Objects.isNull(id)){
            return ;
        }

        TaskCheck taskCheckById = getTaskCheckById(id);
        Area parentArea = ServerCacheUtils.getAreaById(ServerCacheUtils.getAreaById(taskCheckById.getAreaId()).getParentId());
        if (Objects.isNull(parentArea)){
            throw new ServiceException("找不到父级区域，无法撤回");
        }
        LambdaUpdateWrapper<TaskCheck> updateWrapper = new LambdaUpdateWrapper<TaskCheck>()
                .set(TaskCheck::getAssignment, AssignmentEnum.REVOKE.getCode())
                .set(TaskCheck::getAreaId, parentArea.getId())
                .set(TaskCheck::getCheckRegion,parentArea.getAreaName())
                .set(TaskCheck::getUpdateBy, UserAuthInfoContext.getUserName())
                .set(TaskCheck::getUpdateTime, new Date())
                .eq(TaskCheck::getId,id);
        this.update(updateWrapper);
    }
}
