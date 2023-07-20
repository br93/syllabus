package com.syllabus.config;

import java.io.Serializable;
import java.util.List;

import com.syllabus.client.settings.CourseProgramResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class RedisEntity implements Serializable {

    private List<CourseProgramResponse> response;
    
}
