package com.springboot.config;

import com.springboot.authority.AuthorityManagement;
import com.springboot.authority.AuthorityServiceImpl;
import com.springboot.interceptor.AuthorityInterceptor;
import com.springboot.interceptor.HttpServletInterceptor;
import com.springboot.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by zx on 2020/11/18.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new HttpServletInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/static/login.html");

//        registry.addInterceptor(new LoginInterceptor())
//                .addPathPatterns("/users/*")
//                .excludePathPatterns("/static/login.html");

        registry.addInterceptor(new AuthorityInterceptor(new AuthorityManagement(new AuthorityServiceImpl())))
                .addPathPatterns("/**")
                .excludePathPatterns("/static/login.html");
    }
}
