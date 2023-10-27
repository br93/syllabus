package com.syllabus.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountResponse {

    @JsonProperty("user_id")
    private String userId;
    
    private String email;
    
    @JsonProperty("created_at")
    private String createdAt;
    
    @JsonProperty("updated_at")
    private String updatedAt;
    
}
