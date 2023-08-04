package com.syllabus.unmarshal.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syllabus.client.settings.response.UniversityResponse;
import com.syllabus.unmarshal.Unmarshal;

public class UniversityUnmarshal implements Unmarshal<UniversityResponse>{

    @Override
    public UniversityResponse toResponse(Object object) {
        return new ObjectMapper().convertValue(object, new TypeReference<UniversityResponse>() {

        });
    }
    
}
