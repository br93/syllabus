package com.syllabus.util;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.syllabus.data.CoreResponse;
import com.syllabus.data.CourseProgramResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CoreMapper {
    
    public CoreResponse toCoreResponse(CourseProgramResponse courseProgramResponse);
}
