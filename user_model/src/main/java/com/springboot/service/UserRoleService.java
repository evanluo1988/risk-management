package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.Role;
import com.springboot.domain.UserRole;

import java.util.Collection;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-11-25
 */
public interface UserRoleService extends IService<UserRole> {
    /**
     * 根据用户ID查询用户角色ID集合
     *
     * @param userId 用户ID
     * @return 用户角色ID集合
     */
    Collection<Long> listRoleIdsByUserId(Long userId);
}
