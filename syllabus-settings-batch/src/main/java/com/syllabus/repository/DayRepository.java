package com.syllabus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.model.DayModel;

public interface DayRepository extends JpaRepository<DayModel, Long> {

	DayModel findByDayNumber(Short number);
}
