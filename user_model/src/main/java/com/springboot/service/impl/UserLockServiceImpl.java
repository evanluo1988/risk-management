package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.UserLock;
import com.springboot.mapper.UserLockMapper;
import com.springboot.service.UserLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLockServiceImpl extends ServiceImpl<UserLockMapper, UserLock> implements UserLockService {
    @Autowired
    private UserLockMapper userLockMapper;
    @Override
    public UserLock getByLoginName(String loginName) {
        QueryWrapper<UserLock> lockQueryWrapper = new QueryWrapper<>();
        lockQueryWrapper.eq("login_name", loginName);
        return userLockMapper.selectOne(lockQueryWrapper);
    }

    @Override
    public void create(UserLock userLock) {
        userLockMapper.insert(userLock);
    }

    @Override
    public void update(UserLock userLock) {
        userLockMapper.updateById(userLock);
    }
}
