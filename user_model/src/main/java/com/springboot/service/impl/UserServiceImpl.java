package com.springboot.service.impl;

import com.springboot.domain.Permission;
import com.springboot.domain.Role;
import com.springboot.mapper.UserMapper;
import com.springboot.domain.User;
import com.springboot.model.UserRole;
import com.springboot.ret.ReturnT;
import com.springboot.service.UserService;
import com.springboot.utils.ReturnTUtils;
import com.springboot.vo.PermVo;
import com.springboot.vo.RoleVo;
import com.springboot.vo.UserVo;
import com.springboot.vo.UserWithRoleVo;
import org.glassfish.jersey.internal.guava.Sets;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author evan
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public ReturnT<List<UserVo>> findAllUsers() {
        List<User> users = userMapper.findAllUsers();
        if(ObjectUtils.isEmpty(users)){
            return ReturnTUtils.getReturnT(new ArrayList<>());
        }
        List<UserVo> userVos = new ArrayList<>();
        for(User user : users){
            userVos.add(convertUserToUserVo(user));
        }
        return ReturnTUtils.getReturnT(userVos);
    }

    private UserVo convertUserToUserVo(User user) {
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }

    @Override
    public ReturnT<UserWithRoleVo> findWithRoleById(Long id) {
        UserRole user = userMapper.findUserWithRoleById(id);
        if(ObjectUtils.isEmpty(user)){
            return ReturnTUtils.getReturnT(new UserWithRoleVo());
        }
        return ReturnTUtils.getReturnT(convertUserToUserWithRoleVo(user));
    }

    private UserWithRoleVo convertUserToUserWithRoleVo(UserRole user) {
        UserWithRoleVo vo = new UserWithRoleVo();
        BeanUtils.copyProperties(user, vo);
        Set<RoleVo> roleVoSet = Sets.newHashSet();
        Set<PermVo> permVoSet = Sets.newHashSet();
        for(Role role : user.getRoleList()) {
            RoleVo roleVo = new RoleVo();
            BeanUtils.copyProperties(role, roleVo);
            roleVoSet.add(roleVo);

            for(Permission permission : role.getPermissionList()) {
                PermVo permVo = new PermVo();
                BeanUtils.copyProperties(permission, permVo);
                permVoSet.add(permVo);
            }
        }
        vo.setRoleVoSet(roleVoSet);
        vo.setPermVoSet(permVoSet);
        return vo;
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
