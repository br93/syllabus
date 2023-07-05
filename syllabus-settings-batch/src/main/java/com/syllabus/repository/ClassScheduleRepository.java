package com.syllabus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.model.ClassScheduleModel;

public interface ClassScheduleRepository extends JpaRepository<ClassScheduleModel, Long> {

	ClassScheduleModel findByClassScheduleId(String id);
}
