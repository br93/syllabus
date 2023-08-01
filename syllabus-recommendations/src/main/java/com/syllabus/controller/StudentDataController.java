package com.syllabus.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.syllabus.data.model.StudentDataModel;
import com.syllabus.service.StudentDataService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/recommendations/data")
public class StudentDataController {

    private final StudentDataService studentDataService;

    @PostMapping
    public StudentDataModel createData(@RequestParam(name = "user_id") String userId){
        return studentDataService.createData(userId);
    }
    
}
