package com.springboot.authority;

import com.springboot.domain.Menu;
import com.springboot.domain.Permission;
import com.springboot.utils.HttpServletLocalThread;
import com.springboot.utils.ServerCacheUtils;
import com.springboot.utils.ServletUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author evan
 */
public class AuthorityServiceImpl extends AbstractAuthority {

    @Override
    public List<Authority> getAccessAuthorities(Subject subject) {
        List<Authority> authorityList = new ArrayList<>();
        List<Menu> menuList = ServerCacheUtils.getMenuListCache();
        if(CollectionUtils.isEmpty(menuList) || ObjectUtils.isEmpty(subject)){
            return authorityList;
        }

        String requestUri = subject.getRequest().getRequestURI();
        requestUri = requestUri.replace("//","/");

        for(Menu menu : menuList){
            if(requestUri.startsWith(menu.getMenuUrl())) {
                authorityList.add(() -> {
                    return menu.getPermissionList().stream().map(Permission::getPermName).collect(Collectors.toList());
                });
            }
        }

        return authorityList;
    }

    @Override
    public List<Authority> getAuthorities(Subject subject) {
        if(CollectionUtils.isEmpty(subject.getAuthorities())) {
            //TODO 得到当前用户的权限列表
        }
        return subject.getAuthorities();
    }
}
