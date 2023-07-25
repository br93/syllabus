package com.syllabus.cache;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.syllabus.exception.CacheException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
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

    public Boolean isValidCache(String key) {
        return redisTemplate.hasKey(key);
    }

    public void flushCache() {

        var connectionFactory = redisTemplate.getConnectionFactory();

        if (connectionFactory == null) {
            throw new CacheException("connection failed with cache");
        }

        connectionFactory.getConnection().serverCommands().flushDb();

    }
}
