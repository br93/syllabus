package com.syllabus.cache;

import java.util.List;

import org.springframework.stereotype.Service;

import com.syllabus.client.core.CoreResponse;
import com.syllabus.client.settings.response.ClassScheduleResponse;
import com.syllabus.client.settings.response.CourseClassesResponse;
import com.syllabus.client.settings.response.PreRequisiteCountResponse;
import com.syllabus.client.settings.response.PreRequisiteCoursesResponse;
import com.syllabus.client.students.StudentResponse;
import com.syllabus.unmarshal.impl.ClassScheduleUnmarshal;
import com.syllabus.unmarshal.impl.CoreUnmarshal;
import com.syllabus.unmarshal.impl.CourseClassesUnmarshal;
import com.syllabus.unmarshal.impl.PreRequisiteCountUnmarshal;
import com.syllabus.unmarshal.impl.PreRequisiteCoursesUnmarshal;
import com.syllabus.unmarshal.impl.StudentUnmarshal;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CacheService {

    private final CacheRepository cacheRepository;

    private final ClassScheduleUnmarshal classScheduleUnmarshal;
    private final CoreUnmarshal coreUnmarshal;
    private final CourseClassesUnmarshal courseClassesUnmarshal;
    private final PreRequisiteCountUnmarshal preRequisiteCountUnmarshal;
    private final PreRequisiteCoursesUnmarshal preRequisiteCoursesUnmarshal;
    private final StudentUnmarshal studentUnmarshal;

    public String generateCacheId(String key, String id) {
        return cacheRepository.generateCacheId(key, id);
    }

    public List<CoreResponse> getMissingRequiredCoursesByUserId(String userId) {
        var cacheId = cacheRepository.generateCacheId(CacheConstants.MISSING_REQUIRED, userId);
        var cache = cacheRepository.getCachedData(cacheId);

        return coreUnmarshal.toList(cache);
    }

    public List<CoreResponse> getMissingElectiveCoursesByUserId(String userId) {
        var cacheId = cacheRepository.generateCacheId(CacheConstants.MISSING_ELECTIVE, userId);
        var cache = cacheRepository.getCachedData(cacheId);

        return coreUnmarshal.toList(cache);
    }

    public List<CoreResponse> getCoursesTakenByUserId(String userId) {
        var cacheId = cacheRepository.generateCacheId(CacheConstants.MISSING_ELECTIVE, userId);
        var cache = cacheRepository.getCachedData(cacheId);

        return coreUnmarshal.toList(cache);
    }

    public PreRequisiteCountResponse getAsPreRequisiteCountByCourseCode(String courseCode) {
        var cacheId = cacheRepository.generateCacheId(CacheConstants.PRE_REQUISITES_COUNT, courseCode);
        var cache = cacheRepository.getCachedData(cacheId);

        return preRequisiteCountUnmarshal.toResponse(cache);
    }

    public PreRequisiteCoursesResponse getPreRequisitesByCourseCode(String courseCode) {
        var cacheId = cacheRepository.generateCacheId(CacheConstants.PRE_REQUISITES, courseCode);
        var cache = cacheRepository.getCachedData(cacheId);

        return preRequisiteCoursesUnmarshal.toResponse(cache);
    }

    public CourseClassesResponse getClassesByCourse(String courseCode) {
        var cacheId = cacheRepository.generateCacheId(CacheConstants.CLASSES, courseCode);
        var cache = cacheRepository.getCachedData(cacheId);

        return courseClassesUnmarshal.toResponse(cache);
    }

    public List<ClassScheduleResponse> getClassSchedulesByClassCode(String courseCode) {
        var cacheId = cacheRepository.generateCacheId(CacheConstants.CLASS_SCHEDULES, courseCode);
        var cache = cacheRepository.getCachedData(cacheId);

        return classScheduleUnmarshal.toList(cache);
    }

    public StudentResponse getStudentByUserId(String userId) {
        var cacheId = cacheRepository.generateCacheId(CacheConstants.STUDENTS, userId);
        var cache = cacheRepository.getCachedData(cacheId);

        return studentUnmarshal.toResponse(cache);
    }

    public boolean hasKey(String key){
        return cacheRepository.hasKey(key);
    }

    public void flushCache() {
        cacheRepository.flushCache();
    }

}
