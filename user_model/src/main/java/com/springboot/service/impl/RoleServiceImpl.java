package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.Role;
import com.springboot.enums.EnableEnum;
import com.springboot.mapper.RoleDao;
import com.springboot.mapper.RoleMapper;
import com.springboot.model.RolePerm;
import com.springboot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-11-24
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Collection<RolePerm> findAllRolePermission() {
        return roleMapper.findAllRolePermission();
    }

    @Override
    public Role getRoleByName(String name) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<Role>()
                .eq(Role::getRoleName, name)
                .eq(Role::getEnable, EnableEnum.Y.getCode());
        return getOne(queryWrapper,false);
    }
}
