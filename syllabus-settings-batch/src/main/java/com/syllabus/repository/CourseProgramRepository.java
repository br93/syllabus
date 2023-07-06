package com.syllabus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.model.CourseProgramModel;

public interface CourseProgramRepository extends JpaRepository<CourseProgramModel, Long> {

	CourseProgramModel findByCourseProgramId(String id);
}
