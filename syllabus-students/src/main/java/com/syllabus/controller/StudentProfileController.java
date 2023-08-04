package com.syllabus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syllabus.cache.CacheService;
import com.syllabus.data.StudentResponse;
import com.syllabus.service.StudentService;
import com.syllabus.util.StudentMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class StudentProfileController {

    private final StudentService studentService;
    private final StudentMapper studentMapper;

    private final CacheService cacheService;

    @GetMapping(path = "{id}/student-profile")
    public ResponseEntity<StudentResponse> getStudentByUserId(@PathVariable(name = "id") String userId) {

        var cacheId = cacheService.generateCacheId("students", userId);

        if(cacheService.isCached(cacheId))
            return new ResponseEntity<>(cacheService.getStudent(userId), HttpStatus.OK);
        
        var student = this.studentService.getStudentByUserId(userId);
        var response = this.studentMapper.toStudentResponse(student);

        cacheService.cacheStudent(cacheId, response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
