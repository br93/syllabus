package com.syllabus.client.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${config.name}")
public interface ConfigClient {

    @GetMapping(value = "${universities.path}/{university_code}/courses")
    public UniversityCoursesResponse getCoursesByUniversity(@PathVariable("university_code") String universityCode);
    
}
