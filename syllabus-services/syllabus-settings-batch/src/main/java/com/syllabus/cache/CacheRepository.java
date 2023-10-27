package com.syllabus.cache;

import org.hibernate.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CacheRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public void flushCache() {

        var connectionFactory = redisTemplate.getConnectionFactory();

        if (connectionFactory == null) {
            throw new CacheException("connection failed with cache");
        }

        connectionFactory.getConnection().serverCommands().flushDb();

    }
    
}
