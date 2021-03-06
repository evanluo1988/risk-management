package com.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * @author evan
 */
@EnableScheduling
@EnableAsync
@SpringBootApplication
@ServletComponentScan(basePackages = "com.springboot.config")
@EnableFeignClients(basePackages = "com.springboot.service.remote")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 编码设置
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean(CharacterEncodingFilter characterEncodingFilter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        characterEncodingFilter.setEncoding("UTF-8");
        registrationBean.setFilter(characterEncodingFilter);
        return registrationBean;
    }

//    /**
//     * 注册jersey Servlet
//     * @return
//     */
//    @Bean
//    public ServletRegistrationBean jersetServlet(){
//        ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/restful/jersey/*");
//        registration.addInitParameter(
//                ServletProperties.JAXRS_APPLICATION_CLASS,
//                JerseyConfig.class.getName());
//        return registration;
//    }

}
