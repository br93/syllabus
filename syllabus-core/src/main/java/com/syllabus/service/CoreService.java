package com.syllabus.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syllabus.client.settings.CourseProgramResponse;
import com.syllabus.config.RedisEntity;
import com.syllabus.exception.CustomCallNotPermittedException;
import com.syllabus.util.CacheUtil;
import com.syllabus.util.ConstantUtil;
import com.syllabus.util.Unmarshal;
import com.syllabus.util.Validation;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CoreService {

    private final ClientService clientService;

    private final ConstantUtil constantUtil;
    private final CacheUtil cacheUtil;

    @CircuitBreaker(name = "courses-taken", fallbackMethod = "cachedCoursesTaken")
    public List<CourseProgramResponse> getCoursesTaken(String userId) {

        var student = clientService.getStudentByUserId(userId);
        var coursePrograms = clientService.getCourseProgramsByCourseCodeIn(student.getCourseCodes());

        var cacheId = cacheUtil.generateCacheId("courses-taken", userId);
        cacheUtil.cacheData(cacheId, coursePrograms);

        return Unmarshal.toList(coursePrograms);
    }

    public List<CourseProgramResponse> cachedCoursesTaken(String userId, Throwable exception) {
        var cacheId = cacheUtil.generateCacheId("courses-taken", userId);
        var cache = cacheUtil.getCachedData(cacheId);

        if (!Validation.isValidCache(cache))
            throw new CustomCallNotPermittedException(constantUtil.getServiceUnavailableMessage(), exception);

        return Unmarshal.toList(cache);
    }

    @CircuitBreaker(name = "required-courses", fallbackMethod = "cachedRequiredCourses")
    public List<CourseProgramResponse> getAllRequiredCourses(String userId) {
        var student = clientService.getStudentByUserId(userId);
        var program = student.getProgramCode().toString();
        var requiredCourses = clientService.getCourseProgramsByProgramAndCourseType(program,
                constantUtil.getRequiredType());

        List<CourseProgramResponse> response = Unmarshal.toList(requiredCourses);

        if (Validation.hasSecondLayerCourses(program))
            response.addAll(this.getAllSecondLayerCourses(program));

        var cacheId = cacheUtil.generateCacheId("required-courses", userId);
        cacheUtil.cacheData(cacheId, requiredCourses);

        return response;
    }

    public List<CourseProgramResponse> cachedRequiredCourses(String userId, Throwable exception) {
        var cacheId = cacheUtil.generateCacheId("required-courses", userId);
        var cache = cacheUtil.getCachedData(cacheId);

        if (!Validation.isValidCache(cache))
            throw new CustomCallNotPermittedException(constantUtil.getServiceUnavailableMessage(), exception);

        return Unmarshal.toList(cache);
    }

    @CircuitBreaker(name = "missing-required-courses", fallbackMethod = "cachedMissingRequiredCourses")
    public List<CourseProgramResponse> getMissingRequiredCourses(String userId) {
        var coursesTaken = this.getCoursesTaken(userId).stream()
                .filter(x -> x.getType().equals(constantUtil.getRequiredType())).collect(Collectors.toList());
        var missingRequiredCourses = this.getAllRequiredCourses(userId);

        missingRequiredCourses.removeAll(coursesTaken);

        var cacheId = cacheUtil.generateCacheId("missing-required-courses", userId);
        cacheUtil.cacheData(cacheId, missingRequiredCourses);

        return missingRequiredCourses;
    }

    public List<CourseProgramResponse> cachedMissingRequiredCourses(String userId, Throwable exception) {
        var cacheId = cacheUtil.generateCacheId("missing-required-courses", userId);
        var cache = cacheUtil.getCachedData(cacheId);

        if (!Validation.isValidCache(cache))
            throw new CustomCallNotPermittedException(constantUtil.getServiceUnavailableMessage(), exception);

        return Unmarshal.toList(cache);
    }

    @CircuitBreaker(name = "elective-courses", fallbackMethod = "cachedElectiveCourses")
    public List<CourseProgramResponse> getAllElectiveCourses(String userId) {
        var student = clientService.getStudentByUserId(userId);
        var program = student.getProgramCode().toString();
        var electiveCourses = clientService.getCourseProgramsByProgramAndNotCourseType(program,
                constantUtil.getRequiredType());

        var cacheId = cacheUtil.generateCacheId("elective-courses", userId);
        cacheUtil.cacheData(cacheId, electiveCourses);

        return Unmarshal.toList(electiveCourses);
    }

    public List<CourseProgramResponse> cachedElectiveCourses(String userId, Throwable exception) {
        var cacheId = cacheUtil.generateCacheId("elective-courses", userId);
        var cache = cacheUtil.getCachedData(cacheId);

        if (!Validation.isValidCache(cache))
            throw new CustomCallNotPermittedException(constantUtil.getServiceUnavailableMessage(), exception);

        return Unmarshal.toList(cache);
    }

    @CircuitBreaker(name = "missing-elective-courses", fallbackMethod = "cachedMissingElectiveCourses")
    public List<CourseProgramResponse> getMissingElectiveCourses(String userId) {
        var coursesTaken = this.getCoursesTaken(userId).stream()
                .filter(x -> !x.getType().equals(constantUtil.getRequiredType())).collect(Collectors.toList());

        var missingElectiveCourses = this.getAllElectiveCourses(userId);
        missingElectiveCourses.removeAll(coursesTaken);

        var cacheId = cacheUtil.generateCacheId("missing-elective-courses", userId);
        cacheUtil.cacheData(cacheId, missingElectiveCourses);

        return missingElectiveCourses;
    }

    public List<CourseProgramResponse> cachedMissingElectiveCourses(String userId, Throwable exception) {
        var cacheId = cacheUtil.generateCacheId("missing-elective-courses", userId);
        var cache = cacheUtil.getCachedData(cacheId);

        if (!Validation.isValidCache(cache))
            throw new CustomCallNotPermittedException(constantUtil.getServiceUnavailableMessage(), exception);

        return Unmarshal.toList(cache);
    }

    private List<CourseProgramResponse> getAllSecondLayerCourses(String program) {
        var response = clientService.getCourseProgramsByProgramAndCourseType(program, constantUtil.getSecondLayer());
        return Unmarshal.toList(response);
    }

}
