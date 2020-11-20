package com.springboot.service.impl;

import com.springboot.annotation.MyWebService;
import com.springboot.mapper.UserMapper;
import com.springboot.domain.User;
import com.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zx on 2020/3/7.
 */
@Service
@MyWebService
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    @Transactional
    public void update(User user) {
        userMapper.updateById(user);
    }

    @Override
    @Transactional
    public void updateEmailByName(String name) {
        userMapper.updateEmailByName(name, "333@qq.com");
    }

    @Override
    public void deleteById(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    @Transactional( "transactionManager")
    public void create(User user) {
         userMapper.insert(user);
    }
}
