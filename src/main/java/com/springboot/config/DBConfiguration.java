package com.springboot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by zx on 2020/7/23.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManageFactory",
        transactionManagerRef = "transactionManager",
        basePackages = {"com.springboot.dao"}
)
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
        bean.setTypeHandlersPackage("com.springboot.mybatis.typehandle");
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        bean.setGlobalConfig(new GlobalConfig().setMetaObjectHandler(new MyMetaObjectHandler()));
        return bean.getObject();
    }

    @Bean(name = "mybatisSqlSessionTemplate")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("mybatisSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


    /**
     * 一般的hibernate配置
     */
//    @Bean(value = "sessionFactory")
//    public LocalSessionFactoryBean sessionFactory(@Qualifier("baseDataSource") DataSource dataSource) {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource);
//        sessionFactory.setPackagesToScan("com.springboot.domain");
//        Properties hibernateProperties = new Properties();
//        hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
//        hibernateProperties.put("hibernate.show_sql", true);
//        hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
//        sessionFactory.setHibernateProperties(hibernateProperties);
//
//        return sessionFactory;
//    }


//    @Bean("jdbcTemplate")
//    public JdbcTemplate jdbcTemplate(@Qualifier("baseDataSource") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }

    /**
     * Spring data JPA 需要这个类
     * @param dataSource
     * @return
     */
    @Bean(name = "entityManageFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("baseDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        factory.setPackagesToScan("com.springboot.domain");
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        jpaProperties.put("hibernate.connection.charSet", "utf-8");
        jpaProperties.put("hibernate.show_sql", "true");
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        factory.setJpaProperties(jpaProperties);
        factory.afterPropertiesSet();
        return factory;
    }


//    @Bean(name = "transactionManager")
//    public HibernateTransactionManager txManager(SessionFactory sessionFactory) throws Exception {
//        HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
//        hibernateTransactionManager.setSessionFactory(sessionFactory);
//        return hibernateTransactionManager;
//    }

//    @Bean(name = "transactionManager")
//    public PlatformTransactionManager txManager(@Qualifier("baseDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManageFactory) {
        return new JpaTransactionManager(entityManageFactory);
    }
}
