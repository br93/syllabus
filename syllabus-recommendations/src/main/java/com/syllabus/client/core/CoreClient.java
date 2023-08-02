package com.syllabus.client.core;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${core.ms.name}")
public interface CoreClient {

    @GetMapping(value = "${core.ms.students.path}/{id}/required-courses/missing")
    public Page<CoreResponse> getMissingRequiredCoursesByUserId(@PathVariable(name = "id") String userId);
   
    @GetMapping(value = "${core.ms.students.path}/{id}/elective-courses/missing")
    public Page<CoreResponse> getMissingElectiveCoursesByUserId(@PathVariable(name = "id") String userId);

    @GetMapping(value = "${core.ms.students.path}/{id}/courses-taken")
    public Page<CoreResponse> getCoursesTakenByUserId(@PathVariable(name = "id") String userId);
    
}
