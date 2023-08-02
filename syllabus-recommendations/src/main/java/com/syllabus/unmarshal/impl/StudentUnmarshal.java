package com.syllabus.unmarshal.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syllabus.client.students.StudentResponse;
import com.syllabus.unmarshal.Unmarshal;

public class StudentUnmarshal implements Unmarshal<StudentResponse>{

    @Override
    public StudentResponse toResponse(Object object) {
        return new ObjectMapper().convertValue(object, new TypeReference<StudentResponse>() {
        });
    }

    @Override
    public StudentResponse[] toArray(Object object) {
        return new ObjectMapper().convertValue(object,
                new TypeReference<StudentResponse[]>() {
                });
    }

    @Override
    public List<StudentResponse> toList(Object object) {
        return new ArrayList<>(Arrays.asList(toArray(object)));
    }
    
}
