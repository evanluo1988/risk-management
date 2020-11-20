package com.springboot.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by zx on 2020/10/15.
 *标记位对外暴漏服务，启动服务时注册到zookeeper上，对应类为ServiceRegistryImpl
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyWebService {
}
