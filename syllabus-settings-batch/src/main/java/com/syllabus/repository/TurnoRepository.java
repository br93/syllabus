package com.syllabus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.model.TurnoModel;

public interface TurnoRepository extends JpaRepository<TurnoModel, Long> {

	TurnoModel findByTurnoSigla(String turnoSigla);
}
