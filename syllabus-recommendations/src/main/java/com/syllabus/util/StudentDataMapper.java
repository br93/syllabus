package com.syllabus.util;

import org.mapstruct.Mapper;

import com.syllabus.client.core.response.StudentDataResponse;
import com.syllabus.data.model.StudentDataModel;

@Mapper(componentModel = "spring")
public interface StudentDataMapper {
    
    StudentDataResponse toResponse (StudentDataModel model);
}
