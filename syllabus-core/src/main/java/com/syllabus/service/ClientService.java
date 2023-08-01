package com.syllabus.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.syllabus.client.settings.SettingsClient;
import com.syllabus.client.students.StudentsClient;
import com.syllabus.repository.CacheRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final StudentsClient studentsClient;
    private final SettingsClient settingsClient;

    private final CacheRepository cacheUtil;

    private static final String COURSE_PROGRAMS = "course-programs";
    private static final String STUDENT = "student";

    public Object getStudentByUserId(String userId) {
        var cacheId = cacheUtil.generateCacheId(STUDENT, userId);

        if (cacheUtil.hasKey(cacheId))
            return cacheUtil.getCachedData(cacheId);

        return studentsClient.getStudentByUserId(userId);
    }

    public Object getCourseProgramsByCourseCodeIn(Set<String> courseCodes) {
        var cacheId = cacheUtil.generateCacheId(COURSE_PROGRAMS, String.join("-", courseCodes));

        if (cacheUtil.hasKey(cacheId))
            return cacheUtil.getCachedData(cacheId);

        return settingsClient.getCourseProgramsByCourseCodeIn(courseCodes);
    }

    public Object getCourseProgramsByProgramAndCourseType(String program, String courseType) {
        var cacheId = cacheUtil.generateCacheId(COURSE_PROGRAMS, program + courseType);

        if (cacheUtil.hasKey(cacheId))
            return cacheUtil.getCachedData(cacheId);

        return settingsClient.getCourseProgramsByProgramAndCourseType(program,
                courseType);
    }

    public Object getCourseProgramsByProgramAndNotCourseType(String program, String courseType) {
        var cacheId = cacheUtil.generateCacheId(COURSE_PROGRAMS, program + "!" + courseType);

        if (cacheUtil.hasKey(cacheId))
            return cacheUtil.getCachedData(cacheId);

        return settingsClient.getCourseProgramsByProgramAndNotCourseType(program,
                courseType);
    }

}
