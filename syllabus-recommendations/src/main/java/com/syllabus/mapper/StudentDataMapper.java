package com.syllabus.mapper;

import org.mapstruct.Mapper;

import com.syllabus.data.model.StudentDataModel;
import com.syllabus.data.response.StudentDataResponse;

@Mapper(componentModel = "spring")
public interface StudentDataMapper {
    
    StudentDataResponse toResponse (StudentDataModel model);
}
