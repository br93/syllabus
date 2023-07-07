package com.syllabus.data.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.index.Indexed;
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
@Document(collection = "students")
public class StudentModel implements Serializable{

    @MongoId(FieldType.OBJECT_ID)
    private String studentId;

    @Indexed(unique = true)
    private String email;

    private Integer term;

    @Field(name = "university_code")
    private String universityCode;

    @Field(name = "program_code")
    private Integer programCode;

    @Field(name = "course_codes")
    private Set<String> courseCodes = new HashSet<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Instant createdAt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Instant updatedAt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Instant deletedAt;
    
}
