package com.springboot;

import com.springboot.config.JerseyConfig;
import com.springboot.register.ServiceRegisterHelper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.valves.AccessLogValve;
import org.apache.coyote.http11.Http11NioProtocol;
import org.glassfish.jersey.internal.guava.Sets;
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
 * Created by zx on 2020/3/7.
 */
@SpringBootApplication
@ServletComponentScan(basePackages = "com.springboot.config")
public class Application {
    public static void main(String[] args) {
        SpringApplication sa = new SpringApplication();
        sa.run(Application.class, args);
    }

    @Bean
    public ServiceRegisterHelper register(){
        ServiceRegisterHelper register = new ServiceRegisterHelper();
        return register;
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

    /**
     * 修改默认内嵌tomcat配置
     * @return
     */
    @Bean
    public ConfigurableServletWebServerFactory createServletWebServerFactory(){
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.setPort(8888);//tomcat端口
        //factory.setBaseDirectory(new File("d:/temp/tomcat"));//缓存路径
        factory.addContextValves(getLogAccessLogValue());//设置日志
        factory.addConnectorCustomizers(new MyTomcatConnectorCustomizer());//tomcat自定义访问连接
        factory.setSession(getSession());
        return factory;
    }
    private AccessLogValve getLogAccessLogValue(){
        AccessLogValve log=new AccessLogValve();
        log.setDirectory("d:/temp/logs");//日志路径
        log.setEnabled(true);//启用日志
        log.setPattern("common");//输入日志格式
        log.setPrefix("springboot-access-log");//日志名称
        log.setSuffix(".txt");//日志后缀
        return log;
    }

    private Session getSession(){
        Session session = new Session();
        session.setTimeout(Duration.ofSeconds(10));
        session.setPersistent(true);
        return session;
    }

    private class MyTomcatConnectorCustomizer implements TomcatConnectorCustomizer {
        @Override
        public void customize(Connector connector) {
            Http11NioProtocol protocal=(Http11NioProtocol)connector.getProtocolHandler();
            protocal.setMaxConnections(20000);//最大连接数
            protocal.setMaxThreads(500);//最大线程数
        }
    }

}
