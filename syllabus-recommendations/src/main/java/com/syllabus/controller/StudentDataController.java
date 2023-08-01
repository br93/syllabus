package com.syllabus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.syllabus.client.core.response.StudentDataResponse;
import com.syllabus.service.StudentDataService;
import com.syllabus.util.StudentDataMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/recommendations/data")
public class StudentDataController {

    private final StudentDataService studentDataService;
    private final StudentDataMapper studentDataMapper;

    @PostMapping
    public ResponseEntity<StudentDataResponse> createData(@RequestParam(name = "user_id") String userId){
        return new ResponseEntity<>(studentDataMapper.toResponse(studentDataService.createData(userId)), HttpStatus.OK);
    }
    
}
