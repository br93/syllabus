package com.syllabus.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.syllabus.client.settings.SettingsClient;
import com.syllabus.client.settings.response.CourseResponse;
import com.syllabus.client.settings.response.UniversityCoursesResponse;
import com.syllabus.client.settings.response.UniversityResponse;
import com.syllabus.exception.ProgramNotFoundException;
import com.syllabus.exception.UniversityInfoInvalidException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class Validator {

    private final SettingsClient configClient;

    public boolean validCourses(Set<String> courses, String universityCode) {
        UniversityCoursesResponse universityCourses = configClient.getCoursesByUniversity(universityCode);
        List<CourseResponse> coursesList = universityCourses.getCourses();

        List<String> list = coursesList.stream().map(x -> x.getCourseCode()).collect(Collectors.toList());
        return list.containsAll(courses);

    }

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

    public boolean validUniversityCode(String universityCode) {
        UniversityResponse university = configClient.getUniversityByIdOrCode(universityCode);

        return (university != null);
    }

    private boolean validTerm(Short term, Short programTerm) {
        return (term > 1 && term <= programTerm);
    }
    
}
