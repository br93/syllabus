package com.syllabus.unmarshal.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syllabus.client.settings.response.CourseClassesResponse;
import com.syllabus.unmarshal.Unmarshal;

@Component
public class CourseClassesUnmarshal implements Unmarshal<CourseClassesResponse>{

    @Override
    public CourseClassesResponse toResponse(Object object) {
        return new ObjectMapper().convertValue(object, new TypeReference<CourseClassesResponse>() {
        });
    }

    @Override
    public CourseClassesResponse[] toArray(Object object) {
        return new ObjectMapper().convertValue(object,
                new TypeReference<CourseClassesResponse[]>() {
                });
    }

    @Override
    public List<CourseClassesResponse> toList(Object object) {
        return new ArrayList<>(Arrays.asList(toArray(object)));
    }
    
}
