package com.syllabus.repository;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CacheRepository {

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

    public boolean hasKey(String key){
        Boolean hasKey = redisTemplate.hasKey(key);
        return hasKey != null && hasKey.equals(true);
    }
    
}
