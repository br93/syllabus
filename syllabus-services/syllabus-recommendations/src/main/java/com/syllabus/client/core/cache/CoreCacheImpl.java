package com.syllabus.client.core.cache;

import java.util.List;

import org.springframework.stereotype.Service;

import com.syllabus.cache.CacheService;
import com.syllabus.client.core.CoreResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CoreCacheImpl implements CoreCache{

    private final CacheService cacheService;

    @Override
    public List<CoreResponse> getCachedMissingRequiredCoursesByUserId(String userId) {
        return cacheService.getMissingRequiredCoursesByUserId(userId);
    }

    @Override
    public List<CoreResponse> getCachedMissingElectiveCoursesByUserId(String userId) {
       return cacheService.getMissingElectiveCoursesByUserId(userId);
    }

    @Override
    public List<CoreResponse> getCachedCoursesTakenByUserId(String userId) {
       return cacheService.getCoursesTakenByUserId(userId);
    }
    
}
