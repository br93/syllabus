package com.syllabus.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.syllabus.data.model.AlunoModel;


public interface AlunoRepository extends MongoRepository<AlunoModel, String> {

    Optional<AlunoModel> findByAlunoIdAndDeletedAtIsNull(String alunoId);
    
}
