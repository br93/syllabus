package com.syllabus.client.students.cache;

import org.springframework.stereotype.Service;

import com.syllabus.cache.CacheService;
import com.syllabus.client.students.StudentResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudentsCacheImpl implements StudentsCache{

    private final CacheService cacheService;
    
    @Override
    public StudentResponse getCachedStudentByUserId(String userId) {
        return cacheService.getStudentByUserId(userId);
    }
    
}
