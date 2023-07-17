package com.syllabus.client.settings;

import java.util.List;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${settings.name}")
public interface SettingsClient {

    @GetMapping(value = "${course.programs.path}/courses")
    public List<CourseProgramResponse> getCourseProgramsByCourseCodeIn(@RequestParam("code") Set<String> courseCodes);

    @GetMapping(value = "${course.programs.path}/programs/{program_code}/type/{type_name}")
    public List<CourseProgramResponse> getCourseProgramsByProgramAndCourseType(@PathVariable("program_code") String programCode, @PathVariable("type_name") String typeName);

}
