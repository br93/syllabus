package com.syllabus.service;

import org.springframework.stereotype.Service;

import com.syllabus.cache.CacheRepository;
import com.syllabus.data.StudentResponse;
import com.syllabus.util.Unmarshal;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CacheService {

    private final CacheRepository cacheRepository;
    private static final String STUDENT = "student";

    public void cacheStudent(String id, StudentResponse response){
        var cacheId = cacheRepository.generateCacheId(STUDENT, id);
        cacheRepository.cacheData(cacheId, response);
    }

    public StudentResponse getStudent(String id){
        var cacheId = cacheRepository.generateCacheId(STUDENT, id);
        var cachedStudent = cacheRepository.getCachedData(cacheId);

        return Unmarshal.toStudentResponse(cachedStudent);
    }

    public Boolean isCached(String key){
        return cacheRepository.isValidCache(key);
    }

    
}
