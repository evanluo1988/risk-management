package com.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.domain.Role;
import com.springboot.domain.User;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {
    List<Role> findByUserId(Long userId);
}
