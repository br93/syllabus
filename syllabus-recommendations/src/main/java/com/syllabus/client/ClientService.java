package com.syllabus.client;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.syllabus.cache.CacheConstants;
import com.syllabus.cache.CacheService;
import com.syllabus.client.core.CoreClient;
import com.syllabus.client.core.CoreResponse;
import com.syllabus.client.settings.SettingsClient;
import com.syllabus.client.settings.response.ClassScheduleResponse;
import com.syllabus.client.settings.response.CourseClassesResponse;
import com.syllabus.client.settings.response.PreRequisiteCountResponse;
import com.syllabus.client.settings.response.PreRequisiteCoursesResponse;
import com.syllabus.client.students.StudentResponse;
import com.syllabus.client.students.StudentsClient;
import com.syllabus.unmarshal.impl.ClassScheduleUnmarshal;
import com.syllabus.unmarshal.impl.CoreUnmarshal;
import com.syllabus.unmarshal.impl.CourseClassesUnmarshal;
import com.syllabus.unmarshal.impl.PreRequisiteCountUnmarshal;
import com.syllabus.unmarshal.impl.PreRequisiteCoursesUnmarshal;
import com.syllabus.unmarshal.impl.StudentUnmarshal;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final CacheService cacheService;

    private final CoreClient coreClient;
    private final SettingsClient settingsClient;
    private final StudentsClient studentsClient;

    private final ClassScheduleUnmarshal classScheduleUnmarshal;
    private final CoreUnmarshal coreUnmarshal;
    private final CourseClassesUnmarshal coursesClassesUnmarshal;
    private final PreRequisiteCountUnmarshal preRequisiteCountUnmarshal;
    private final PreRequisiteCoursesUnmarshal preRequisiteCoursesUnmarshal;
    private final StudentUnmarshal studentUnmarshal;

    public CourseClassesResponse getClassesByCourse(String courseCode) {

        var cacheId = cacheService.generateCacheId(CacheConstants.CLASSES, courseCode);

        if (cacheService.hasKey(cacheId))
            return cacheService.getClassesByCourse(courseCode);

        return settingsClient.getClassesByCourse(courseCode);
    }

    public List<ClassScheduleResponse> getClassSchedulesByClassCode(String classCode) {

        var cacheId = cacheService.generateCacheId(CacheConstants.CLASS_SCHEDULES, classCode);

        if (cacheService.hasKey(cacheId))
            return cacheService.getClassSchedulesByClassCode(classCode);
        return settingsClient.getClassSchedulesByClassCode(classCode);
    }

    public StudentResponse getStudentByUserId(String userId) {

        var cacheId = cacheService.generateCacheId(CacheConstants.STUDENTS, userId);

        if (cacheService.hasKey(cacheId))
            return cacheService.getStudentByUserId(userId);
        return studentsClient.getStudentByUserId(userId);
    }

    public List<CoreResponse> getCoursesTakenByUserId(String userId) {

        var cacheId = cacheService.generateCacheId(CacheConstants.COURSES_TAKEN, userId);

        if (cacheService.hasKey(cacheId))
            return cacheService.getCoursesTakenByUserId(userId);

        return coreClient.getCoursesTakenByUserId(userId).toList();

    }

    public List<CoreResponse> getMissingRequiredCoursesByUserId(String userId) {

        var cacheId = cacheService.generateCacheId(CacheConstants.MISSING_REQUIRED, userId);

        if (cacheService.hasKey(cacheId))
            return cacheService.getMissingRequiredCoursesByUserId(userId);
        return coreClient.getMissingRequiredCoursesByUserId(userId).get()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<CoreResponse> getMissingElectiveCoursesByUserId(String userId) {

        var cacheId = cacheService.generateCacheId(CacheConstants.MISSING_ELECTIVE, userId);

        if (cacheService.hasKey(cacheId))
            return cacheService.getMissingElectiveCoursesByUserId(userId);
        return coreClient.getMissingElectiveCoursesByUserId(userId).get()
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public PreRequisiteCoursesResponse getPreRequisitesByCourseCode(String courseCode) {

        var cacheId = cacheService.generateCacheId(CacheConstants.PRE_REQUISITES, courseCode);

        if (cacheService.hasKey(cacheId))
            return cacheService.getPreRequisitesByCourseCode(courseCode);
        return settingsClient.getPreRequisitesByCourseCode(courseCode);
    }

    public PreRequisiteCountResponse getAsPreRequisiteCountByCourseCode(String courseCode) {

        var cacheId = cacheService.generateCacheId(CacheConstants.PRE_REQUISITES_COUNT, courseCode);

        if (cacheService.hasKey(cacheId))
            return cacheService.getAsPreRequisiteCountByCourseCode(courseCode);
        return settingsClient.getAsPreRequisiteCountByCourseCode(courseCode);
    }

}
