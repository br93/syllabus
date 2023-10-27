package com.syllabus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.model.CourseModel;

public interface CourseRepository extends JpaRepository<CourseModel, Long> {

	Optional<CourseModel> findByCourseCode(String code);
}
