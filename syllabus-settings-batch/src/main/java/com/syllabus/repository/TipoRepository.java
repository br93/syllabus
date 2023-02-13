package com.syllabus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.model.TipoModel;

public interface TipoRepository extends JpaRepository<TipoModel, Long> {

	TipoModel findByTipoNome(String tipoNome);
}
