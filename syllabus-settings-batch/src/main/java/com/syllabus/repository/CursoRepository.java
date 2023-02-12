package com.syllabus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.model.CursoModel;

public interface CursoRepository extends JpaRepository<CursoModel, Long> {

}
