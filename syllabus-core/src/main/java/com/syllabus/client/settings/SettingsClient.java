package com.syllabus.client.settings;

import java.util.List;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${settings.name}")
public interface SettingsClient {

    @GetMapping(value = "${course.programs.path}")
    public List<CourseProgramResponse> getCourseProgramsByCourseCodeIn(@RequestParam("code") Set<String> courseCodes);
    
}
