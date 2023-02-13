package com.syllabus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.model.DisciplinaModel;

public interface DisciplinaRepository extends JpaRepository<DisciplinaModel, Long> {

	DisciplinaModel findByCodigo(String codigo);
}
