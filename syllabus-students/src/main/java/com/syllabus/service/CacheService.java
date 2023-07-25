package com.syllabus.service;

import org.springframework.stereotype.Service;

import com.syllabus.cache.CacheRepository;
import com.syllabus.client.settings.response.ProgramResponse;
import com.syllabus.client.settings.response.UniversityCoursesResponse;
import com.syllabus.client.settings.response.UniversityResponse;
import com.syllabus.data.StudentResponse;
import com.syllabus.util.Unmarshal;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CacheService {

    private final CacheRepository cacheRepository;
    private static final String STUDENT = "student";
    
    private static final String UNIVERSITY = "university";
    private static final String COURSES = "courses";
    private static final String PROGRAM = "program";

    public String generateCacheId(String key, String id){
        return cacheRepository.generateCacheId(key, id);
    }

    public void cacheStudent(String id, StudentResponse response){
        var cacheId = cacheRepository.generateCacheId(STUDENT, id);
        cacheRepository.cacheData(cacheId, response);
    }

    public StudentResponse getStudent(String id){
        var cacheId = cacheRepository.generateCacheId(STUDENT, id);
        var cachedStudent = cacheRepository.getCachedData(cacheId);

        return Unmarshal.toStudentResponse(cachedStudent);
    }

    public UniversityCoursesResponse getUniversityCourses(String universityCode){
        var cacheId = cacheRepository.generateCacheId(COURSES, universityCode);
        var cachedUniversityCourse = cacheRepository.getCachedData(cacheId);

        return Unmarshal.toUniversityCoursesResponse(cachedUniversityCourse);
    }

    public UniversityResponse getUniversity(String universityCode){
        var cacheId = cacheRepository.generateCacheId(UNIVERSITY, universityCode);
        var cachedUniversity = cacheRepository.getCachedData(cacheId);

        return Unmarshal.toUniversityResponse(cachedUniversity);
    }

    public ProgramResponse getProgram(String programCode){
        var cacheId = cacheRepository.generateCacheId(PROGRAM, programCode);
        var cachedProgram = cacheRepository.getCachedData(cacheId);

        return Unmarshal.toProgramResponse(cachedProgram);
    }

    public Boolean isCached(String key){
        return cacheRepository.isValidCache(key);
    }

    public void flush(){
        cacheRepository.flushCache();
    }

}
