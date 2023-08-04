package com.syllabus.client.settings.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PreRequisiteCountResponse {
    
    @JsonProperty("course_code")
    private String courseCode;

    @JsonProperty("as_pre_requisite")
    private Short count;
}
