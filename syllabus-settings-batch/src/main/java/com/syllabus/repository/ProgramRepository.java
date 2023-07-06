package com.syllabus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.model.ProgramModel;

public interface ProgramRepository extends JpaRepository<ProgramModel, Long> {
	
	ProgramModel findByProgramCode(String code);

}
