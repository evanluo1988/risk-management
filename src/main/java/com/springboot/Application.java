package com.springboot;

import com.springboot.config.JerseyConfig;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.valves.AccessLogValve;
import org.apache.coyote.http11.Http11NioProtocol;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.Serializable;
import java.time.Duration;
import java.util.Set;

/**
 * @author evan
 */
@SpringBootApplication
@ServletComponentScan(basePackages = "com.springboot.config")
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

    /**
     * 注册jersey Servlet
     * @return
     */
    @Bean
    public ServletRegistrationBean jersetServlet(){
        ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/restful/jersey/*");
        registration.addInitParameter(
                ServletProperties.JAXRS_APPLICATION_CLASS,
                JerseyConfig.class.getName());
        return registration;
    }

}
