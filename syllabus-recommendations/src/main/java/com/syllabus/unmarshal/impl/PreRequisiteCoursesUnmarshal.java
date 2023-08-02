package com.syllabus.unmarshal.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syllabus.client.settings.response.PreRequisiteCoursesResponse;
import com.syllabus.unmarshal.Unmarshal;

@Component
public class PreRequisiteCoursesUnmarshal implements Unmarshal<PreRequisiteCoursesResponse>{

    @Override
    public PreRequisiteCoursesResponse toResponse(Object object) {
        return new ObjectMapper().convertValue(object, new TypeReference<PreRequisiteCoursesResponse>() {
        });
    }

    @Override
    public PreRequisiteCoursesResponse[] toArray(Object object) {
        return new ObjectMapper().convertValue(object,
                new TypeReference<PreRequisiteCoursesResponse[]>() {
                });
    }

    @Override
    public List<PreRequisiteCoursesResponse> toList(Object object) {
        return new ArrayList<>(Arrays.asList(toArray(object)));
    }
    
}
