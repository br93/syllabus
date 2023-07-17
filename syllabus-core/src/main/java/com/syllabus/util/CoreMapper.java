package com.syllabus.util;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.syllabus.client.settings.CourseProgramResponse;
import com.syllabus.data.CoreResponseModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CoreMapper {
    
    public CoreResponseModel toCoreResponse(CourseProgramResponse courseProgramResponse);
}
