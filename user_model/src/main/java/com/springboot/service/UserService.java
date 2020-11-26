package com.springboot.service;

import com.springboot.domain.User;
import com.springboot.ret.ReturnT;
import com.springboot.vo.UserVo;
import com.springboot.vo.UserWithRoleVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author evan
 */
public interface UserService {
    /**
     * 获取所有的用户列表
     *
     * @return
     */
    public ReturnT<List<UserVo>> findAllUsers();

    /**
     * 通过用户ID获取带权限的用户信息
     *
     * @param id
     * @return
     */
    public ReturnT<UserWithRoleVo> findWithRoleById(Long id);

    /**
     * 通过用户ID获取用户信息
     *
     * @param id
     * @return
     */
    public ReturnT<UserVo> getById(Long id);

    /**
     * 新建用户
     *
     * @param userVo
     */
    public void create(UserVo userVo);

    /**
     * 注销用户
     *
     * @param id
     */
    public void deleteById(Long id);

    /**
     * 修改用户
     *
     * @param user
     */
    public void update(User user);

    /**
     * 用户登录
     *
     * @param user    用户名密码信息
     */
    void login(UserVo user);
    /**
     * 用户登出
     */
    void logout();

    /**
     * 根据用户ID查询用户实体
     * @param userId
     * @return
     */
    User getUserEntityById(Long userId);
}
