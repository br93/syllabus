package com.syllabus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.model.HorarioModel;

public interface HorarioRepository extends JpaRepository<HorarioModel, Long> {

	HorarioModel findBySigla(String sigla);
}
