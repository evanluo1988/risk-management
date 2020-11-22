package com.springboot.service;

import com.springboot.domain.User;
import com.springboot.ret.ReturnT;
import com.springboot.vo.UserVo;
import com.springboot.vo.UserWithRoleVo;

/**
 * Created by zx on 2020/3/7.
 */
public interface UserService {

    public ReturnT<UserWithRoleVo> findUserWithRoleById(Long id);

    public void create(User user);

    public void deleteById(Long id);

    public ReturnT<UserVo> getById(Long id);

    public void update(User user);

    public void updateEmailByName(String name);
}
