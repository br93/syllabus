package com.syllabus.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.syllabus.data.model.StudentDataModel;

public interface StudentDataRepository extends MongoRepository<StudentDataModel, String>{

    Optional<StudentDataModel> findByStudentDataIdAndDeletedAtIsNull(String studentDataId);
    Optional<StudentDataModel> findByUserIdAndCoursesTakenAndDeletedAtIsNull(String userId, Long coursesTaken);

    Optional<StudentDataModel> findFirstByUserIdAndDeletedAtIsNullOrderByCreatedAtDesc(String userId);
    
}
