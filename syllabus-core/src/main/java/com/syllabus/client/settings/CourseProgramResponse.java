package com.syllabus.client.settings;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseProgramResponse {

    @JsonProperty("course_program_id")
    private String courseProgramId;

    @JsonProperty("course_code")
    private String courseCode;

    @JsonProperty("course_name")
    private String courseName;

    @JsonProperty("course_type")
    private String type;

    @JsonProperty("term")
    private Short term;
    
}
