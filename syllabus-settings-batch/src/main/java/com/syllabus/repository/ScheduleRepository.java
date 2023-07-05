package com.syllabus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.model.ScheduleModel;

public interface ScheduleRepository extends JpaRepository<ScheduleModel, Long> {

	ScheduleModel findByScheduleCode(String code);
}
