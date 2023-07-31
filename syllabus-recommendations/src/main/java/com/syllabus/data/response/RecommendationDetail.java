package com.syllabus.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecommendationDetail {

    @JsonProperty(value = "class_code", index = 0)
    private String classCode;
    private String schedule;
    
}
