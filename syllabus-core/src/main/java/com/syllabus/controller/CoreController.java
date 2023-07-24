package com.syllabus.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syllabus.client.settings.CourseProgramResponse;
import com.syllabus.data.CoreResponseModel;
import com.syllabus.service.CoreService;
import com.syllabus.util.CoreMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/core")
public class CoreController {

    private final CoreService coreService;
    private final CoreMapper coreMapper;

    @GetMapping("students/{id}/courses-taken")
    public ResponseEntity<Page<CoreResponseModel>> getCoursesTaken(@PageableDefault Pageable pageable, @PathVariable(name = "id") String userId) {
        
        var coursePrograms = coreService.getCoursesTaken(userId);
        Page<CourseProgramResponse> pageCoursePrograms = new PageImpl<>(coursePrograms, pageable, coursePrograms.size());
    
        return new ResponseEntity<>(pageCoursePrograms.map(coreMapper::toCoreResponse), HttpStatus.OK);

    }

    @GetMapping("students/{id}/required-courses")
    public ResponseEntity<Page<CoreResponseModel>> getAllRequiredCourses(@PageableDefault Pageable pageable, @PathVariable(name = "id") String userId) {
        
        var coursePrograms = coreService.getAllRequiredCourses(userId);
        Page<CourseProgramResponse> pageCoursePrograms = new PageImpl<>(coursePrograms, pageable, coursePrograms.size());

        return new ResponseEntity<>(pageCoursePrograms.map(coreMapper::toCoreResponse), HttpStatus.OK);
    }

    @GetMapping("students/{id}/required-courses/missing")
    public ResponseEntity<Page<CoreResponseModel>> getMissingRequiredCourses(@PageableDefault Pageable pageable, @PathVariable(name = "id") String userId) {
        
        var coursePrograms = coreService.getMissingRequiredCourses(userId);
        Page<CourseProgramResponse> pageCoursePrograms = new PageImpl<>(coursePrograms, pageable, coursePrograms.size());

        return new ResponseEntity<>(pageCoursePrograms.map(coreMapper::toCoreResponse), HttpStatus.OK);
    }

    @GetMapping("students/{id}/elective-courses")
    public ResponseEntity<Page<CoreResponseModel>> getAllElectiveCourses(@PageableDefault Pageable pageable, @PathVariable(name = "id") String userId) {
        
        var coursePrograms = coreService.getAllElectiveCourses(userId);
        Page<CourseProgramResponse> pageCoursePrograms = new PageImpl<>(coursePrograms, pageable, coursePrograms.size());

        return new ResponseEntity<>(pageCoursePrograms.map(coreMapper::toCoreResponse), HttpStatus.OK);
    }

    @GetMapping("students/{id}/elective-courses/missing")
    public ResponseEntity<Page<CoreResponseModel>> getMissingElectiveCourses(@PageableDefault Pageable pageable, @PathVariable(name = "id") String userId) {
        
        var coursePrograms = coreService.getMissingElectiveCourses(userId);
        Page<CourseProgramResponse> pageCoursePrograms = new PageImpl<>(coursePrograms, pageable, coursePrograms.size());

        return new ResponseEntity<>(pageCoursePrograms.map(coreMapper::toCoreResponse), HttpStatus.OK);
    }

}
