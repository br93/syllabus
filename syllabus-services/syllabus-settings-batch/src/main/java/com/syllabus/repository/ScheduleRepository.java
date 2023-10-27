package com.syllabus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.model.ScheduleModel;

public interface ScheduleRepository extends JpaRepository<ScheduleModel, Long> {

	Optional<ScheduleModel> findByScheduleCode(String code);
}
