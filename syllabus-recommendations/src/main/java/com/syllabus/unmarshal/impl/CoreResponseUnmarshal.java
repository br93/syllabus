package com.syllabus.unmarshal.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syllabus.client.core.CoreResponse;
import com.syllabus.unmarshal.Unmarshal;

@Component
public class CoreResponseUnmarshal implements Unmarshal<CoreResponse>{

    @Override
    public CoreResponse toResponse(Object object) {
        return new ObjectMapper().convertValue(object, new TypeReference<CoreResponse>() {
        });
    }

    @Override
    public CoreResponse[] toArray(Object object) {
        return new ObjectMapper().convertValue(object,
                new TypeReference<CoreResponse[]>() {
                });
    }

    @Override
    public List<CoreResponse> toList(Object object) {
        return new ArrayList<>(Arrays.asList(toArray(object)));
    }
    
}
