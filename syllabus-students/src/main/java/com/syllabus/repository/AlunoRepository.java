package com.syllabus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.syllabus.data.model.AlunoModel;

public interface AlunoRepository extends MongoRepository<AlunoModel, String> {
    
}
