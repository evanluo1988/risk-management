package com.springboot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;

/**
 * Created by zx on 2020/7/23.
 */
@Configuration
@EnableTransactionManagement
public class DBConfiguration {
    @Bean(name = "baseDataSource")
    public DataSource setDataSource(DBConfig dbc) {
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl(dbc.getUrl());
        ds.setUsername(dbc.getUsername());
        ds.setPassword(dbc.getPassword());
        ds.setDriverClassName(dbc.getDriverClassName());
        return ds;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.springboot.mapper");
        mapperScannerConfigurer.setSqlSessionTemplateBeanName("mybatisSqlSessionTemplate");
        return mapperScannerConfigurer;
    }

    @Bean(name = "mybatisSqlSessionFactory")
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("baseDataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //bean.setTypeHandlersPackage("com.springboot.mybatis.typehandle");
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        bean.setGlobalConfig(new GlobalConfig().setMetaObjectHandler(new MyMetaObjectHandler()));
        return bean.getObject();
    }

    @Bean(name = "mybatisSqlSessionTemplate")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("mybatisSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
