package com.springboot.cache;

import com.springboot.service.MenuService;
import com.springboot.utils.ServerCacheUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServerCacheLoader implements InitializingBean {
    private static Logger log = LoggerFactory.getLogger(ServerCacheLoader.class);

    @Autowired
    private MenuService menuService;
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("loading menu list......");
        ServerCacheUtils.setMenuListCache(menuService.findAllMenus());

    }
}
