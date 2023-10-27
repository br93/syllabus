package com.syllabus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.model.CourseProgramModel;

public interface CourseProgramRepository extends JpaRepository<CourseProgramModel, Long> {

	Optional<CourseProgramModel> findByCourseProgramId(String id);
}
