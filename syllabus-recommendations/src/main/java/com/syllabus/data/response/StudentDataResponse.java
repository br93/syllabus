package com.syllabus.data.response;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentDataResponse {

    @JsonProperty(value = "user_id", index = 0)  
    private String userId;

    @JsonProperty(value = "courses_taken", index = 1)
    private Integer coursesTaken;

    @JsonProperty(value = "student_term", index = 2)
    private Short studentTerm;
    
    @JsonProperty(value = "required_courses", index = 3)
    private Map<String, Double> requiredCourses;
    
    @JsonProperty(value = "elective_courses", index = 4)
    private Map<String, Double> electiveCourses;

    
    
}
