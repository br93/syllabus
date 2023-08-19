package com.syllabus.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentResponse implements Serializable{

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
