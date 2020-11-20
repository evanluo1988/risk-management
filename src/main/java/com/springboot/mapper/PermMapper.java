package com.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.domain.Permission;
import com.springboot.domain.Role;

import java.util.List;

public interface PermMapper extends BaseMapper<Permission> {
    List<Permission> findByRoleId(Long roleId);
}
