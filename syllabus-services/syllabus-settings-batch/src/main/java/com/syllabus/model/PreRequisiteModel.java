package com.syllabus.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@IdClass(PreRequisiteIdClass.class)
@Table(name = "tb_pre_requisite_courses")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class PreRequisiteModel {

    @Id
    @ManyToOne
    private CourseModel course;
    
    @Id
    @ManyToOne
    private CourseModel preRequisiteCourse;
    
}
