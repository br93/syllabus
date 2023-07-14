package com.syllabus.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.syllabus.data.model.StudentModel;

public interface StudentRepository extends MongoRepository<StudentModel, String> {

    Optional<StudentModel> findByStudentIdAndDeletedAtIsNull(String studentId);
    Optional<StudentModel> findByUserIdAndDeletedAtIsNull(String userId);

}
