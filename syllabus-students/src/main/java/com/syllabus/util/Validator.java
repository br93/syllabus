package com.syllabus.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.syllabus.client.settings.SettingsClient;
import com.syllabus.client.settings.cache.SettingsCacheClient;
import com.syllabus.client.settings.response.CourseResponse;
import com.syllabus.client.settings.response.UniversityCoursesResponse;
import com.syllabus.client.settings.response.UniversityResponse;
import com.syllabus.exception.CustomCallNotPermittedException;
import com.syllabus.exception.ProgramNotFoundException;
import com.syllabus.exception.UniversityInfoInvalidException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class Validator {

    private final SettingsClient configClient;
    private final SettingsCacheClient cacheClient;

    private final MessageUtil messageUtil;

    private static final String UNIVERSITY = "university";
    private static final String COURSES = "courses";
    private static final String PROGRAM = "program";
    
    @CircuitBreaker(name = "valid-courses", fallbackMethod = "cachedValidCourses")
    public boolean validCourses(Set<String> courses, String universityCode) {
        UniversityCoursesResponse universityCourses = configClient.getCoursesByUniversity(universityCode);
        List<CourseResponse> coursesList = universityCourses.getCourses();

        List<String> list = coursesList.stream().map(x -> x.getCourseCode()).collect(Collectors.toList());
        return list.containsAll(courses);

    }

    public boolean cachedValidCourses(Set<String> courses, String universityCode, Throwable exception) {

        var cacheId = COURSES+universityCode;

         if (cacheClient.isCached(cacheId).equals(false))
            throw new CustomCallNotPermittedException(messageUtil.getServiceUnavailableMessage(), exception);

        UniversityCoursesResponse universityCourses = cacheClient.cachedCoursesByUniversity(cacheId);
        List<CourseResponse> coursesList = universityCourses.getCourses();

        List<String> list = coursesList.stream().map(x -> x.getCourseCode()).collect(Collectors.toList());
        return list.containsAll(courses);
    }

    @CircuitBreaker(name = "validate-university-info", fallbackMethod = "cachedValidateUniversityInfo")
    public void validateUniversityInfo(String universityCode, Short term, String programCode) {
        if (!validUniversityCode(universityCode))
            throw new UniversityInfoInvalidException("university code invalid");

        try {
            Short programTerms = configClient.getProgramByIdOrCode(programCode).getTerms();
                if (!validTerm(term, programTerms))
                    throw new UniversityInfoInvalidException("term invalid");
        } catch (Exception ex) {
            throw new ProgramNotFoundException("program not found");
        }

    }

    public void cacheValidateUniversityInfo(String universityCode, Short term, String programCode, Throwable exception) {

        var programCache = PROGRAM+programCode;
        var universityCache = UNIVERSITY+universityCode;

        if (cacheClient.isCached(programCache).equals(false) || cacheClient.isCached(universityCache).equals(false))
            throw new CustomCallNotPermittedException(messageUtil.getServiceUnavailableMessage(), exception);
  
        try {
            Short programTerms = cacheClient.cachedProgramByIdOrCode(programCache).getTerms();
                if (!validTerm(term, programTerms))
                    throw new UniversityInfoInvalidException("term invalid");
        } catch (Exception ex) {
            throw new ProgramNotFoundException("program not found");
        }
    }

    @CircuitBreaker(name = "validUniversityCode", fallbackMethod = "cachedValidUniversityCode")
    public boolean validUniversityCode(String universityCode) {
        UniversityResponse university = configClient.getUniversityByIdOrCode(universityCode);

        return (university != null);
    }

    public boolean cachedValidUniversityCode(String universityCode, Throwable exception) {
        var cacheId = UNIVERSITY+universityCode;
        var isCached = cacheClient.isCached(cacheId);

        if (isCached.equals(false))
            throw new CustomCallNotPermittedException(messageUtil.getServiceUnavailableMessage(), exception);

        return isCached;
    }

    private boolean validTerm(Short term, Short programTerm) {
        return (term > 1 && term <= programTerm);
    }
    
}
