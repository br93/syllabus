package com.syllabus.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syllabus.data.StudentResponse;
import com.syllabus.data.CourseProgramResponse;

public class Unmarshal {

    private Unmarshal() {
    }

    private static CourseProgramResponse[] toArray(Object object) {
        return new ObjectMapper().convertValue(object,
                new TypeReference<CourseProgramResponse[]>() {
                });
    }

    public static List<CourseProgramResponse> toList(Object object) {
        return new ArrayList<>(Arrays.asList(toArray(object)));
    }

    public static StudentResponse toStudentResponse(Object object) {
        return new ObjectMapper().convertValue(object, new TypeReference<StudentResponse>() {
        });
    }

}
