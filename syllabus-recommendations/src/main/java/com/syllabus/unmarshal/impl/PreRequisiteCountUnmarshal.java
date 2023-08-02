package com.syllabus.unmarshal.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syllabus.client.settings.response.PreRequisiteCountResponse;
import com.syllabus.unmarshal.Unmarshal;

@Component
public class PreRequisiteCountUnmarshal implements Unmarshal<PreRequisiteCountResponse>{

    @Override
    public PreRequisiteCountResponse toResponse(Object object) {
        return new ObjectMapper().convertValue(object, new TypeReference<PreRequisiteCountResponse>() {
        });
    }

    @Override
    public PreRequisiteCountResponse[] toArray(Object object) {
        return new ObjectMapper().convertValue(object,
                new TypeReference<PreRequisiteCountResponse[]>() {
                });
    }

    @Override
    public List<PreRequisiteCountResponse> toList(Object object) {
        return new ArrayList<>(Arrays.asList(toArray(object)));
    }
    
}
