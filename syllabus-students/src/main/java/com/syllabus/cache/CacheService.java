package com.syllabus.cache;

import org.springframework.stereotype.Service;

import com.syllabus.client.settings.response.ProgramResponse;
import com.syllabus.client.settings.response.UniversityCoursesResponse;
import com.syllabus.client.settings.response.UniversityResponse;
import com.syllabus.data.StudentResponse;
import com.syllabus.unmarshal.impl.ProgramUnmarshal;
import com.syllabus.unmarshal.impl.StudentUnmarshal;
import com.syllabus.unmarshal.impl.UniversityCoursesUnmarshal;
import com.syllabus.unmarshal.impl.UniversityUnmarshal;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CacheService {

    private final CacheRepository cacheRepository;

    private final ProgramUnmarshal programUnmarshal;
    private final StudentUnmarshal studentUnmarshal;
    private final UniversityCoursesUnmarshal universityCoursesUnmarshal;
    private final UniversityUnmarshal universityUnmarshal;
    

    public String generateCacheId(String key, String id){
        return cacheRepository.generateCacheId(key, id);
    }

    public void cacheStudent(String id, StudentResponse response){
        var cacheId = cacheRepository.generateCacheId(CacheConstants.STUDENT, id);
        cacheRepository.cacheData(cacheId, response);
    }

    public StudentResponse getStudent(String id){
        var cacheId = cacheRepository.generateCacheId(CacheConstants.STUDENT, id);
        var cachedStudent = cacheRepository.getCachedData(cacheId);

        return studentUnmarshal.toResponse(cachedStudent);
    }

    public UniversityCoursesResponse getUniversityCourses(String universityCode){
        var cacheId = cacheRepository.generateCacheId(CacheConstants.COURSES, universityCode);
        var cachedUniversityCourse = cacheRepository.getCachedData(cacheId);

        return universityCoursesUnmarshal.toResponse(cachedUniversityCourse);
    }

    public UniversityResponse getUniversity(String universityCode){
        var cacheId = cacheRepository.generateCacheId(CacheConstants.UNIVERSITY, universityCode);
        var cachedUniversity = cacheRepository.getCachedData(cacheId);

        return universityUnmarshal.toResponse(cachedUniversity);
    }

    public ProgramResponse getProgram(String programCode){
        var cacheId = cacheRepository.generateCacheId(CacheConstants.PROGRAM, programCode);
        var cachedProgram = cacheRepository.getCachedData(cacheId);

        return programUnmarshal.toResponse(cachedProgram);
    }

    public boolean isCached(String key){
        var isValidCache = cacheRepository.isValidCache(key);

        return isValidCache != null && isValidCache.equals(true);
    }

    public void flush(){
        cacheRepository.flushCache();
    }

}
