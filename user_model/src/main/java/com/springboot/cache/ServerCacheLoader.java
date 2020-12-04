package com.springboot.cache;

import com.springboot.service.AreaService;
import com.springboot.service.MenuService;
import com.springboot.service.RoleService;
import com.springboot.utils.ServerCacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ServerCacheLoader implements CommandLineRunner {

    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AreaService areaService;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public void run(String... args) {
        log.info("cache context path ......");
        ServerCacheUtils.setContextPathCache(contextPath);
        log.info("loading menu list......");
        ServerCacheUtils.setMenuListCache(menuService.findAllMenus());
        log.info("loading role permission......");
        ServerCacheUtils.setRolePermissionCache(roleService.findAllRolePermission());
        log.info("loading area ......");
        ServerCacheUtils.setAreas(areaService.list());
    }
}
