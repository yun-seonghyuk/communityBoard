package com.community.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Temp {

    private final RedisTemplate<String , String> redisTemplate;

    public String test(){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("key", "value");
        return null;
    }
}
