package com.syllabus.client.core;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${core.ms.name}")
public interface CoreClient {

    @GetMapping(value = "${students.path}/{id}/courses-taken")
    public Page<CoreResponse> getCoursesTaken(@PathVariable(name = "id") String userId);
    
}
