package com.syllabus.unmarshal.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syllabus.client.settings.response.ClassScheduleResponse;
import com.syllabus.unmarshal.Unmarshal;

@Component
public class ClassScheduleUnmarshal implements Unmarshal<ClassScheduleResponse>{

    @Override
    public ClassScheduleResponse toResponse(Object object) {
        return new ObjectMapper().convertValue(object, new TypeReference<ClassScheduleResponse>() {
        });
    }

    @Override
    public ClassScheduleResponse[] toArray(Object object) {
        return new ObjectMapper().convertValue(object,
                new TypeReference<ClassScheduleResponse[]>() {
                });
    }

    @Override
    public List<ClassScheduleResponse> toList(Object object) {
        return new ArrayList<>(Arrays.asList(toArray(object)));
    }
    
}
