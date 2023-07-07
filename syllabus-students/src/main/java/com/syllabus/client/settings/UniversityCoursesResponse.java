package com.syllabus.client.settings;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UniversityCoursesResponse {

    @JsonProperty("university_id")
    private String universityId;

    @JsonProperty("university_code")
    private String universityCode;

    @JsonProperty("courses")
    private List<CourseResponse> courses;
    
}
