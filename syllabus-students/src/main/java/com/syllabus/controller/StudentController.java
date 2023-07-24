package com.syllabus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.syllabus.data.StudentRequest;
import com.syllabus.data.StudentResponse;
import com.syllabus.service.StudentService;
import com.syllabus.util.StudentMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@RequestBody StudentRequest request) {
        var student = this.studentService.createStudent(this.studentMapper.toStudentModel(request));
        return new ResponseEntity<>(this.studentMapper.toStudentResponse(student), HttpStatus.CREATED);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable String id) {
        var student = this.studentService.getStudent(id);
        return new ResponseEntity<>(this.studentMapper.toStudentResponse(student), HttpStatus.OK);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable String id, @RequestBody StudentRequest request) {
        var student = this.studentService.updateStudent(id, this.studentMapper.toStudentModel(request));
        return new ResponseEntity<>(this.studentMapper.toStudentResponse(student), HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<StudentResponse> deleteStudent(@PathVariable String id) {
        this.studentService.deleteStudent(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
