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
    /**
     * 获取所有的用户列表
     * @return
     */
    public ReturnT<List<UserVo>> findAllUsers();

    /**
     * 通过用户ID获取带权限的用户信息
     * @param id
     * @return
     */
    public ReturnT<UserWithRoleVo> findWithRoleById(Long id);

    /**
     * 通过用户ID获取用户信息
     * @param id
     * @return
     */
    public ReturnT<UserVo> getById(Long id);

    /**
     * 新建用户
     * @param user
     */
    public void create(User user);

    /**
     * 注销用户
     * @param id
     */
    public void deleteById(Long id);

    /**
     * 修改用户
     * @param user
     */
    public void update(User user);
}
