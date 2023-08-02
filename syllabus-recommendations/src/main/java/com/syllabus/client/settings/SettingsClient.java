package com.syllabus.client.settings;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.syllabus.client.settings.response.ClassScheduleResponse;
import com.syllabus.client.settings.response.CourseClassesResponse;
import com.syllabus.client.settings.response.PreRequisiteCountResponse;
import com.syllabus.client.settings.response.PreRequisiteCoursesResponse;

@FeignClient(name = "${settings.ms.name}")
public interface SettingsClient {

    @GetMapping(value = "${settings.ms.courses.path}/{course_code}/prerequisites/count")
    public PreRequisiteCountResponse getAsPreRequisiteCountByCourseCode(@PathVariable("course_code") String courseCode);

    @GetMapping(value = "${settings.ms.courses.path}/{course_code}/prerequisites")
    public PreRequisiteCoursesResponse getPreRequisitesByCourseCode(@PathVariable("course_code") String courseCode);

    @GetMapping(value = "${settings.ms.courses.path}/{course_code}/classes")
    public CourseClassesResponse getClassesByCourse(@PathVariable("course_code") String courseCode);

    @GetMapping(value = "${settings.ms.classes.path}/{class_code}/schedules")
    public List<ClassScheduleResponse> getClassSchedulesByClassCode(@PathVariable("class_code") String courseCode);
    
}
