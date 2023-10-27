package com.syllabus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syllabus.model.UniversityModel;

public interface UniversityRepository extends JpaRepository<UniversityModel, Long>{

    Optional<UniversityModel> findByUniversityCode(String code);
    
}
