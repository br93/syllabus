package com.syllabus.client.settings;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.syllabus.client.settings.response.ProgramResponse;
import com.syllabus.client.settings.response.UniversityCoursesResponse;
import com.syllabus.client.settings.response.UniversityResponse;

@FeignClient(name = "${settings.name}")
public interface SettingsClient{

    @GetMapping(value = "${universities.path}/{university_code}/courses")
    public UniversityCoursesResponse getCoursesByUniversity(@PathVariable("university_code") String universityCode);

    @GetMapping(value = "${universities.path}/{university_code}")
    public UniversityResponse getUniversityByIdOrCode(@PathVariable("university_code") String universityCode);

    @GetMapping(value = "${programs.path}/{program_code}")
    public ProgramResponse getProgramByIdOrCode(@PathVariable("program_code") String programCode);   
}
