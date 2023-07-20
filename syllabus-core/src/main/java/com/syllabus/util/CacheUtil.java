package com.syllabus.util;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.syllabus.config.RedisEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CacheUtil {

    private final RedisTemplate<String, RedisEntity> redisTemplate;

    public String generateCacheId(String key, String id) {
        return key + id;
    }

    public void cacheData(String key, RedisEntity value) {
        redisTemplate.opsForValue().setIfAbsent(key, value, Long.valueOf("4"), TimeUnit.MINUTES);
    }

    public RedisEntity getCachedData(String key) {
        return redisTemplate.opsForValue().get(key);

    }

}
