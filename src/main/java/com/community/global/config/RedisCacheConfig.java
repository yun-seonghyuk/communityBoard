package com.community.global.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisCacheConfig {

    @Value("${spring.data.redis.port}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;


}
