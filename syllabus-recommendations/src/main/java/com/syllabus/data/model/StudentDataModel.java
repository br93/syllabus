package com.syllabus.data.model;

import java.time.Instant;
import java.util.Map;

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
@Document(collection = "students_data")
@CompoundIndex(name = "user_courses", def = "{'user_id' : 1, 'courses_taken': 1}", unique = true)
public class StudentDataModel {

    @MongoId(FieldType.OBJECT_ID)
    private String studentDataId;
    
    private String userId;

    @Field(name = "courses_taken")
    private Short coursesTaken;
    
    @Field(name = "required_courses")
    private Map<String, Short> requiredCourses;
    
    @Field(name = "elective_courses")
    private Map<String, Short> electiveCourses;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Instant createdAt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Instant updatedAt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Instant deletedAt;

    
}
