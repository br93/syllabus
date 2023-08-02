package com.syllabus.mapper;

import org.mapstruct.Mapper;

import com.syllabus.client.settings.response.ClassScheduleResponse;
import com.syllabus.data.model.RecommendationModel;
import com.syllabus.data.response.RecommendationResponse;
import com.syllabus.data.response.RecommendationTimetable;

@Mapper(componentModel = "spring")
public interface RecommendationMapper {

    RecommendationResponse toResponse (RecommendationModel model);
    RecommendationTimetable toTimetable (ClassScheduleResponse response);
    
}
