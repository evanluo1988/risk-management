package com.springboot.service;

import com.springboot.domain.User;
import com.springboot.ret.ReturnT;
import com.springboot.vo.UserVo;
import com.springboot.vo.UserWithRoleVo;

import java.util.List;

/**
 * @author evan
 */
public interface UserService {
    public ReturnT<List<UserVo>> findAllUsers();

    public ReturnT<UserWithRoleVo> findWithRoleById(Long id);

    public void create(User user);

    public void deleteById(Long id);

    public ReturnT<UserVo> getById(Long id);

    public void update(User user);

    public void updateEmailByName(String name);
}
