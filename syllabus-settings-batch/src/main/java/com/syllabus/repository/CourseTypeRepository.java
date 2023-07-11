package com.syllabus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.model.CourseTypeModel;

public interface CourseTypeRepository extends JpaRepository<CourseTypeModel, Long> {

	Optional<CourseTypeModel> findByTypeName(String name);
}
