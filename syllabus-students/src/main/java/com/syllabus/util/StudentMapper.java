package com.syllabus.util;

import org.mapstruct.Mapper;

import com.syllabus.data.StudentRequest;
import com.syllabus.data.StudentResponse;
import com.syllabus.data.model.StudentModel;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    public StudentModel toStudentModel(StudentRequest dto);
    public StudentResponse toStudentResponse(StudentModel model);
    
}
