package com.syllabus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.model.ClassModel;

public interface ClassRepository extends JpaRepository<ClassModel, Long> {

	Optional<ClassModel> findByClassCode(String code);
}
