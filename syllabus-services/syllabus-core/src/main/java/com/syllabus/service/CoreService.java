package com.syllabus.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.syllabus.data.StudentResponse;
import com.syllabus.data.CourseProgramResponse;
import com.syllabus.exception.CustomCallNotPermittedException;
import com.syllabus.kafka.KafkaConsumer;
import com.syllabus.repository.CacheRepository;
import com.syllabus.util.ConstantUtil;
import com.syllabus.util.Unmarshal;
import com.syllabus.util.Validation;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CoreService {

    private final ClientService clientService;
    private final KafkaConsumer kafkaConsumer;

    private final ConstantUtil constantUtil;
    private final CacheRepository cacheUtil;

    @CircuitBreaker(name = "courses-taken", fallbackMethod = "cachedCoursesTaken")
    public List<CourseProgramResponse> getCoursesTaken(String userId) {

        var student = this.getStudentInfo(userId);

        var coursePrograms = clientService.getCourseProgramsByCourseCodeIn(student.getCourseCodes());

        var cacheId = cacheUtil.generateCacheId("courses-taken", userId);
        cacheUtil.cacheData(cacheId, coursePrograms);

        return Unmarshal.toList(coursePrograms);
    }

    public List<CourseProgramResponse> cachedCoursesTaken(String userId, Throwable exception) {
        var cacheId = cacheUtil.generateCacheId("courses-taken", userId);

        if (!cacheUtil.hasKey(cacheId))
            throw new CustomCallNotPermittedException(constantUtil.serviceUnavailableMessage(), exception);

        var cache = cacheUtil.getCachedData(cacheId);
        return Unmarshal.toList(cache);
    }

    @CircuitBreaker(name = "required-courses", fallbackMethod = "cachedRequiredCourses")
    public List<CourseProgramResponse> getAllRequiredCourses(String userId) {

        var student = this.getStudentInfo(userId);

        var program = student.getProgramCode().toString();
        var requiredCourses = clientService.getCourseProgramsByProgramAndCourseType(program,
                constantUtil.constantRequiredType());

        List<CourseProgramResponse> response = Unmarshal.toList(requiredCourses);

        if (Validation.hasSecondLayerCourses(program))
            response.addAll(this.getAllSecondLayerCourses(program));

        var cacheId = cacheUtil.generateCacheId("required-courses", userId);
        cacheUtil.cacheData(cacheId, requiredCourses);

        return response;
    }

    public List<CourseProgramResponse> cachedRequiredCourses(String userId, Throwable exception) {
        var cacheId = cacheUtil.generateCacheId("required-courses", userId);

        if (!cacheUtil.hasKey(cacheId))
            throw new CustomCallNotPermittedException(constantUtil.serviceUnavailableMessage(), exception);

        var cache = cacheUtil.getCachedData(cacheId);

        return Unmarshal.toList(cache);
    }

    @CircuitBreaker(name = "missing-required-courses", fallbackMethod = "cachedMissingRequiredCourses")
    public List<CourseProgramResponse> getMissingRequiredCourses(String userId) {

        var coursesTaken = this.getCoursesTaken(userId).stream()
                .filter(x -> x.getType().equals(constantUtil.constantRequiredType()))
                .collect(Collectors.toList());

        var missingRequiredCourses = this.getAllRequiredCourses(userId);

        missingRequiredCourses.removeAll(coursesTaken);

        var cacheId = cacheUtil.generateCacheId("missing-required-courses", userId);
        cacheUtil.cacheData(cacheId, missingRequiredCourses);

        return missingRequiredCourses;
    }

    public List<CourseProgramResponse> cachedMissingRequiredCourses(String userId, Throwable exception) {
        var cacheId = cacheUtil.generateCacheId("missing-required-courses", userId);

        if (!cacheUtil.hasKey(cacheId))
            throw new CustomCallNotPermittedException(constantUtil.serviceUnavailableMessage(), exception);

        var cache = cacheUtil.getCachedData(cacheId);

        return Unmarshal.toList(cache);
    }

    @CircuitBreaker(name = "elective-courses", fallbackMethod = "cachedElectiveCourses")
    public List<CourseProgramResponse> getAllElectiveCourses(String userId) {

        var student = this.getStudentInfo(userId);

        var program = student.getProgramCode().toString();
        var electiveCourses = clientService.getCourseProgramsByProgramAndNotCourseType(program,
                constantUtil.constantRequiredType());

        var cacheId = cacheUtil.generateCacheId("elective-courses", userId);
        cacheUtil.cacheData(cacheId, electiveCourses);

        var list = Unmarshal.toList(electiveCourses);

        return list.stream().filter(x -> !x.getType().equals(constantUtil.constantSecondLayer()))
                .collect(Collectors.toList());
    }

    public List<CourseProgramResponse> cachedElectiveCourses(String userId, Throwable exception) {
        var cacheId = cacheUtil.generateCacheId("elective-courses", userId);

        if (!cacheUtil.hasKey(cacheId))
            throw new CustomCallNotPermittedException(constantUtil.serviceUnavailableMessage(), exception);

        var cache = cacheUtil.getCachedData(cacheId);

        return Unmarshal.toList(cache);
    }

    @CircuitBreaker(name = "missing-elective-courses", fallbackMethod = "cachedMissingElectiveCourses")
    public List<CourseProgramResponse> getMissingElectiveCourses(String userId) {

        var coursesTaken = this.getCoursesTaken(userId).stream()
                .filter(x -> !x.getType().equals(constantUtil.constantRequiredType()))
                .collect(Collectors.toList());

        var missingElectiveCourses = this.getAllElectiveCourses(userId);
        missingElectiveCourses.removeAll(coursesTaken);

        var cacheId = cacheUtil.generateCacheId("missing-elective-courses", userId);
        cacheUtil.cacheData(cacheId, missingElectiveCourses);

        return missingElectiveCourses;
    }

    public List<CourseProgramResponse> cachedMissingElectiveCourses(String userId, Throwable exception) {
        var cacheId = cacheUtil.generateCacheId("missing-elective-courses", userId);

        if (!cacheUtil.hasKey(cacheId))
            throw new CustomCallNotPermittedException(constantUtil.serviceUnavailableMessage(), exception);

        var cache = cacheUtil.getCachedData(cacheId);
        return Unmarshal.toList(cache);
    }

    private List<CourseProgramResponse> getAllSecondLayerCourses(String program) {
        var response = clientService.getCourseProgramsByProgramAndCourseType(program, constantUtil.constantSecondLayer());
        return Unmarshal.toList(response);
    }

    private StudentResponse getStudentInfo(String userId){
        var studentMessage = kafkaConsumer.getMessage();

        if(studentMessage != null){
            System.out.println("Message");
            return studentMessage;
        }

        var studentObject = clientService.getStudentByUserId(userId);
        return Unmarshal.toStudentResponse(studentObject);
    }

}
