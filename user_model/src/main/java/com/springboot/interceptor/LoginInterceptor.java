package com.springboot.interceptor;

import com.google.common.collect.Lists;
import com.springboot.constant.GlobalConstants;
import com.springboot.domain.User;
import com.springboot.exception.InvalidAuthException;
import com.springboot.exception.ServiceException;
import com.springboot.model.RolePerm;
import com.springboot.service.UserRoleService;
import com.springboot.service.UserService;
import com.springboot.utils.ServerCacheUtils;
import com.springboot.utils.UserAuthInfoContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Objects;

/**
 * Created by zx on 2020/11/19.
 */
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private UserService userService;
    private UserRoleService userRoleService;

    public LoginInterceptor(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String s = request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString();
        log.info("best matching pattern attribute is :{}",s);
        Long userId;
        if (Objects.isNull(userId = (Long) request.getSession().getAttribute(GlobalConstants.USER_ID))) {
            throw new InvalidAuthException("用户登录凭证失效，请登录！");
        }
        //查询用户实体
        User user = userService.getUserEntityById(userId);
        if (Objects.isNull(user)) {
            throw new ServiceException("用户信息不存在");
        }
        //查询用户角色
        Collection<Long> roleIds = userRoleService.listRoleIdsByUserId(user.getId());

        //组装用户角色权限信息
        Collection<RolePerm> rolePerms = Lists.newArrayListWithCapacity(roleIds.size());
        roleIds.forEach(roleId -> rolePerms.add(ServerCacheUtils.getRolePermByRoleId(roleId)));

        //设置用户上下文缓存信息
        UserAuthInfoContext.set(user, rolePerms);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserAuthInfoContext.clear();
    }
}
