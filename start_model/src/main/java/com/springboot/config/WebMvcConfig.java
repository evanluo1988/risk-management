package com.springboot.config;

import com.springboot.authority.AuthorityManagement;
import com.springboot.authority.AuthorityServiceImpl;
import com.springboot.interceptor.AuthorityInterceptor;
import com.springboot.interceptor.HttpServletInterceptor;
import com.springboot.interceptor.LoginInterceptor;
import com.springboot.service.UserRoleService;
import com.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zx on 2020/11/18.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> frontResources = Arrays.asList("/", "/to_login", "", "/charge/user/login", "/get_valid_code", "/**/*.html", "/**/*.js", "/**/*.css", "/**/*.json", "/**/*.icon");
        registry.addInterceptor(new HttpServletInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/test/**")
                .excludePathPatterns("/static/**")
                .excludePathPatterns(frontResources);

        registry.addInterceptor(new LoginInterceptor(userService,userRoleService))
                .excludePathPatterns("/login")
                .excludePathPatterns("/test/**")
                .excludePathPatterns("/static/**")
                .excludePathPatterns(frontResources);

        registry.addInterceptor(new AuthorityInterceptor(new AuthorityManagement(new AuthorityServiceImpl())))
                .addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/logout")
                .excludePathPatterns("/test/**")
                .excludePathPatterns("/static/**")
                .excludePathPatterns(frontResources);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(new String[]{"classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/"});
    }
}
