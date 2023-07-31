package com.syllabus.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.syllabus.data.model.RecommendationModel;

public interface RecommendationRepository extends MongoRepository<RecommendationModel, String>{

    Optional<RecommendationModel> findByRecommendationIdAndDeletedAtIsNull(String studentDataId);
    Optional<RecommendationModel> findFirstByUserIdAndDeletedAtIsNullOrderByCreatedAtDesc(String userId);
    
}
