package com.syllabus.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syllabus.client.core.CoreClient;
import com.syllabus.client.core.CoreResponse;
import com.syllabus.client.settings.SettingsClient;
import com.syllabus.client.settings.response.ClassScheduleResponse;
import com.syllabus.client.settings.response.CourseClassesResponse;
import com.syllabus.client.settings.response.PreRequisiteCountResponse;
import com.syllabus.client.settings.response.PreRequisiteCoursesResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/recommendations")
public class RecommendationController {
    
    private final CoreClient coreClient;
    private final SettingsClient settingsClient;

    @GetMapping(value = "{id}/required-courses/missing")
    public List<CoreResponse> getMissingRequiredCourses(@PathVariable(name = "id") String userId){
        return coreClient.getMissingRequiredCourses(userId).toList();
    }
   
    @GetMapping(value = "{id}/elective-courses/missing")
    public List<CoreResponse> getMissingElectiveCourses(@PathVariable(name = "id") String userId){
        return coreClient.getMissingElectiveCourses(userId).toList();
    }

    @GetMapping(value = "{course_code}/prerequisites/count")
    public PreRequisiteCountResponse getAsPreRequisiteCount(@PathVariable("course_code") String courseCode){
        return settingsClient.getAsPreRequisiteCount(courseCode);
    }

    @GetMapping(value = "{course_code}/prerequisites")
    public PreRequisiteCoursesResponse getPreRequisites(@PathVariable("course_code") String courseCode){
        return settingsClient.getPreRequisites(courseCode);
    }

    @GetMapping(value = "{course_code}/classes")
    public CourseClassesResponse getClassesByCourse(@PathVariable("course_code") String courseCode){
        return settingsClient.getClassesByCourse(courseCode);
    }

    @GetMapping(value = "{class_code}/schedules")
    public List<ClassScheduleResponse> getClassSchedulesByClassCode(@PathVariable("class_code") String courseCode){
        return settingsClient.getClassSchedulesByClassCode(courseCode);
    }
}
