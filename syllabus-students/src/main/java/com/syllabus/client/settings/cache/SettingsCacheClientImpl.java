package com.syllabus.client.settings.cache;

import org.springframework.stereotype.Component;

import com.syllabus.client.settings.response.ProgramResponse;
import com.syllabus.client.settings.response.UniversityCoursesResponse;
import com.syllabus.client.settings.response.UniversityResponse;
import com.syllabus.service.CacheService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class SettingsCacheClientImpl implements SettingsCacheClient {

    private final CacheService cacheService;
   
    @Override
    public UniversityCoursesResponse cachedCoursesByUniversity(String key) {
        return cacheService.getUniversityCourses(key);
    }

    @Override
    public UniversityResponse cachedUniversityByIdOrCode(String key) {
        return cacheService.getUniversity(key);
    }

    @Override
    public ProgramResponse cachedProgramByIdOrCode(String key) {
        return cacheService.getProgram(key);
    }

    @Override
    public Boolean isCached(String key) {
        return cacheService.isCached(key);
    }

}
