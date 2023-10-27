package com.syllabus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.model.DayModel;

public interface DayRepository extends JpaRepository<DayModel, Long> {

	Optional<DayModel> findByDayNumber(Short number);
}
