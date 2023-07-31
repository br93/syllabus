package com.syllabus.util;

import org.mapstruct.Mapper;

import com.syllabus.data.model.RecommendationModel;
import com.syllabus.data.response.RecommendationResponse;

@Mapper(componentModel = "spring")
public interface RecommendationMapper {

    RecommendationResponse toResponse (RecommendationModel model);
    
}
