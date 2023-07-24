package com.syllabus.util;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CacheUtil {

    private final RedisTemplate<String, Object> redisTemplate;
    
    public String generateCacheId(String key, String id) {
        return key + id;
    }

    public void cacheData(String key, Object value) {
        redisTemplate.opsForValue().setIfAbsent(key, value, Long.valueOf("10"), TimeUnit.MINUTES);
    }

    public Object getCachedData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Boolean isValidCache(String key){
        return redisTemplate.hasKey(key);
    }

}
