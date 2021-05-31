package com.example;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import javax.annotation.Resource;

@SpringBootTest
public class RedisJobTest {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    void demo(){
        SetOperations<String, Object> stringObjectSetOperations = redisTemplate.opsForSet();
    }
}
