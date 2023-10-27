package com.syllabus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.syllabus.data.response.StudentDataResponse;
import com.syllabus.service.StudentDataService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/recommendations/data")
public class StudentDataController {

    private final StudentDataService studentDataService;
    
    @PostMapping
    public ResponseEntity<StudentDataResponse> createData(@RequestParam(name = "user_id") String userId){
        var studentData = studentDataService.createData(userId);
        return new ResponseEntity<>(studentData, HttpStatus.OK);
    }
    
}
