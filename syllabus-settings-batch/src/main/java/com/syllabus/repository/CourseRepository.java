package com.syllabus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.model.CourseModel;

public interface CourseRepository extends JpaRepository<CourseModel, Long> {

	CourseModel findByCourseCode(String code);
}
