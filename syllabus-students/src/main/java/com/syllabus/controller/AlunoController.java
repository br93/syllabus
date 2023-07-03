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

import com.syllabus.data.AlunoRequest;
import com.syllabus.data.AlunoResponse;
import com.syllabus.service.AlunoService;
import com.syllabus.util.AlunoMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/student")
public class AlunoController{

    private final AlunoService alunoService;
    private final AlunoMapper alunoMapper;

    @PostMapping
    public ResponseEntity<AlunoResponse> createAluno(@RequestBody AlunoRequest request) {
        var aluno = this.alunoService.createAluno(this.alunoMapper.toAlunoModel(request));
        return new ResponseEntity<>(this.alunoMapper.toAlunoResponse(aluno), HttpStatus.CREATED);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<AlunoResponse> getAlunoById(@PathVariable String id) {
        var aluno = this.alunoService.getAluno(id);
        return new ResponseEntity<>(this.alunoMapper.toAlunoResponse(aluno), HttpStatus.OK);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<AlunoResponse> updateAluno(@PathVariable String id, @RequestBody AlunoRequest request) {
        var aluno = this.alunoService.updateAluno(id, this.alunoMapper.toAlunoModel(request));
        return new ResponseEntity<>(this.alunoMapper.toAlunoResponse(aluno), HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<AlunoResponse> deleteAluno(@PathVariable String id) {
        this.alunoService.deleteAluno(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
