package com.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.domain.Permission;
import com.springboot.model.PremWithMenuId;

import java.util.List;

public interface PermMapper extends BaseMapper<Permission> {
    List<Permission> findByRoleId(Long roleId);

    List<Permission> findByMenuId(Long menuId);

    List<PremWithMenuId> findWithMenuIdByMenuIds(List<Long> menuIds);
}
