package com.syllabus.unmarshal.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syllabus.client.settings.response.PreRequisiteCountResponse;
import com.syllabus.unmarshal.Unmarshal;

public class PreRequisiteCountResponseUnmarshal implements Unmarshal<PreRequisiteCountResponse>{

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
