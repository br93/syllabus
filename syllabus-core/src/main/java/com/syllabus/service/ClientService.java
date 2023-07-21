package com.syllabus.service;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.syllabus.client.settings.CourseProgramResponse;
import com.syllabus.client.settings.SettingsClient;
import com.syllabus.client.students.StudentResponse;
import com.syllabus.client.students.StudentsClient;
import com.syllabus.util.CacheUtil;
import com.syllabus.util.Validation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final StudentsClient studentsClient;
    private final SettingsClient settingsClient;

    private final CacheUtil cacheUtil;

    private static final String COURSE_PROGRAMS = "course-programs";
   
    public StudentResponse getStudentByUserId(String userId) {
        return studentsClient.getStudentByUserId(userId);
    }


    public Object getCourseProgramsByCourseCodeIn(Set<String> courseCodes) {
        var cacheId = cacheUtil.generateCacheId(COURSE_PROGRAMS, String.join("-", courseCodes));
        var cache = cacheUtil.getCachedData(cacheId);

        if(Validation.isValidCache(cache))            
          return cache;
        
        return settingsClient.getCourseProgramsByCourseCodeIn(courseCodes);
    }

    public Object getCourseProgramsByProgramAndCourseType(String program, String courseType) {
        var cacheId = cacheUtil.generateCacheId(COURSE_PROGRAMS, program+courseType);
        var cache = cacheUtil.getCachedData(cacheId);
       
       if(Validation.isValidCache(cache))            
            return cache;

        return settingsClient.getCourseProgramsByProgramAndCourseType(program,
                courseType);
    }

    public Object getCourseProgramsByProgramAndNotCourseType(String program, String courseType) {
        var cacheId = cacheUtil.generateCacheId(COURSE_PROGRAMS, program+"!"+courseType);
        var cache = cacheUtil.getCachedData(cacheId);

        if(Validation.isValidCache(cache))            
            return cache;

        return settingsClient.getCourseProgramsByProgramAndNotCourseType(program,
                courseType);
    }
    
}
