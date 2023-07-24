package com.syllabus.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.syllabus.client.settings.SettingsClient;
import com.syllabus.client.students.StudentsClient;
import com.syllabus.util.CacheUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final StudentsClient studentsClient;
    private final SettingsClient settingsClient;

    private final CacheUtil cacheUtil;

    private static final String COURSE_PROGRAMS = "course-programs";
    private static final String STUDENT = "student";

    public Object getStudentByUserId(String userId) {
        var cacheId = cacheUtil.generateCacheId(STUDENT, userId);

        if (cacheUtil.isValidCache(cacheId).equals(true))
            return cacheUtil.getCachedData(cacheId);

        return studentsClient.getStudentByUserId(userId);
    }

    public Object getCourseProgramsByCourseCodeIn(Set<String> courseCodes) {
        var cacheId = cacheUtil.generateCacheId(COURSE_PROGRAMS, String.join("-", courseCodes));

        if (cacheUtil.isValidCache(cacheId).equals(true))
            return cacheUtil.getCachedData(cacheId);

        return settingsClient.getCourseProgramsByCourseCodeIn(courseCodes);
    }

    public Object getCourseProgramsByProgramAndCourseType(String program, String courseType) {
        var cacheId = cacheUtil.generateCacheId(COURSE_PROGRAMS, program + courseType);

        if (cacheUtil.isValidCache(cacheId).equals(true))
            return cacheUtil.getCachedData(cacheId);

        return settingsClient.getCourseProgramsByProgramAndCourseType(program,
                courseType);
    }

    public Object getCourseProgramsByProgramAndNotCourseType(String program, String courseType) {
        var cacheId = cacheUtil.generateCacheId(COURSE_PROGRAMS, program + "!" + courseType);

        if (cacheUtil.isValidCache(cacheId).equals(true))
            return cacheUtil.getCachedData(cacheId);

        return settingsClient.getCourseProgramsByProgramAndNotCourseType(program,
                courseType);
    }

}
