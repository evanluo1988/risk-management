package com.springboot.authority;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.collect.Lists;
import com.springboot.domain.Menu;
import com.springboot.domain.Permission;
import com.springboot.model.RolePerm;
import com.springboot.utils.HttpServletLocalThread;
import com.springboot.utils.ServerCacheUtils;
import com.springboot.utils.ServletUtils;
import com.springboot.utils.UserAuthInfoContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author evan
 */
public class AuthorityServiceImpl extends AbstractAuthority {

    @Override
    public List<Authority> getAccessAuthorities(Subject subject) {
        List<Authority> authorityList = new ArrayList<>();
        List<Menu> menuList = ServerCacheUtils.getMenuListCache();
        if (CollectionUtils.isEmpty(menuList) || ObjectUtils.isEmpty(subject)) {
            return authorityList;
        }

        String requestUri = subject.getRequest().getRequestURI();
        requestUri = requestUri.replace("//", "/");
        if(requestUri.startsWith(ServerCacheUtils.getContextPath())){
            requestUri = requestUri.substring(ServerCacheUtils.getContextPath().length());
        }

        for (Menu menu : menuList) {
            if (requestUri.startsWith(menu.getMenuUrl()) && StringUtils.isNotBlank(menu.getMenuUrl())) {
                List<String> permissionList = menu.getPermissionList().stream().map(Permission::getPermName).collect(Collectors.toList());
                for(String permission : permissionList){
                    authorityList.add(new Authority() {
                        @Override
                        public Object getPremission() {
                            return permission;
                        }
                    });
                }
            }
        }
        return authorityList;
    }

    @Override
    public List<Authority> getAuthorities(Subject subject) {
        if (CollectionUtils.isEmpty(subject.getAuthorities())) {
            List<Authority> authorities = Lists.newArrayList();
            Set<String> permissionNames = UserAuthInfoContext.getRolePerms().stream().flatMap(rolePerm -> rolePerm.getPermissionList().stream()).map(Permission::getPermName).collect(Collectors.toSet());
            permissionNames.forEach(permissionName -> authorities.add(() -> permissionName));
            subject.setAuthorities(authorities);
        }
        return subject.getAuthorities();
    }
}
