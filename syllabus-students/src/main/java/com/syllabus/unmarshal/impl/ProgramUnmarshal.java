package com.syllabus.unmarshal.impl;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syllabus.client.settings.response.ProgramResponse;
import com.syllabus.unmarshal.Unmarshal;

@Component
public class ProgramUnmarshal implements Unmarshal<ProgramResponse>{

    @Override
    public ProgramResponse toResponse(Object object) {
        return new ObjectMapper().convertValue(object, new TypeReference<ProgramResponse>() {

        });
    }
    
}
