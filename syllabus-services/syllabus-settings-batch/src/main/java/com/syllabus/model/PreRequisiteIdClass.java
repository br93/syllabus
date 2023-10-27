package com.syllabus.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PreRequisiteIdClass{

    private CourseModel course;
    private CourseModel preRequisiteCourse;
    
}
