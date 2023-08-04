package com.syllabus.client.settings.cache;

import java.util.List;

import org.springframework.stereotype.Service;

import com.syllabus.cache.CacheService;
import com.syllabus.client.settings.response.ClassScheduleResponse;
import com.syllabus.client.settings.response.CourseClassesResponse;
import com.syllabus.client.settings.response.PreRequisiteCountResponse;
import com.syllabus.client.settings.response.PreRequisiteCoursesResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SettingsCacheImpl implements SettingsCache{

    private final CacheService cacheService;

    @Override
    public PreRequisiteCountResponse getCachedAsPreRequisiteCountByCourseCode(String courseCode) {
       return cacheService.getAsPreRequisiteCountByCourseCode(courseCode);
    }

    @Override
    public PreRequisiteCoursesResponse getCachedPreRequisitesByCourseCode(String courseCode) {
        return cacheService.getPreRequisitesByCourseCode(courseCode);
    }

    @Override
    public CourseClassesResponse getCachedClassesByCourse(String courseCode) {
        return cacheService.getClassesByCourse(courseCode);
    }

    @Override
    public List<ClassScheduleResponse> getCachedClassSchedulesByClassCode(String courseCode) {
        return cacheService.getClassSchedulesByClassCode(courseCode);
    }
    
}
