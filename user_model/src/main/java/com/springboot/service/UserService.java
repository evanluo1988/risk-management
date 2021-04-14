package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.User;
import com.springboot.page.PageIn;
import com.springboot.page.Pagination;
import com.springboot.ret.ReturnT;
import com.springboot.vo.*;

import java.util.List;
import java.util.Map;

/**
 * @author evan
 */
public interface UserService extends IService<User> {
    /**
     * 获取用户列表
     * @return
     */
    public Pagination<UserPageVo> findUsers(UserVo userVo);

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
    public UserVo getById(Long id);

    /**
     * 新建用户
     *
     * @param userVo
     */
    public void create(RegUserVo userVo);

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
    public void update(RegUserVo user);

    /**
     * 用户登录
     *
     * @param user    用户名密码信息
     */
    void login(RegUserVo user);
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

    /**
     * 修改用户密码
     * @param regUserVo
     */
    void updateUserPassword(RegUserVo regUserVo);

    UserInfoVo info();

    /**
     * 根据区域id分组查询所有用户信息
     * @param areaIds
     * @return
     */
    Map<Long, List<User>> groupUserByAreaIds(List<Long> areaIds);
}
