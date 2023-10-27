package com.syllabus.client.students;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentResponse{

    @JsonProperty("student_id")
    private String studentId;

    private Integer term;
    
    @JsonProperty("university_code")
    private String universityCode;
    
    @JsonProperty("program_code")
    private Integer programCode;
    
    @JsonProperty("course_codes")
    private Set<String> courseCodes = new HashSet<>();
    
}
