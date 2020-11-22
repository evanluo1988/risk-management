package com.springboot.utils;

import com.springboot.domain.Menu;

import java.util.List;

public class ServerCacheUtils {
    private static List<Menu> menuListCache;

    public static List<Menu> getMenuListCache() {
        return ServerCacheUtils.menuListCache;
    }

    public static void setMenuListCache(List<Menu> menuListCache) {
        ServerCacheUtils.menuListCache = menuListCache;
    }
}
