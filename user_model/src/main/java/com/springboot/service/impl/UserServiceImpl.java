package com.springboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.springboot.constant.GlobalConstants;
import com.springboot.domain.Permission;
import com.springboot.domain.Role;
import com.springboot.enums.EnableEnum;
import com.springboot.exception.ServiceException;
import com.springboot.mapper.UserMapper;
import com.springboot.domain.User;
import com.springboot.model.RolePerm;
import com.springboot.model.UserRole;
import com.springboot.ret.ReturnT;
import com.springboot.service.UserService;
import com.springboot.utils.HttpServletLocalThread;
import com.springboot.utils.ReturnTUtils;
import com.springboot.vo.PermVo;
import com.springboot.vo.RoleVo;
import com.springboot.vo.UserVo;
import com.springboot.vo.UserWithRoleVo;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.internal.guava.Sets;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author evan
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ReturnT<List<UserVo>> findAllUsers() {
        List<User> users = userMapper.findAllUsers();
        if (ObjectUtils.isEmpty(users)) {
            return ReturnTUtils.getReturnT(new ArrayList<>());
        }
        List<UserVo> userVos = new ArrayList<>();
        for (User user : users) {
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
        if (ObjectUtils.isEmpty(user)) {
            return ReturnTUtils.getReturnT(new UserWithRoleVo());
        }
        return ReturnTUtils.getReturnT(convertUserToUserWithRoleVo(user));
    }

    private UserWithRoleVo convertUserToUserWithRoleVo(UserRole user) {
        UserWithRoleVo vo = new UserWithRoleVo();
        BeanUtils.copyProperties(user, vo);
        Set<RoleVo> roleVoSet = Sets.newHashSet();
        Set<PermVo> permVoSet = Sets.newHashSet();
        for (RolePerm role : user.getRoleList()) {
            RoleVo roleVo = new RoleVo();
            BeanUtils.copyProperties(role, roleVo);
            roleVoSet.add(roleVo);

            for (Permission permission : role.getPermissionList()) {
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
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void update(User user) {
        userMapper.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void login(UserVo userVo) {
        log.info("login param:{}", JSON.toJSONString(userVo));
        User user = getUserByUsername(userVo.getUserName());
        //用户不存在
        if (Objects.isNull(user)) {
            throw new ServiceException("用户名或密码错误");
        }
        //密码错误
        if (!BCrypt.checkpw(userVo.getPassword(), user.getPassword())) {
            throw new ServiceException("用户名或密码错误");
        }
        log.debug("userName password valid success");
        HttpServletRequest request = HttpServletLocalThread.getRequest();
        //将session和业务用户关联
        request.getSession().setAttribute(GlobalConstants.USER_ID, user.getId());
        log.debug("login success userId:{}", user.getId());
    }

    @Override
    public void logout() {
        HttpServletRequest request = HttpServletLocalThread.getRequest();
        HttpSession session = request.getSession();
        Long userId;
        if (Objects.nonNull(userId = (Long) session.getAttribute(GlobalConstants.USER_ID))) {
            log.debug("user logout , userId:{}", userId);
            session.invalidate();
        } else {
            throw new ServiceException("未登录不能登出！");
        }
    }

    @Override
    public User getUserEntityById(Long userId) {
        if (Objects.isNull(userId)) {
            return null;
        }

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getId, userId)
                .eq(User::getEnable, EnableEnum.Y.getCode());
        return userMapper.selectOne(queryWrapper);
    }

    /**
     * 根据用户名查询用户
     *
     * @param userName 用户名
     * @return 用户信息
     */
    private User getUserByUsername(String userName) {
        if (StringUtils.isEmpty(userName)){
            return null;
        }

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                .eq(User::getUserName, userName.trim())
                .eq(User::getEnable, EnableEnum.Y.getCode());
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void deleteById(Long id) {
        userMapper.disableById(id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void create(UserVo userVo) {
        //如果没输入密码 填充默认密码
        if (StringUtils.isEmpty(userVo.getPassword())){
            userVo.setPassword(BCrypt.hashpw(GlobalConstants.DEFAULT_PASSWORD,BCrypt.gensalt()));
        }

        //校验area

        //userMapper.insert(user);
    }
}
