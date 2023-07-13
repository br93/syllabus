package com.syllabus.util;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.syllabus.data.StudentRequest;
import com.syllabus.data.StudentResponse;
import com.syllabus.data.model.StudentModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {

    public StudentModel toStudentModel(StudentRequest dto);
    public StudentResponse toStudentResponse(StudentModel model);
    
}
