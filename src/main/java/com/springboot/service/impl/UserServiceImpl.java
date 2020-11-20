package com.springboot.service.impl;

import com.springboot.domain.Permission;
import com.springboot.domain.Role;
import com.springboot.mapper.UserMapper;
import com.springboot.domain.User;
import com.springboot.service.UserService;
import com.springboot.utils.ReturnTUtils;
import com.springboot.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author evan
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public ReturnT<UserWithRoleVo> findUserWithRoleById(Long id) {
        User user = userMapper.findUserWithRoleById(id);
        if(ObjectUtils.isEmpty(user)){
            return ReturnTUtils.getReturnT(new UserWithRoleVo());
        }
        UserWithRoleVo vo = new UserWithRoleVo();
        convertUserToUserWithRoleVo(user, vo);
        return ReturnTUtils.getReturnT(vo);
    }

    private void convertUserToUserWithRoleVo(User user, UserWithRoleVo vo) {
        BeanUtils.copyProperties(user, vo);
        List<RoleVo> roleVoList = new ArrayList<>();
        for(Role role : user.getRoleList()) {
            RoleVo roleVo = new RoleVo();
            BeanUtils.copyProperties(role, roleVo);
            roleVoList.add(roleVo);
            List<PermVo> permVoList = new ArrayList<>();
            for(Permission permission : role.getPermissionList()) {
                PermVo permVo = new PermVo();
                BeanUtils.copyProperties(permission, permVo);
                permVoList.add(permVo);
            }
            roleVo.setPermissionVoList(permVoList);
        }
        vo.setRoleList(roleVoList);
    }

    @Override
    public ReturnT<UserVo> getById(Long id) {
        User user = userMapper.selectById(id);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return ReturnTUtils.getReturnT(userVo);
    }

    @Override
    @Transactional
    public void update(User user) {
        userMapper.updateById(user);
    }

    @Override
    @Transactional
    public void updateEmailByName(String name) {
        userMapper.updateEmailByName(name, "333@qq.com");
    }

    @Override
    public void deleteById(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    @Transactional( "transactionManager")
    public void create(User user) {
         userMapper.insert(user);
    }
}
