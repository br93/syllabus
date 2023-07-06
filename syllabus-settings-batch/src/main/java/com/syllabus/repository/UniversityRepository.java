package com.syllabus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.model.UniversityModel;

public interface UniversityRepository extends JpaRepository<UniversityModel, Long>{

    UniversityModel findByUniversityCode(String code);
    
}
