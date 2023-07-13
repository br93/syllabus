package com.syllabus.client.settings.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProgramResponse {

    @JsonProperty("program_id")
    private String programId;

    @JsonProperty("program_code")
    private String programCode;

    @JsonProperty("program_name")
    private String programName;

    @JsonProperty("university_code")
    private String universityCode;

    @JsonProperty("terms")
    private Short terms;

}
