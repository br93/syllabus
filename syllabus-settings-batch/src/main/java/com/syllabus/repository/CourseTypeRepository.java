package com.syllabus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.model.CourseTypeModel;

public interface CourseTypeRepository extends JpaRepository<CourseTypeModel, Long> {

	CourseTypeModel findByTypeName(String name);
}
