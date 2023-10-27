package com.syllabus.client.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoreResponse {

    @JsonProperty("course_code")
    private String courseCode;

    @JsonProperty("course_name")
    private String courseName;

    @JsonProperty("course_type")
    private String type;

    @JsonProperty("term")
    private Short term;
    
}
