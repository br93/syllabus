package com.syllabus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.model.ClassScheduleModel;

public interface ClassScheduleRepository extends JpaRepository<ClassScheduleModel, Long> {

	Optional<ClassScheduleModel> findByClassScheduleId(String id);
}
