package com.syllabus.unmarshal.impl;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syllabus.client.settings.response.UniversityCoursesResponse;
import com.syllabus.unmarshal.Unmarshal;

@Component
public class UniversityCoursesUnmarshal implements Unmarshal<UniversityCoursesResponse> {

    @Override
    public UniversityCoursesResponse toResponse(Object object) {
        return new ObjectMapper().convertValue(object, new TypeReference<UniversityCoursesResponse>() {

        });
    }

}
