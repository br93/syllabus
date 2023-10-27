package com.syllabus.client.settings.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UniversityResponse {

    @JsonProperty("university_id")
    private String universityId;

    @JsonProperty("university_name")
    private String universityName;

    @JsonProperty("university_code")
    private String universityCode;

}
