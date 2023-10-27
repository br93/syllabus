package com.syllabus.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.syllabus.cache.CacheConstants;
import com.syllabus.client.settings.SettingsClient;
import com.syllabus.client.settings.cache.SettingsCacheClient;
import com.syllabus.client.settings.response.CourseResponse;
import com.syllabus.client.settings.response.UniversityCoursesResponse;
import com.syllabus.client.settings.response.UniversityResponse;
import com.syllabus.exception.CustomCallNotPermittedException;
import com.syllabus.exception.ProgramNotFoundException;
import com.syllabus.exception.UniversityInfoInvalidException;
import com.syllabus.message.MessageConstants;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class Validator {

    private final SettingsClient configClient;
    private final SettingsCacheClient cacheClient;
    
    @CircuitBreaker(name = "valid-courses", fallbackMethod = "cachedValidCourses")
    public boolean validCourses(Set<String> courses, String universityCode) {
        UniversityCoursesResponse universityCourses = configClient.getCoursesByUniversity(universityCode);
        List<CourseResponse> coursesList = universityCourses.getCourses();

        List<String> list = coursesList.stream().map(x -> x.getCourseCode()).collect(Collectors.toList());
        return list.containsAll(courses);

    }

    public boolean cachedValidCourses(Set<String> courses, String universityCode, Throwable exception) {

        var cacheId = CacheConstants.COURSES+universityCode;

         if (cacheClient.isCached(cacheId))
            throw new CustomCallNotPermittedException(MessageConstants.SERVICE_UNAVAILABLE, exception);

        UniversityCoursesResponse universityCourses = cacheClient.cachedCoursesByUniversity(cacheId);
        List<CourseResponse> coursesList = universityCourses.getCourses();

        List<String> list = coursesList.stream().map(x -> x.getCourseCode()).collect(Collectors.toList());
        return list.containsAll(courses);
    }

    @CircuitBreaker(name = "validate-university-info", fallbackMethod = "cachedValidateUniversityInfo")
    public void validateUniversityInfo(String universityCode, Short term, String programCode) {
        if (!validUniversityCode(universityCode))
            throw new UniversityInfoInvalidException(MessageConstants.INVALID_UNIVERSITY_INFO_UNIVERSITY_CODE);

        try {
            Short programTerms = configClient.getProgramByIdOrCode(programCode).getTerms();
                if (!validTerm(term, programTerms))
                    throw new UniversityInfoInvalidException(MessageConstants.INVALID_UNIVERSITY_INFO_TERM);
        } catch (Exception ex) {
            throw new ProgramNotFoundException(MessageConstants.PROGRAM_NOT_FOUND);
        }

    }

    public void cacheValidateUniversityInfo(String universityCode, Short term, String programCode, Throwable exception) {

        var programCache = CacheConstants.PROGRAM+programCode;
        var universityCache = CacheConstants.UNIVERSITY+universityCode;

        if (!cacheClient.isCached(programCache) || !cacheClient.isCached(universityCache))
            throw new CustomCallNotPermittedException(MessageConstants.SERVICE_UNAVAILABLE, exception);
  
        try {
            Short programTerms = cacheClient.cachedProgramByIdOrCode(programCache).getTerms();
                if (!validTerm(term, programTerms))
                    throw new UniversityInfoInvalidException(MessageConstants.INVALID_UNIVERSITY_INFO_TERM);
        } catch (Exception ex) {
            throw new ProgramNotFoundException(MessageConstants.PROGRAM_NOT_FOUND);
        }
    }

    @CircuitBreaker(name = "validUniversityCode", fallbackMethod = "cachedValidUniversityCode")
    public boolean validUniversityCode(String universityCode) {
        UniversityResponse university = configClient.getUniversityByIdOrCode(universityCode);

        return (university != null);
    }

    public boolean cachedValidUniversityCode(String universityCode, Throwable exception) {
        var cacheId = CacheConstants.UNIVERSITY+universityCode;
        var isCached = cacheClient.isCached(cacheId);

        if (!isCached)
            throw new CustomCallNotPermittedException(MessageConstants.SERVICE_UNAVAILABLE, exception);

        return isCached;
    }

    private boolean validTerm(Short term, Short programTerm) {
        return (term > 1 && term <= programTerm);
    }
    
}
