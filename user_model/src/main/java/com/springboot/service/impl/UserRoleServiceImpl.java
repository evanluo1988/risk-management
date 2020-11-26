package com.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.domain.Role;
import com.springboot.domain.UserRole;
import com.springboot.mapper.UserRoleDao;
import com.springboot.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 刘宏飞
 * @since 2020-11-25
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements UserRoleService {

    @Override
    public Collection<Long> listRoleIdsByUserId(Long userId) {
        if (Objects.isNull(userId)){
            return Collections.emptyList();
        }

        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userId);

        return list(queryWrapper).stream().map(UserRole::getRoleId).collect(Collectors.toList());
    }
}
