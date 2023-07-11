package com.syllabus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.model.PreRequisiteIdClass;
import com.syllabus.model.PreRequisiteModel;

public interface PreRequisiteRepository extends JpaRepository<PreRequisiteModel, PreRequisiteIdClass> {
    
}
