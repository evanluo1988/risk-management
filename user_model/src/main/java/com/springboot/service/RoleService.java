package com.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.springboot.domain.Role;
import com.springboot.model.RolePerm;

import java.util.Collection;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-11-24
 */
public interface RoleService extends IService<Role> {

    Collection<RolePerm> findAllRolePermission();

    Role getRoleByName(String name);
}
