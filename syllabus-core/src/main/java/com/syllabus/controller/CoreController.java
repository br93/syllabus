package com.syllabus.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<CoreResponseModel>> getCoursesTaken(@PathVariable(name = "id") String userId) {
        var coursePrograms = coreService.getCoursesTaken(userId);

        return new ResponseEntity<>(
                coursePrograms.stream().map(coreMapper::toCoreResponse).collect(Collectors.toList()), HttpStatus.OK);

    }

    @GetMapping("students/{id}/required-courses")
    public ResponseEntity<List<CoreResponseModel>> getAllRequiredCourses(@PathVariable(name = "id") String userId) {
        var coursePrograms = coreService.getAllRequiredCourses(userId);

        return new ResponseEntity<>(
                coursePrograms.stream().map(coreMapper::toCoreResponse).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("students/{id}/required-courses/missing")
    public ResponseEntity<List<CoreResponseModel>> getMissingRequiredCourses(@PathVariable(name = "id") String userId) {
        var coursePrograms = coreService.getMissingRequiredCourses(userId);

        return new ResponseEntity<>(
                coursePrograms.stream().map(coreMapper::toCoreResponse).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("students/{id}/elective-courses")
    public ResponseEntity<List<CoreResponseModel>> getAllElectiveCourses(@PathVariable(name = "id") String userId) {
        var coursePrograms = coreService.getAllElectiveCourses(userId);

        return new ResponseEntity<>(
                coursePrograms.stream().map(coreMapper::toCoreResponse).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("students/{id}/elective-courses/missing")
    public ResponseEntity<List<CoreResponseModel>> getMissingElectiveCourses(@PathVariable(name = "id") String userId) {
        var coursePrograms = coreService.getMissingElectiveCourses(userId);

        return new ResponseEntity<>(
                coursePrograms.stream().map(coreMapper::toCoreResponse).collect(Collectors.toList()), HttpStatus.OK);
    }

}
