package com.springboot.redis;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by zx on 2020/11/18.
 */
public class RedisCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return Boolean.TRUE.toString().equalsIgnoreCase(context.getEnvironment().getProperty("redis.enable"));
    }
}
