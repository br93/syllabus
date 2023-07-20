package com.syllabus.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.syllabus.client.settings.CourseProgramResponse;
import com.syllabus.client.settings.SettingsClient;
import com.syllabus.client.students.StudentsClient;
import com.syllabus.config.RedisEntity;
import com.syllabus.util.CacheUtil;
import com.syllabus.util.ConstantUtil;
import com.syllabus.util.Validation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CoreService {

    private final StudentsClient studentsClient;
    private final SettingsClient settingsClient;

    private final ConstantUtil constantUtil;
    private final CacheUtil cacheUtil;

    // @Cacheable(value = "courses-taken" )
    public List<CourseProgramResponse> getCoursesTaken(String userId) {

        var cacheId = cacheUtil.generateCacheId("coursen-taken", userId);
        var cache = cacheUtil.getCachedData(cacheId);

        if (cache != null)
            return cache.getResponse();
        
        var student = studentsClient.getStudentByUserId(userId);
        var coursePrograms = settingsClient.getCourseProgramsByCourseCodeIn(student.getCourseCodes());

        cacheUtil.cacheData(cacheId, new RedisEntity(coursePrograms));

        return coursePrograms;
    }

    // @Cacheable(value = "all-required-courses", key = "#userId")
    public List<CourseProgramResponse> getAllRequiredCourses(String userId) {
        var student = studentsClient.getStudentByUserId(userId);
        var program = student.getProgramCode().toString();
        var requiredCourses = settingsClient.getCourseProgramsByProgramAndCourseType(program,
                constantUtil.getRequiredType());

        if (Validation.hasSecondLayerCourses(program)) {
            requiredCourses.addAll(this.getAllSecondLayerCourses(program));
        }

        return requiredCourses;
    }

    // @Cacheable(value = "missing-required-courses", key = "#userId")
    public List<CourseProgramResponse> getMissingRequiredCourses(String userId) {
        var coursesTaken = this.getCoursesTaken(userId).stream()
                .filter(x -> x.getType().equals(constantUtil.getRequiredType())).collect(Collectors.toList());
        var allRequiredCourses = this.getAllRequiredCourses(userId);

        allRequiredCourses.removeAll(coursesTaken);

        return allRequiredCourses;
    }

    // @Cacheable(value = "all-elective-courses", key = "#userId")
    public List<CourseProgramResponse> getAllElectiveCourses(String userId) {
        var student = studentsClient.getStudentByUserId(userId);
        var program = student.getProgramCode().toString();

        return settingsClient.getCourseProgramsByProgramAndNotCourseType(program, constantUtil.getRequiredType());

    }

    // @Cacheable(value = "missing-elective-courses", key = "#userId")
    public List<CourseProgramResponse> getMissingElectiveCourses(String userId) {
        var coursesTaken = this.getCoursesTaken(userId).stream()
                .filter(x -> !x.getType().equals(constantUtil.getRequiredType())).collect(Collectors.toList());

        var allElectiveCourses = this.getAllElectiveCourses(userId);

        allElectiveCourses.removeAll(coursesTaken);

        return allElectiveCourses;
    }

    private List<CourseProgramResponse> getAllSecondLayerCourses(String program) {
        return settingsClient.getCourseProgramsByProgramAndCourseType(program, constantUtil.getSecondLayer());
    }

}
