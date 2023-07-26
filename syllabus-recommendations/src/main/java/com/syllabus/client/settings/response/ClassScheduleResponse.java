package com.syllabus.client.settings.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClassScheduleResponse {

    @JsonProperty("class_schedule_id")
    private String classScheduleId;

    @JsonProperty("class_code")
    private String classCode;
    
    @JsonProperty("schedule")
    private String schedule;

    @JsonProperty("time_of_day")
    private String timeOfDay;
    
}