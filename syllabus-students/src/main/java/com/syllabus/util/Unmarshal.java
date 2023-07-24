package com.syllabus.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syllabus.data.StudentResponse;

public class Unmarshal {

    private Unmarshal(){}

    public static StudentResponse toStudentResponse(Object object) {
        return new ObjectMapper().convertValue(object, new TypeReference<StudentResponse>() {
        });
    }
    
}
