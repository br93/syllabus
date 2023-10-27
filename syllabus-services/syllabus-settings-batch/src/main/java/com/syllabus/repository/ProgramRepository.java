package com.syllabus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.model.ProgramModel;

public interface ProgramRepository extends JpaRepository<ProgramModel, Long> {
	
	Optional<ProgramModel> findByProgramCode(String code);

}
