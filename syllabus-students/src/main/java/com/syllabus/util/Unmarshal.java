package com.syllabus.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syllabus.client.settings.response.ProgramResponse;
import com.syllabus.client.settings.response.UniversityCoursesResponse;
import com.syllabus.client.settings.response.UniversityResponse;
import com.syllabus.data.StudentResponse;

public class Unmarshal {

    private Unmarshal() {
    }

    public static StudentResponse toStudentResponse(Object object) {
        return new ObjectMapper().convertValue(object, new TypeReference<StudentResponse>() {
        });
    }

    public static UniversityCoursesResponse toUniversityCoursesResponse(Object object) {
        return new ObjectMapper().convertValue(object, new TypeReference<UniversityCoursesResponse>() {

        });
    }

    public static UniversityResponse toUniversityResponse(Object object) {
        return new ObjectMapper().convertValue(object, new TypeReference<UniversityResponse>() {

        });
    }

    public static ProgramResponse toProgramResponse(Object object) {
        return new ObjectMapper().convertValue(object, new TypeReference<ProgramResponse>() {

        });
    }

}
