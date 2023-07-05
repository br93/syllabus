package com.syllabus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.model.ClassModel;

public interface ClassRepository extends JpaRepository<ClassModel, Long> {

	ClassModel findByClassCode(String code);
}
