package com.springboot.redis;

import org.glassfish.jersey.internal.guava.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by zx on 2020/11/18.
 */
@Configuration
@Conditional(RedisCondition.class)
public class RedisConfig {
    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        Set<String> clusterNodes = Sets.newHashSet();
        clusterNodes.add("127.0.0.1:7001");
        clusterNodes.add("127.0.0.1:7002");
        clusterNodes.add("127.0.0.1:7003");
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(clusterNodes);
        RedisConnectionFactory redisConnectionFactory = new LettuceConnectionFactory(redisClusterConfiguration);
        return redisConnectionFactory;
    }

    @Bean("redisTemplate")
    public RedisTemplate<String, Serializable> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(RedisTemplate<String, Serializable> redisTemplate){
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        return redisCacheConfiguration;
    }
}
