package com.syllabus.cache;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CacheService {

    private final CacheRepository cacheRepository;

    public void flush(){
        cacheRepository.flushCache();
    }
    
}
