package com.springboot.utils;

import com.springboot.domain.Menu;
import com.springboot.model.RolePerm;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServerCacheUtils {
    private static List<Menu> menuListCache;
    /**
     * rolePerm缓存，key：roleId，value：RolePerm
     */
    private static Map<Long, RolePerm> rolePermCache;
    /**
     * context path
     */
    private static String contextPath;


    public static List<Menu> getMenuListCache() {
        return ServerCacheUtils.menuListCache;
    }

    public static void setMenuListCache(List<Menu> menuListCache) {
        ServerCacheUtils.menuListCache = menuListCache;
    }

    public static void setRolePermissionCache(Collection<RolePerm> rolePerms) {
        ServerCacheUtils.rolePermCache = rolePerms.stream().collect(Collectors.toMap(RolePerm::getId, rolePerm -> rolePerm));
    }

    /**
     * 根据roleId查询RolePerm
     *
     * @param roleId roleId
     * @return RolePerm
     */
    public static RolePerm getRolePermByRoleId(Long roleId) {
        return rolePermCache.get(roleId);
    }

    public static void setContextPathCache(String contextPath) {
        ServerCacheUtils.contextPath = contextPath;
    }

    /**
     * 从缓存获取contextPath
     *
     * @return contextPath
     */
    public static String getContextPath() {
        return contextPath;
    }
}
