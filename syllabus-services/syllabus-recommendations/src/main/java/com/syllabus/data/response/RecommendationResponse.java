package com.syllabus.data.response;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecommendationResponse {

    @JsonProperty(value = "recommendation_id", index = 0)
    private String recommendationId;

    private Set<String> recommendation;

    private Integer workload;
    
}
