package com.springboot.service.impl;

import com.springboot.annotation.MyWebService;
import com.springboot.mapper.UserMapper;
import com.springboot.domain.User;
import com.springboot.service.UserService;
import com.springboot.utils.ReturnTUtils;
import com.springboot.vo.ReturnT;
import com.springboot.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ReturnT<UserVo> getById(Long id) {
        User user = userMapper.selectById(id);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return ReturnTUtils.getReturnT(userVo);
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
