package com.syllabus.client.core;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${core.ms.name}")
public interface CoreClient {

    @GetMapping(value = "${core.ms.students.path}/{id}/required-courses/missing")
    public Page<CoreResponse> getMissingRequiredCourses(@PathVariable(name = "id") String userId);
   
    @GetMapping(value = "${core.ms.students.path}/{id}/elective-courses/missing")
    public Page<CoreResponse> getMissingElectiveCourses(@PathVariable(name = "id") String userId);
    
}
