package com.springboot.config;

import org.glassfish.jersey.server.ResourceConfig;
import javax.ws.rs.ApplicationPath;

/**
 * Created by zx on 2020/10/4.
 * 定义jersey的顶级uri 即基本路径
 */
@ApplicationPath("/restful/jersey")
public class JerseyConfig extends ResourceConfig{
    public JerseyConfig(){
        packages("com.springboot.controller.jersey");
        //register(JerseyController.class);
    }
}

