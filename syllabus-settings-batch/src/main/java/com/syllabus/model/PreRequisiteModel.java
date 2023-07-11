package com.syllabus.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@IdClass(PreRequisiteIdClass.class)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class PreRequisiteModel {

    @Id
    @Column(name = "course_id")
    private CourseModel course;
    
    @Id
    @Column(name = "pre_requisite_course_id")
    private CourseModel preRequisiteCourse;
    
}
