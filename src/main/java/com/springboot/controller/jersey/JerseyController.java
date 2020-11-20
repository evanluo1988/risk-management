package com.springboot.controller.jersey;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by zx on 2020/10/4.
 * 顶级path在JerseyConfig中的@ApplicationPath定义
 * http://localhost:8888/restful/jersey/hello
 */
@Component
@Path("/hello")
public class JerseyController {
    @GET
    public String message(){
        return "hello jersey!";
    }

}
