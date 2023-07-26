package com.syllabus.data.model;

import java.time.Instant;
import java.util.Set;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "recommendations")
@CompoundIndex(name = "recommendations_required", def = "{'recommendation' : 1, 'is_required': 1}", unique = true)
public class RecommendationModel {

    @MongoId(FieldType.OBJECT_ID)
    private String recommendationId;

    private String userId;
    
    private Set<String> recommendation;
    
    @Field("is_required")
    private Boolean recommendingRequired;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Instant createdAt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Instant updatedAt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Instant deletedAt;
    
}
